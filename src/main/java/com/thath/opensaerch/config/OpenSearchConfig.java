package com.thath.opensaerch.config;

import com.thath.opensaerch.function.TriArgsFunction;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.transport.rest_client.RestClientTransport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
* OpenSearch bean configurations.
* */
@Configuration
public class OpenSearchConfig {

    /**
     * CredentialsProvider Bean Definition.
     * @param username
     * @param password
     * @return CredentialsProvider
     * */
    @Qualifier("CredentialProvider")
    @Bean
    @Scope("singleton")
    public CredentialsProvider getCredentialsProvider(@Value("${openSearch.username}")final String username,
                                                      @Value("${openSearch.password}")final String password) {
        return getCredentialsProviderFunction.apply(username, password);
    }

    /**
     * RestClient Bean Definition.
     * @param credentialsProvider
     * @param host
     * @param port
     * @return RestClient
     * */
    @Qualifier("RestClient")
    @Bean
    @Scope("singleton")
    public RestClient getRestClient(@Qualifier("CredentialProvider") final CredentialsProvider credentialsProvider,
                                    @Value("${openSearch.host}")final String host,
                                    @Value("${openSearch.port}")final Integer port
                                    ) {
        return getRestClientFunction.apply(host, port, credentialsProvider);
    }

    /**
     * OpenSearchClient Bean Definition.
     * @param restClient
     * @return OpenSearchClient
     * */
    @Bean
    @Scope("singleton")
    public OpenSearchClient getOpenSearchClient(@Qualifier("RestClient")final RestClient restClient) {
        return getOpenSearchClientFunction.apply(restClient);
    }

    private Function<RestClient, OpenSearchClient> getOpenSearchClientFunction = (
            final RestClient restClient
    ) -> {
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new OpenSearchClient(transport);
    };

    private TriArgsFunction<String, Integer, CredentialsProvider, RestClient> getRestClientFunction = (
            final String host,
            final Integer port,
            final CredentialsProvider credentialsProvider
    ) -> {
        RestClient restClient = RestClient.builder(new HttpHost(host, port)).
                setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }).build();
        return restClient;
    };

    private BiFunction<String, String, CredentialsProvider> getCredentialsProviderFunction = (
            final String username,
            final String password
    ) -> {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
        return credentialsProvider;
    };

}