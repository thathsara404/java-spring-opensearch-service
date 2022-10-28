package com.thath.opensaerch.service.specification;

import com.thath.opensaerch.dto.AccountDTO;

/**
 * Represent the specification which includes accounts index specific business logic operations.
 * */
public interface IAccountService {
    /**
     * Find account by account number and map to AccountDTO
     * @param accountNumber Long
     * @return AccountDTO
     * */
    public abstract AccountDTO findByAccountNumber(Long accountNumber);
}
