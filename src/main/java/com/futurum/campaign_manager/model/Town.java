package com.futurum.campaign_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Town entity in the system.
 * <p>
 * A town contains a unique name, optional postal code, and can be associated with multiple campaigns.
 * The equality of towns is based solely on the town name.
 */

@Entity
@Table(name = "towns")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Town name is mandatory")
    @Column(name = "town_name", nullable = false, unique = true)
    private String townName;

    @Column(name = "postal_code")
    private String postalCode;

    @OneToMany(mappedBy = "town")
    private Set<Campaign> campaigns;

    public Town() {}

    public Town(String townName) {
        this.townName = townName;
    }

    public Town(String townName, String postalCode) {
        this.townName = townName;
        this.postalCode = postalCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTownName() { return townName; }
    public void setTownName(String townName) { this.townName = townName; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public Set<Campaign> getCampaigns() { return campaigns; }
    public void setCampaigns(Set<Campaign> campaigns) { this.campaigns = campaigns; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Town)) return false;
        Town town = (Town) o;
        return Objects.equals(townName, town.townName);
    }

    @Override
    public int hashCode() {
        return townName != null ? townName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return townName;
    }
}
