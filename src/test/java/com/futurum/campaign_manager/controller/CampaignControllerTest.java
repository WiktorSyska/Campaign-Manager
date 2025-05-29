package com.futurum.campaign_manager.controller;

import com.futurum.campaign_manager.dto.CampaignDTO;
import com.futurum.campaign_manager.model.CampaignStatus;
import com.futurum.campaign_manager.service.CampaignService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CampaignController.class)
class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    @Test
    void getAllCampaigns_ShouldReturn200() throws Exception {
        // Given
        CampaignDTO campaign = new CampaignDTO();
        campaign.setCampaignName("Test Campaign");
        campaign.setBidAmount(new BigDecimal("10.00"));
        campaign.setCampaignFund(new BigDecimal("100.00"));
        campaign.setStatus(CampaignStatus.ON);
        campaign.setRadius(10);
        campaign.setKeywordIds(Set.of(1L));

        when(campaignService.getAllCampaigns()).thenReturn(List.of(campaign));

        // When & Then
        mockMvc.perform(get("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].campaignName").value("Test Campaign"));
    }

    @Test
    void createCampaign_ShouldReturn201() throws Exception {
        // Given
        CampaignDTO request = new CampaignDTO();
        request.setCampaignName("New Campaign");
        request.setBidAmount(new BigDecimal("10.00"));
        request.setCampaignFund(new BigDecimal("100.00"));
        request.setStatus(CampaignStatus.ON);
        request.setRadius(10);
        request.setKeywordIds(Set.of(1L));

        when(campaignService.createCampaign(any(CampaignDTO.class))).thenReturn(request);

        // When & Then
        mockMvc.perform(post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "campaignName": "New Campaign",
                        "bidAmount": 10.00,
                        "campaignFund": 100.00,
                        "status": "ON",
                        "radius": 10,
                        "keywordIds": [1]
                    }"""))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.campaignName").value("New Campaign"))
                .andExpect(jsonPath("$.data.bidAmount").value(10.00))
                .andExpect(jsonPath("$.data.campaignFund").value(100.00))
                .andExpect(jsonPath("$.data.status").value("ON"))
                .andExpect(jsonPath("$.data.radius").value(10));
    }

    @Test
    void createCampaign_ShouldHandleValidationError() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCampaigns_ShouldReturnEmptyList() throws Exception {
        // Given
        when(campaignService.getAllCampaigns()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    void createCampaign_ShouldHandleServiceException() throws Exception {
        // Given
        when(campaignService.createCampaign(any(CampaignDTO.class)))
                .thenThrow(new RuntimeException("Service error"));

        // When & Then
        mockMvc.perform(post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "campaignName": "New Campaign",
                        "bidAmount": 10.00,
                        "campaignFund": 100.00,
                        "status": "ON",
                        "radius": 10,
                        "keywordIds": [1]
                    }"""))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").exists());
    }
}