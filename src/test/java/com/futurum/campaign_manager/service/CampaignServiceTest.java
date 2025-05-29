package com.futurum.campaign_manager.service;

import com.futurum.campaign_manager.dto.CampaignDTO;
import com.futurum.campaign_manager.model.Campaign;
import com.futurum.campaign_manager.model.CampaignStatus;
import com.futurum.campaign_manager.model.Keyword;
import com.futurum.campaign_manager.model.Town;
import com.futurum.campaign_manager.repository.CampaignRepository;
import com.futurum.campaign_manager.repository.KeywordRepository;
import com.futurum.campaign_manager.repository.TownRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private TownRepository townRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CampaignService campaignService;

    @Test
    void createCampaign_ShouldSuccess() {
        CampaignDTO dto = new CampaignDTO();
        dto.setCampaignName("Test");
        dto.setBidAmount(new BigDecimal("10.00"));
        dto.setCampaignFund(new BigDecimal("100.00"));
        dto.setStatus(CampaignStatus.ON);
        dto.setRadius(10);
        dto.setKeywordIds(Set.of(1L));
        dto.setTownId(1L);

        when(accountService.hasEnoughFunds(anyString(), any())).thenReturn(true);
        when(townRepository.findById(anyLong())).thenReturn(Optional.of(new Town()));
        when(keywordRepository.findById(anyLong())).thenReturn(Optional.of(new Keyword()));
        when(campaignRepository.save(any())).thenReturn(new Campaign());

        CampaignDTO result = campaignService.createCampaign(dto);

        assertNotNull(result);
        verify(campaignRepository).save(any());
    }
}
