package com.futurum.campaign_manager.controller;

import com.futurum.campaign_manager.dto.TownDTO;
import com.futurum.campaign_manager.service.TownService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TownController.class)
class TownControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TownService townService;

    @Test
    void getAllTowns_ShouldReturn200() throws Exception {
        // Given
        List<TownDTO> mockTowns = Arrays.asList(
                new TownDTO(1L, "Warszawa", "00-001"),
                new TownDTO(2L, "Kraków", "30-001")
        );
        when(townService.getAllTowns()).thenReturn(mockTowns);

        // When & Then
        mockMvc.perform(get("/api/towns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].townName").value("Warszawa"))
                .andExpect(jsonPath("$.data[0].postalCode").value("00-001"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].townName").value("Kraków"))
                .andExpect(jsonPath("$.data[1].postalCode").value("30-001"));
    }

    @Test
    void getAllTowns_ShouldHandleException() throws Exception {
        // Given
        when(townService.getAllTowns()).thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(get("/api/towns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value("Error retrieving towns: Database error"));
    }

    @Test
    void getAllTowns_ShouldReturnEmptyList_WhenNoTowns() throws Exception {
        // Given
        when(townService.getAllTowns()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/towns")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }
}