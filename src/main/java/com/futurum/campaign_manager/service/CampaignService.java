package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.CampaignDTO;
import com.futurum.campaign_manager.model.Keyword;
import com.futurum.campaign_manager.model.Town;
import com.futurum.campaign_manager.repository.CampaignRepository;
import com.futurum.campaign_manager.repository.KeywordRepository;
import com.futurum.campaign_manager.repository.TownRepository;

import com.futurum.campaign_manager.model.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.math.BigDecimal;

/**
 * Service class for managing Campaign entities and related business operations.
 *
 * This service handles the complete lifecycle of advertising campaigns including:
 * - Campaign creation, retrieval, updating, and deletion (CRUD operations)
 * - Fund management and validation through integration with AccountService
 * - Keyword and location (town) association management
 * - Data conversion between Campaign entities and CampaignDTO objects
 */
@Service
@Transactional
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final KeywordRepository keywordRepository;
    private final TownRepository townRepository;
    private final AccountService accountService;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository,
                           KeywordRepository keywordRepository,
                           TownRepository townRepository,
                           AccountService accountService) {
        this.campaignRepository = campaignRepository;
        this.keywordRepository = keywordRepository;
        this.townRepository = townRepository;
        this.accountService = accountService;
    }

    public List<CampaignDTO> getAllCampaigns() {
        return campaignRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CampaignDTO> getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .map(this::convertToDTO);
    }

    public CampaignDTO createCampaign(CampaignDTO campaignDTO) {
        // Check if account has sufficient funds before creating campaign
        if (!accountService.hasEnoughFunds("Emerald Account", campaignDTO.getCampaignFund())) {
            throw new IllegalArgumentException("Insufficient funds in Emerald Account");
        }

        Campaign campaign = convertToEntity(campaignDTO);

        // Associate keywords with the campaign
        Set<Keyword> keywords = getOrCreateKeywords(campaignDTO.getKeywordIds());
        campaign.setKeywords(keywords);

        // Set campaign location if specified
        if (campaignDTO.getTownId() != null) {
            Town town = townRepository.findById(campaignDTO.getTownId())
                    .orElseThrow(() -> new IllegalArgumentException("Town not found"));
            campaign.setTown(town);
        }

        Campaign savedCampaign = campaignRepository.save(campaign);

        // Deduct campaign funds from account after successful creation
        accountService.deductFunds("Emerald Account", campaignDTO.getCampaignFund());

        return convertToDTO(savedCampaign);
    }

    public CampaignDTO updateCampaign(Long id, CampaignDTO campaignDTO) {
        Campaign existingCampaign = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

        // Calculate fund difference to handle account balance adjustments
        var fundDifference = campaignDTO.getCampaignFund().subtract(existingCampaign.getCampaignFund());

        // Handle fund increase - check availability and deduct
        if (fundDifference.compareTo(java.math.BigDecimal.ZERO) > 0) {
            if (!accountService.hasEnoughFunds("Emerald Account", fundDifference)) {
                throw new IllegalArgumentException("Insufficient funds to increase campaign fund");
            }
            accountService.deductFunds("Emerald Account", fundDifference);
        }
        // Handle fund decrease - return excess funds to account
        else if (fundDifference.compareTo(java.math.BigDecimal.ZERO) < 0) {
            accountService.addFunds("Emerald Account", fundDifference.abs());
        }

        updateCampaignFields(existingCampaign, campaignDTO);

        Campaign savedCampaign = campaignRepository.save(existingCampaign);
        return convertToDTO(savedCampaign);
    }

    public void deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found"));

        // Return campaign funds to account before deletion
        accountService.addFunds("Emerald Account", campaign.getCampaignFund());

        campaignRepository.delete(campaign);
    }

    /**
     * Retrieves or validates existing keywords by their IDs
     */
    private Set<Keyword> getOrCreateKeywords(Set<Long> keywordIds) {
        return keywordIds.stream()
                .map(id -> keywordRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Keyword not found: " + id)))
                .collect(Collectors.toSet());
    }

    /**
     * Updates campaign fields with new values from DTO
     */
    private void updateCampaignFields(Campaign campaign, CampaignDTO dto) {
        campaign.setCampaignName(dto.getCampaignName());
        campaign.setBidAmount(dto.getBidAmount());
        campaign.setCampaignFund(dto.getCampaignFund());
        campaign.setStatus(dto.getStatus());
        campaign.setRadius(dto.getRadius());

        // Update town association if provided
        if (dto.getTownId() != null) {
            Town town = townRepository.findById(dto.getTownId())
                    .orElseThrow(() -> new IllegalArgumentException("Town not found"));
            campaign.setTown(town);
        }

        // Update keyword associations if provided
        if (dto.getKeywordIds() != null) {
            Set<Keyword> keywords = getOrCreateKeywords(dto.getKeywordIds());
            campaign.setKeywords(keywords);
        }
    }

    /**
     * Converts Campaign entity to CampaignDTO for data transfer
     */
    private CampaignDTO convertToDTO(Campaign campaign) {
        CampaignDTO dto = new CampaignDTO();
        dto.setId(campaign.getId());
        dto.setCampaignName(campaign.getCampaignName());
        dto.setBidAmount(campaign.getBidAmount());
        dto.setCampaignFund(campaign.getCampaignFund());
        dto.setStatus(campaign.getStatus());
        dto.setRadius(campaign.getRadius());
        dto.setCreatedAt(campaign.getCreatedAt());
        dto.setUpdatedAt(campaign.getUpdatedAt());

        // Set town information if available
        if (campaign.getTown() != null) {
            dto.setTownId(campaign.getTown().getId());
            dto.setTownName(campaign.getTown().getTownName());
        }

        // Set keyword information if available
        if (campaign.getKeywords() != null) {
            dto.setKeywordIds(campaign.getKeywords().stream()
                    .map(Keyword::getId)
                    .collect(Collectors.toSet()));
            dto.setKeywordTexts(campaign.getKeywords().stream()
                    .map(Keyword::getKeywordText)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    /**
     * Converts CampaignDTO to Campaign entity for persistence
     */
    private Campaign convertToEntity(CampaignDTO dto) {
        Campaign campaign = new Campaign();
        campaign.setCampaignName(dto.getCampaignName());
        campaign.setBidAmount(dto.getBidAmount());
        campaign.setCampaignFund(dto.getCampaignFund());
        campaign.setStatus(dto.getStatus());
        campaign.setRadius(dto.getRadius());
        return campaign;
    }
}