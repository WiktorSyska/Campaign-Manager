package com.futurum.campaign_manager.controller;

import com.futurum.campaign_manager.dto.KeywordDTO;
import com.futurum.campaign_manager.service.KeywordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(KeywordController.class)
class KeywordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeywordService keywordService;

    @Test
    void getAllKeywords_ShouldReturn200_WhenKeywordsExist() throws Exception {
        // Given
        List<KeywordDTO> mockKeywords = Arrays.asList(
                new KeywordDTO(1L, "java"),
                new KeywordDTO(2L, "spring"),
                new KeywordDTO(3L, "programming")
        );
        when(keywordService.getAllKeywords()).thenReturn(mockKeywords);

        // When & Then
        mockMvc.perform(get("/api/keywords")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Keywords retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].keywordText").value("java"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].keywordText").value("spring"))
                .andExpect(jsonPath("$.data[2].id").value(3))
                .andExpect(jsonPath("$.data[2].keywordText").value("programming"));
    }

    @Test
    void getAllKeywords_ShouldReturn200_WhenNoKeywords() throws Exception {
        // Given
        when(keywordService.getAllKeywords()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/keywords")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Keywords retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void getAllKeywords_ShouldReturn500_WhenServiceThrowsException() throws Exception {
        // Given
        when(keywordService.getAllKeywords()).thenThrow(new RuntimeException("Database connection error"));

        // When & Then
        mockMvc.perform(get("/api/keywords")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error retrieving keywords: Database connection error"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void searchKeywords_ShouldReturn200_WhenKeywordsFound() throws Exception {
        // Given
        List<KeywordDTO> mockKeywords = Arrays.asList(
                new KeywordDTO(1L, "java"),
                new KeywordDTO(2L, "javascript")
        );
        when(keywordService.searchKeywords("java")).thenReturn(mockKeywords);

        // When & Then
        mockMvc.perform(get("/api/keywords/search")
                        .param("q", "java")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Keywords found"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].keywordText").value("java"))
                .andExpect(jsonPath("$.data[1].keywordText").value("javascript"));
    }

    @Test
    void searchKeywords_ShouldReturn200_WhenNoQueryParam() throws Exception {
        // Given
        List<KeywordDTO> mockKeywords = Arrays.asList(
                new KeywordDTO(1L, "keyword1"),
                new KeywordDTO(2L, "keyword2")
        );
        when(keywordService.searchKeywords(null)).thenReturn(mockKeywords);

        // When & Then
        mockMvc.perform(get("/api/keywords/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Keywords found"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    void searchKeywords_ShouldReturn200_WhenNoKeywordsFound() throws Exception {
        // Given
        when(keywordService.searchKeywords("nonexistent")).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/keywords/search")
                        .param("q", "nonexistent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Keywords found"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void searchKeywords_ShouldReturn500_WhenServiceThrowsException() throws Exception {
        // Given
        when(keywordService.searchKeywords("error")).thenThrow(new RuntimeException("Search service unavailable"));

        // When & Then
        mockMvc.perform(get("/api/keywords/search")
                        .param("q", "error")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error searching keywords: Search service unavailable"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void searchKeywords_ShouldReturn200_WhenEmptyQueryParam() throws Exception {
        // Given
        List<KeywordDTO> mockKeywords = Arrays.asList(
                new KeywordDTO(1L, "all"),
                new KeywordDTO(2L, "keywords")
        );
        when(keywordService.searchKeywords("")).thenReturn(mockKeywords);

        // When & Then
        mockMvc.perform(get("/api/keywords/search")
                        .param("q", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Keywords found"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }
}