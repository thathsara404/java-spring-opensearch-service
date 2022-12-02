package com.thath.opensaerch.repository.search;

import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import com.thath.opensaerch.consts.ErrorConst;
import com.thath.opensaerch.entity.search.Account;
import com.thath.opensaerch.exception.UnExpectedResultFoundException;
import com.thath.opensaerch.repository.search.specification.IAccountRepository;
import lombok.AllArgsConstructor;
import org.opensearch.Build;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.client.Request;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.Requests;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.common.unit.TimeValue;
import org.opensearch.search.builder.PointInTimeBuilder;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of IAccountRepository
 * */
@Repository
@AllArgsConstructor
public class AccountRepositoryImpl implements IAccountRepository {

    @Autowired
    private OpenSearchClient openSearchClient;

    final Logger logger = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    /**
     * Find account by account number and map to Account
     * @param accountNumber Long
     * @return Account
     * */
    @Override
    public Account findByAccountNumber(Long accountNumber) {
        try {

            SearchResponse<Account> searchResponse = openSearchClient.
                    search(s -> s.index("accounts")
                            .query(q-> q.match(t ->
                                    t.field("account_number")
                                            .query(FieldValue.of(accountNumber)))), Account.class);
            List<Account> account = searchResponse.hits().hits().stream().map(data -> new Account(data.source().getAccount_number(),
                    data.source().getAddress(),
                    data.source().getBalance(),
                    data.source().getCity(),
                    data.source().getAge(),
                    data.source().getEmail(),
                    data.source().getEmployer(),
                    data.source().getFirstname(),
                    data.source().getGender(),
                    data.source().getLastname(),
                    data.source().getState())).collect(Collectors.toList());
            if (account.size() > 1) {
                throw new UnExpectedResultFoundException(ErrorConst.FOUND_MORE_THAN_ONE_ACCOUNT_ERROR_MESSAGE, null);
            }
            return account.get(0);

        } catch (IOException exception) {

            String errorMessage = exception.getMessage();
            Throwable throwable = exception.getCause();
            logger.error("An error occurred while fetching account details. Error message {}. Error cause {}.",
                    errorMessage, throwable);
            throw new RuntimeException(exception);

        } catch (UnExpectedResultFoundException exception) {

            String errorMessage = exception.getMessage();
            Throwable throwable = exception.getCause();
            logger.error("An error occurred while fetching account details. Error message {}. Error cause {}.",
                    errorMessage, throwable);
            throw exception;

        }
    }

    /**
     * Find account by account age and map to Account
     * @param age Long
     * @return Account
     * */
    @Override
    public List<Account> findByAge(final Long age) {
        try {
            //https://stackoverflow.com/questions/73784672/sort-by-custom-field-property-in-opensearch-index-with-java
//            QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(String.valueOf(age)).field("age");
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
//                    .query(queryBuilder)
//                    .sort(new FieldSortBuilder("age").order(SortOrder.DESC));
//
//
//            SearchRequest ss = new SearchRequest("accounts");
//            ss.source(searchSourceBuilder);
//
//            SearchResponse<Account> searchResponse = openSearchClient.search(ss, );
//
//
//            SearchResponse<Account> searchResponse = openSearchClient.search(ss, Account.class);

//            SearchResponse searchResponse = openSearchClient.search(searchRequest, RequestOptions.DEFAULT);

            System.out.println("=======?? AGE" + age);


// HERE---->>>
//            SearchRequest searchRequest = new SearchRequest();
//            final PointInTimeBuilder pointInTimeBuilder = new PointInTimeBuilder(pitId);
//            pointInTimeBuilder.setKeepAlive("2m");
//                                searchRequest.source(new SearchSourceBuilder().pointInTimeBuilder(pointInTimeBuilder));
//            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//
//
//            final PointInTimeBuilder pointInTimeBuilder = new PointInTimeBuilder(pitId);
//            pointInTimeBuilder.setKeepAlive(new TimeValue(120000));

            // READ code impl       : https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search.html

            PointInTimeBuilder pbuilder = new PointInTimeBuilder("accounts");
            pbuilder.setKeepAlive(TimeValue.timeValueSeconds(120)); // two minutes
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.source(new SearchSourceBuilder().pointInTimeBuilder(pbuilder));

            openSearchClient.search(org.opensearch.client.opensearch.core.SearchRequest.of(searchRequest), )


            final SortOptions sort = new SortOptions.Builder().
                    field(f -> f.field("account_number")
                            .order(SortOrder.Asc))
                                .build();
            final List<SortOptions> list = new ArrayList<>();
            list.add(sort);

            final SearchResponse<Account> searchResponse = openSearchClient.
                    search(s -> s.index("accounts")
                            .sort(list).from(0).size(3)

                            .timeout("60000")
                            .query(q-> q.match(t ->
                                    t.field("age")
                                            .query(FieldValue.of(age)))), Account.class);
            final Long TimeTook = searchResponse.took();
            final String pitId = searchResponse.pitId();

            System.out.println("PITID : "+ pitId + " " + TimeTook);

            final List<Account> accounts = searchResponse.hits().hits().stream().map(data -> new Account(data.source().getAccount_number(),
                    data.source().getAddress(),
                    data.source().getBalance(),
                    data.source().getCity(),
                    data.source().getAge(),
                    data.source().getEmail(),
                    data.source().getEmployer(),
                    data.source().getFirstname(),
                    data.source().getGender(),
                    data.source().getLastname(),
                    data.source().getState())).collect(Collectors.toList());
            return accounts;

        } catch (IOException exception) {
            System.out.println("------>>>>" + exception.getMessage());
            String errorMessage = exception.getMessage();
            Throwable throwable = exception.getCause();
            logger.error("An error occurred while fetching account details. Error message {}. Error cause {}.",
                    errorMessage, throwable);
            throw new RuntimeException(exception);

        } catch (UnExpectedResultFoundException exception) {
            System.out.println("------>>>>" + exception.getMessage());
            String errorMessage = exception.getMessage();
            Throwable throwable = exception.getCause();
            logger.error("An error occurred while fetching account details. Error message {}. Error cause {}.",
                    errorMessage, throwable);
            throw exception;

        }
    }
}
