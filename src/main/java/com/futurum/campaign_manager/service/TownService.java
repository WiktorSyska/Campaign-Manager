package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.TownDTO;
import com.futurum.campaign_manager.model.Town;
import com.futurum.campaign_manager.repository.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing Town entities.
 * Provides business logic for town-related operations including
 * data retrieval and conversion to DTOs.
 */
@Service
public class TownService {

    private final TownRepository townRepository;

    @Autowired
    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }


    public List<TownDTO> getAllTowns() {
        return townRepository.findAllByOrderByTownName().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TownDTO convertToDTO(Town town) {
        return new TownDTO(town.getId(), town.getTownName(), town.getPostalCode());
    }
}