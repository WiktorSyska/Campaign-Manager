package com.futurum.campaign_manager.dto;

import com.futurum.campaign_manager.model.CampaignStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
/**
 * Data Transfer Object representing a marketing campaign.
 * <p>
 * Contains all necessary information for creating and managing advertising campaigns,
 * including financial details, targeting parameters, and status information.
 * </p>
 *
 * <p>
 * The class includes strict validation constraints:
 * <ul>
 *   <li>Campaign name is mandatory</li>
 *   <li>Bid amount and campaign fund must be at least 0.01</li>
 *   <li>Status is required</li>
 *   <li>Targeting radius must be at least 1 km</li>
 *   <li>At least one keyword must be specified</li>
 * </ul>
 * </p>
 */
public class CampaignDTO {
    private Long id;

    @NotBlank(message = "Campaign name is mandatory")
    private String campaignName;

    @NotNull(message = "Bid amount is mandatory")
    @DecimalMin(value = "0.01", message = "Minimum bid amount is 0.01")
    private BigDecimal bidAmount;

    @NotNull(message = "Campaign fund is mandatory")
    @DecimalMin(value = "0.01", message = "Minimum campaign fund is 0.01")
    private BigDecimal campaignFund;

    @NotNull(message = "Status is mandatory")
    private CampaignStatus status;

    private Long townId;
    private String townName;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 1, message = "Minimum radius is 1 km")
    private Integer radius;

    @NotEmpty(message = "At least one keyword is required")
    private Set<Long> keywordIds;

    private Set<String> keywordTexts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CampaignDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public BigDecimal getCampaignFund() {
        return campaignFund;
    }

    public void setCampaignFund(BigDecimal campaignFund) {
        this.campaignFund = campaignFund;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Set<Long> getKeywordIds() {
        return keywordIds;
    }

    public void setKeywordIds(Set<Long> keywordIds) {
        this.keywordIds = keywordIds;
    }

    public Set<String> getKeywordTexts() {
        return keywordTexts;
    }

    public void setKeywordTexts(Set<String> keywordTexts) {
        this.keywordTexts = keywordTexts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
