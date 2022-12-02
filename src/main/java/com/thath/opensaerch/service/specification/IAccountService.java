package com.thath.opensaerch.service.specification;

import com.thath.opensaerch.dto.AccountDTO;

import java.util.List;

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

    /**
     * Find account by age and map to AccountDTO
     * @param age Long
     * @return AccountDTO
     * */
    public abstract List<AccountDTO> findByAge(Long age);
}
