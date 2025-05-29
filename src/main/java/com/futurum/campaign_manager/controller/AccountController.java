package com.futurum.campaign_manager.controller;

import com.futurum.campaign_manager.dto.AccountBalanceDTO;
import com.futurum.campaign_manager.dto.ApiResponse;
import com.futurum.campaign_manager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for account-related operations.
 * <p>
 * Exposes endpoints under '/api/account' path for managing account information.
 * Currently focuses on balance retrieval functionality through {@link AccountService}.
 * </p>
 *
 * <p>
 * Returns responses in standardized {@link ApiResponse} format with proper
 * error handling for service layer exceptions.
 * </p>
 */

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<AccountBalanceDTO>> getBalance() {
        try {
            AccountBalanceDTO balance = accountService.getAccountBalance("Emerald Account");
            return ResponseEntity.ok(ApiResponse.success("Balance retrieved", balance));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error fetching balance: " + e.getMessage()));
        }
    }
}
