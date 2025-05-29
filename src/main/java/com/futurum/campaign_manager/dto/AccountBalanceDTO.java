package com.futurum.campaign_manager.dto;

import java.math.BigDecimal;
/**
 * Data Transfer Object representing an account's balance information.
 * <p>
 * This DTO is used to transfer account balance data between different layers of the application,
 * typically for reporting or display purposes. It contains the essential financial information
 * about an account without exposing unnecessary details.
 * </p>
 *
 * <p>
 * The object holds:
 * <ul>
 *   <li>Account name - identifier of the account</li>
 *   <li>Current balance - the financial amount available in the account</li>
 * </ul>
 * </p>
 */
public class AccountBalanceDTO {

    private String accountName;
    private BigDecimal balance;

    public AccountBalanceDTO() {}

    public AccountBalanceDTO(String accountName, BigDecimal balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
