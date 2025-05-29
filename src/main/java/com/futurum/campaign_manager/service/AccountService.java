package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.AccountBalanceDTO;
import com.futurum.campaign_manager.model.Account;
import com.futurum.campaign_manager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service layer for handling account-related operations including balance checks,
 * fund transfers, and account management.
 * <p>
 * This service provides transactional operations on account entities and ensures
 * data consistency through Spring's transaction management.
 */
@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Constructs an AccountService with the specified repository.
     *
     * @param accountRepository the account repository to be used for database operations
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Retrieves the current balance for a specified account.
     *
     * @param accountName the name of the account to query
     */
    public AccountBalanceDTO getAccountBalance(String accountName) {
        Account account = accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountName));

        return new AccountBalanceDTO(account.getAccountName(), account.getBalance());
    }

    /**
     * Checks if an account has sufficient funds for a specified amount.
     *
     * @param accountName the name of the account to check
     * @param amount the amount to verify against the account balance
     */
    public boolean hasEnoughFunds(String accountName, BigDecimal amount) {
        Account account = accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountName));

        return account.hasEnoughFunds(amount);
    }

    /**
     * Deducts funds from the specified account.
     * <p>
     * The operation will fail if the account doesn't have sufficient funds.
     *
     * @param accountName the name of the account to deduct from
     * @param amount the amount to deduct (must be positive)
     */
    public void deductFunds(String accountName, BigDecimal amount) {
        Account account = accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountName));

        account.deductFunds(amount);
        accountRepository.save(account);
    }

    /**
     * Adds funds to the specified account.
     *
     * @param accountName the name of the account to add funds to
     * @param amount the amount to add (must be positive)
     */
    public void addFunds(String accountName, BigDecimal amount) {
        Account account = accountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountName));

        account.addFunds(amount);
        accountRepository.save(account);
    }
}