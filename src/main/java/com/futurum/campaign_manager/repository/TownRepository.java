package com.futurum.campaign_manager.repository;

import com.futurum.campaign_manager.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Town} entity operations.
 * <p>
 * Provides standard CRUD operations through {@link JpaRepository} and custom query methods
 * for town-related data access. All methods are transactional by default.
 */
@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    Optional<Town> findByTownName(String townName);

    List<Town> findByTownNameContainingIgnoreCaseOrderByTownName(String townName);

    List<Town> findAllByOrderByTownName();
}








