package com.futurum.campaign_manager.model;

public enum CampaignStatus {
    ON("On"),
    OFF("Off");

    private final String displayName;

    CampaignStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}