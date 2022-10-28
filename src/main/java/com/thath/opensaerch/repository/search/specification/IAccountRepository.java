package com.thath.opensaerch.repository.search.specification;

import com.thath.opensaerch.entity.search.Account;

/**
 * Represent the specification which includes accounts index specific OpenSearch data operations.
 * */
public interface IAccountRepository {

    /**
     * Find account by account number and map to Account
     * @param accountNumber Long
     * @return Account
     * */
    public abstract Account findByAccountNumber(Long accountNumber);

}
