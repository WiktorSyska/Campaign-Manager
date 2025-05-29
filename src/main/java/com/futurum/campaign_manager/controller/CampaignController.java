package com.futurum.campaign_manager.controller;


import com.futurum.campaign_manager.dto.ApiResponse;
import com.futurum.campaign_manager.dto.CampaignDTO;
import com.futurum.campaign_manager.service.CampaignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for campaign management operations.
 * <p>
 * Provides full CRUD endpoints under '/api/campaigns' path for managing marketing campaigns.
 * Supports CORS for cross-origin requests and uses {@link CampaignService} for business logic.
 * All responses follow the standardized {@link ApiResponse} format.
 * </p>
 *
 * <p>
 * Features:
 * <ul>
 *   <li>Input validation through {@link Valid} annotations</li>
 *   <li>Comprehensive error handling</li>
 *   <li>Proper HTTP status codes for all operations</li>
 *   <li>Detailed success/error messages</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignDTO>>> getAllCampaigns() {
        try {
            List<CampaignDTO> campaigns = campaignService.getAllCampaigns();
            return ResponseEntity.ok(ApiResponse.success("Campaigns retrieved successfully", campaigns));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving campaigns: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignDTO>> getCampaignById(@PathVariable Long id) {
        try {
            return campaignService.getCampaignById(id)
                    .map(campaign -> ResponseEntity.ok(ApiResponse.success("Campaign found", campaign)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving campaign: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CampaignDTO>> createCampaign(@Valid @RequestBody CampaignDTO campaignDTO) {
        try {
            CampaignDTO createdCampaign = campaignService.createCampaign(campaignDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Campaign created successfully", createdCampaign));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error creating campaign: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignDTO>> updateCampaign(@PathVariable Long id,
                                                                   @Valid @RequestBody CampaignDTO campaignDTO) {
        try {
            CampaignDTO updatedCampaign = campaignService.updateCampaign(id, campaignDTO);
            return ResponseEntity.ok(ApiResponse.success("Campaign updated successfully", updatedCampaign));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error updating campaign: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCampaign(@PathVariable Long id) {
        try {
            campaignService.deleteCampaign(id);
            return ResponseEntity.ok(ApiResponse.success("Campaign deleted successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error deleting campaign: " + e.getMessage()));
        }
    }
}
