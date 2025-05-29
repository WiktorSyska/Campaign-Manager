package com.futurum.campaign_manager.repository;

import com.futurum.campaign_manager.model.Campaign;
import com.futurum.campaign_manager.model.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Campaign} entities.
 * <p>
 * Provides CRUD operations through {@link JpaRepository} and custom query methods
 * for campaign-related data access. Includes both derived queries and custom JPQL queries.
 */
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStatus(CampaignStatus status);

    List<Campaign> findByCampaignNameContainingIgnoreCase(String campaignName);

    @Query("SELECT c FROM Campaign c WHERE c.town.id = :townId")
    List<Campaign> findByTownId(@Param("townId") Long townId);

    @Query("SELECT c FROM Campaign c JOIN c.keywords k WHERE k.keywordText = :keyword")
    List<Campaign> findByKeyword(@Param("keyword") String keyword);
}
