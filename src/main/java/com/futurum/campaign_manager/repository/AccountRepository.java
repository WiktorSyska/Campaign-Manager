package com.futurum.campaign_manager.repository;

import com.futurum.campaign_manager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link Account} entity operations.
 * <p>
 * Provides standard CRUD operations through {@link JpaRepository} and custom query methods
 * for account-related data access. All methods are transactional by default.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountName(String accountName);
}
