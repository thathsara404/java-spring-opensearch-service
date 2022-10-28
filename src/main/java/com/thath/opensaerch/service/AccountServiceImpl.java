package com.thath.opensaerch.service;

import com.thath.opensaerch.dto.AccountDTO;
import com.thath.opensaerch.repository.search.AccountRepositoryImpl;
import com.thath.opensaerch.entity.search.Account;
import com.thath.opensaerch.exception.AccountNotFoundException;
import com.thath.opensaerch.exception.UnExpectedResultFoundException;
import com.thath.opensaerch.service.specification.IAccountService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of IAccountService
 * */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepositoryImpl accountRepository;

    final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    /**
     * Find account by account number and map to AccountDTO
     * @param accountNumber Long
     * @return AccountDTO
     * */
    @Override
    public AccountDTO findByAccountNumber(Long accountNumber) {
        try {

            logger.info("Account details are being fetched by account number {} ", accountNumber);
            Account account  = accountRepository.findByAccountNumber(accountNumber);
            AccountDTO accountDTO = new AccountDTO(
                    account.getAccount_number(),
                    account.getAddress(),
                    account.getBalance(),
                    account.getCity(),
                    account.getAge()
            );
            return accountDTO;

        } catch (UnExpectedResultFoundException exception) {

            String errorMessage = exception.getMessage();
            Throwable throwable = exception.getCause();
            logger.error("An error occurred while fetching account details. Error message {}. Error cause {}.",
                    errorMessage, throwable);
            throw exception;

        } catch (Exception exception) {

            String errorMessage = exception.getMessage();
            Throwable throwable = exception.getCause();
            logger.error("An error occurred while fetching account details. Error message {}. Error cause {}.",
                    errorMessage, throwable);
            throw new AccountNotFoundException(errorMessage, throwable);

        }

    }

}
