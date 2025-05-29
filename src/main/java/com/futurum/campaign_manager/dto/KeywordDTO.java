package com.futurum.campaign_manager.dto;

import jakarta.validation.constraints.NotBlank;
/**
 * Data Transfer Object (DTO) representing keyword information.
 * <p>
 * This class is used to transfer town-related data between different layers of the application.
 * It contains basic town information including identifier, and keyword text.
 */
public class KeywordDTO {

    private Long id;

    @NotBlank(message = "Keyword text is mandatory")
    private String keywordText;

    public KeywordDTO() {
    }

    public KeywordDTO(Long id, String keywordText) {
        this.id = id;
        this.keywordText = keywordText;
    }

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

