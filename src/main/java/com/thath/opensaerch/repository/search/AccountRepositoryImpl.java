package com.thath.opensaerch.repository.search;

import com.thath.opensaerch.consts.ErrorConst;
import com.thath.opensaerch.entity.search.Account;
import com.thath.opensaerch.exception.UnExpectedResultFoundException;
import com.thath.opensaerch.repository.search.specification.IAccountRepository;
import lombok.AllArgsConstructor;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.IOException;
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
}
