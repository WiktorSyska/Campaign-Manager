package com.futurum.campaign_manager.dto;

import jakarta.validation.constraints.NotBlank;
/**
 * Data Transfer Object (DTO) representing town information.
 * <p>
 * This class is used to transfer town-related data between different layers of the application.
 * It contains basic town information including identifier, name, and postal code.
 */
public class TownDTO {

    private Long id;

    @NotBlank(message = "Town name is mandatory")
    private String townName;

    private String postalCode;

    public TownDTO() {}

    public TownDTO(Long id, String townName, String postalCode) {
        this.id = id;
        this.townName = townName;
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
