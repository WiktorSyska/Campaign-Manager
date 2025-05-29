package com.futurum.campaign_manager.repository;

import com.futurum.campaign_manager.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Repository interface for managing {@link Keyword} entities.
 * <p>
 * Provides standard CRUD operations through {@link JpaRepository} and additional custom queries
 * for keyword-related operations. Includes methods for finding keywords by exact match or
 * partial text search with case insensitivity.
 * </p>
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByKeywordText(String keywordText);

    @Query("SELECT k FROM Keyword k WHERE LOWER(k.keywordText) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Keyword> findByKeywordTextContainingIgnoreCase(@Param("text") String text);

    List<Keyword> findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(String text);
}
