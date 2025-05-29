package com.futurum.campaign_manager.controller;

import com.futurum.campaign_manager.dto.ApiResponse;
import com.futurum.campaign_manager.dto.TownDTO;
import com.futurum.campaign_manager.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for managing town-related operations.
 * <p>
 * Exposes endpoints under the '/api/towns' base path for retrieving town information.
 * Uses {@link TownService} for business logic and returns standardized responses
 * wrapped in {@link ApiResponse}.
 * </p>
 *
 * <p>
 * Handles errors by returning appropriate HTTP status codes and error messages.
 * </p>
 */
@RestController
@RequestMapping("/api/towns")
public class TownController {

    private final TownService townService;

    @Autowired
    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TownDTO>>> getAllTowns() {
        try {
            List<TownDTO> towns = townService.getAllTowns();
            return ResponseEntity.ok(ApiResponse.success("Towns retrieved successfully", towns));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error retrieving towns: " + e.getMessage()));
        }
    }

}