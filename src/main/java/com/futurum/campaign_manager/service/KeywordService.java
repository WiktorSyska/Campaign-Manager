package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.KeywordDTO;
import com.futurum.campaign_manager.model.Keyword;
import com.futurum.campaign_manager.repository.KeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Service class for managing Keyword entities.
 * Provides business logic for keyword-related operations including
 * data retrieval and conversion to DTOs.
 */
@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public List<KeywordDTO> getAllKeywords() {
        return keywordRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<KeywordDTO> searchKeywords(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllKeywords();
        }

        return keywordRepository.findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(query.trim())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private KeywordDTO convertToDTO(Keyword keyword) {
        return new KeywordDTO(keyword.getId(), keyword.getKeywordText());
    }
}
