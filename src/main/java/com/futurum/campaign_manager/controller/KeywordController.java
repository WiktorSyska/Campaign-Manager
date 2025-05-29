package com.futurum.campaign_manager.controller;

import com.futurum.campaign_manager.dto.ApiResponse;
import com.futurum.campaign_manager.dto.KeywordDTO;
import com.futurum.campaign_manager.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for keyword management operations.
 * <p>
 * Provides endpoints under '/api/keywords' for retrieving and searching keywords.
 * Supports CORS for cross-origin requests and uses {@link KeywordService} for business logic.
 * </p>
 *
 * <p>
 * Includes both exact retrieval and search functionality, with all responses standardized
 * using {@link ApiResponse} format.
 * </p>
 */
@RestController
@RequestMapping("/api/keywords")
@CrossOrigin(origins = "*", maxAge = 3600)
public class KeywordController {

    private final KeywordService keywordService;

    @Autowired
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<KeywordDTO>>> getAllKeywords() {
        try {
            List<KeywordDTO> keywords = keywordService.getAllKeywords();
            return ResponseEntity.ok(ApiResponse.success("Keywords retrieved successfully", keywords));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error retrieving keywords: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<KeywordDTO>>> searchKeywords(@RequestParam(required = false) String q) {
        try {
            List<KeywordDTO> keywords = keywordService.searchKeywords(q);
            return ResponseEntity.ok(ApiResponse.success("Keywords found", keywords));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error searching keywords: " + e.getMessage()));
        }
    }
}
