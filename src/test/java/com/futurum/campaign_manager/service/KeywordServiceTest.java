package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.KeywordDTO;
import com.futurum.campaign_manager.model.Keyword;
import com.futurum.campaign_manager.repository.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeywordServiceTest {

    @Mock
    private KeywordRepository keywordRepository;

    @InjectMocks
    private KeywordService keywordService;

    private List<Keyword> sampleKeywords;

    @BeforeEach
    void setUp() {
        sampleKeywords = Arrays.asList(
                new Keyword(1L, "java"),
                new Keyword(2L, "spring"),
                new Keyword(3L, "javascript"),
                new Keyword(4L, "programming"),
                new Keyword(5L, "database")
        );
    }

    @Test
    void getAllKeywords_ShouldReturnAllKeywordsAsDTO() {
        // Given
        when(keywordRepository.findAll()).thenReturn(sampleKeywords);

        // When
        List<KeywordDTO> result = keywordService.getAllKeywords();

        // Then
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getKeywordText()).isEqualTo("java");
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getKeywordText()).isEqualTo("spring");

        verify(keywordRepository, times(1)).findAll();
    }

    @Test
    void getAllKeywords_ShouldReturnEmptyList_WhenNoKeywords() {
        // Given
        when(keywordRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<KeywordDTO> result = keywordService.getAllKeywords();

        // Then
        assertThat(result).isEmpty();
        verify(keywordRepository, times(1)).findAll();
    }

    @Test
    void searchKeywords_ShouldReturnMatchingKeywords_WhenQueryProvided() {
        // Given
        String query = "java";
        List<Keyword> matchingKeywords = Arrays.asList(
                new Keyword(1L, "java"),
                new Keyword(3L, "javascript")
        );
        when(keywordRepository.findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(query))
                .thenReturn(matchingKeywords);

        // When
        List<KeywordDTO> result = keywordService.searchKeywords(query);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getKeywordText()).isEqualTo("java");
        assertThat(result.get(1).getKeywordText()).isEqualTo("javascript");

        verify(keywordRepository, times(1))
                .findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(query);
        verify(keywordRepository, never()).findAll();
    }

    @Test
    void searchKeywords_ShouldReturnAllKeywords_WhenQueryIsNull() {
        // Given
        when(keywordRepository.findAll()).thenReturn(sampleKeywords);

        // When
        List<KeywordDTO> result = keywordService.searchKeywords(null);

        // Then
        assertThat(result).hasSize(5);
        assertThat(result.get(0).getKeywordText()).isEqualTo("java");

        verify(keywordRepository, times(1)).findAll();
        verify(keywordRepository, never())
                .findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(anyString());
    }

    @Test
    void searchKeywords_ShouldReturnAllKeywords_WhenQueryIsEmpty() {
        // Given
        when(keywordRepository.findAll()).thenReturn(sampleKeywords);

        // When
        List<KeywordDTO> result = keywordService.searchKeywords("");

        // Then
        assertThat(result).hasSize(5);
        verify(keywordRepository, times(1)).findAll();
        verify(keywordRepository, never())
                .findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(anyString());
    }

    @Test
    void searchKeywords_ShouldReturnAllKeywords_WhenQueryIsWhitespace() {
        // Given
        when(keywordRepository.findAll()).thenReturn(sampleKeywords);

        // When
        List<KeywordDTO> result = keywordService.searchKeywords("   ");

        // Then
        assertThat(result).hasSize(5);
        verify(keywordRepository, times(1)).findAll();
        verify(keywordRepository, never())
                .findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(anyString());
    }

    @Test
    void searchKeywords_ShouldTrimQuery_WhenQueryHasWhitespace() {
        // Given
        String queryWithWhitespace = "  java  ";
        String trimmedQuery = "java";
        List<Keyword> matchingKeywords = Arrays.asList(
                new Keyword(1L, "java")
        );
        when(keywordRepository.findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(trimmedQuery))
                .thenReturn(matchingKeywords);

        // When
        List<KeywordDTO> result = keywordService.searchKeywords(queryWithWhitespace);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getKeywordText()).isEqualTo("java");

        verify(keywordRepository, times(1))
                .findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(trimmedQuery);
    }

    @Test
    void searchKeywords_ShouldReturnEmptyList_WhenNoMatches() {
        // Given
        String query = "nonexistent";
        when(keywordRepository.findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(query))
                .thenReturn(Collections.emptyList());

        // When
        List<KeywordDTO> result = keywordService.searchKeywords(query);

        // Then
        assertThat(result).isEmpty();
        verify(keywordRepository, times(1))
                .findTop10ByKeywordTextContainingIgnoreCaseOrderByKeywordText(query);
    }

}