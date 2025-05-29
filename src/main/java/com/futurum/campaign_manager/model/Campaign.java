package com.futurum.campaign_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents a Campaign entity in the system.
 * <p>
 * A campaign contains information about advertising campaigns including financial details,
 * target location, keywords, and status. Automatically tracks creation and modification timestamps.
 */
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campaign name is mandatory")
    @Column(name = "campaign_name", nullable = false)
    private String campaignName;

    @NotNull(message = "Bid amount is mandatory")
    @DecimalMin(value = "0.01", message = "Minimum bid amount is 0.01")
    @Column(name = "bid_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal bidAmount;

    @NotNull(message = "Campaign fund is mandatory")
    @DecimalMin(value = "0.01", message = "Minimum campaign fund is 0.01")
    @Column(name = "campaign_fund", nullable = false, precision = 10, scale = 2)
    private BigDecimal campaignFund;

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CampaignStatus status;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;

    @NotNull(message = "Radius is mandatory")
    @Min(value = 1, message = "Minimum radius is 1 km")
    @Column(name = "radius", nullable = false)
    private Integer radius;

    @ManyToMany
    @JoinTable(
            name = "campaign_keywords",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Campaign() {
    }

    public Campaign(String campaignName, BigDecimal bidAmount, BigDecimal campaignFund,
                    CampaignStatus status, Town town, Integer radius, Set<Keyword> keywords) {
        this.campaignName = campaignName;
        this.bidAmount = bidAmount;
        this.campaignFund = campaignFund;
        this.status = status;
        this.town = town;
        this.radius = radius;
        this.keywords = keywords;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
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