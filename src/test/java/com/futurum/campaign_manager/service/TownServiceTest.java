package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.TownDTO;
import com.futurum.campaign_manager.model.Town;
import com.futurum.campaign_manager.repository.TownRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockMakers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TownServiceTest {

    @Mock
    private TownRepository townRepository;

    @InjectMocks
    private TownService townService;

    @Test
    void getAllTowns_ShouldReturnOrderedList() {
        // Given
        List<Town> mockTowns = Arrays.asList(
                new Town("Warszawa", "00-001"),
                new Town("Kraków", "30-001")
        );
        when(townRepository.findAllByOrderByTownName()).thenReturn(mockTowns);

        // When
        List<TownDTO> result = townService.getAllTowns();

        // Then
        assertEquals(2, result.size());
        assertEquals("Warszawa", result.get(0).getTownName());
        assertEquals("Kraków", result.get(1).getTownName());
        verify(townRepository).findAllByOrderByTownName();
    }

    @Test
    void getAllTowns_ShouldReturnEmptyList() {
        when(townRepository.findAllByOrderByTownName()).thenReturn(Collections.emptyList());

        List<TownDTO> result = townService.getAllTowns();

        assertTrue(result.isEmpty());
    }
}