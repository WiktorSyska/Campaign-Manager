package com.futurum.campaign_manager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Keyword entity in the system.
 * <p>
 * A keyword is a unique text label that can be associated with multiple campaigns.
 * Keywords are case-sensitive and must be unique across the system.
 * Equality of keywords is based solely on the keyword text.
 */
@Entity
@Table(name = "keywords")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Keyword text is mandatory")
    @Column(name = "keyword_text", nullable = false, unique = true)
    private String keywordText;

    @ManyToMany(mappedBy = "keywords")
    private Set<Campaign> campaigns;

    public Keyword() {}

    public Keyword(String keywordText) {
        this.keywordText = keywordText;
    }

    public Keyword(long id, String keywordText) {
        this.id = id;
        this.keywordText = keywordText;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKeywordText() { return keywordText; }
    public void setKeywordText(String keywordText) { this.keywordText = keywordText; }

    public Set<Campaign> getCampaigns() { return campaigns; }
    public void setCampaigns(Set<Campaign> campaigns) { this.campaigns = campaigns; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyword)) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(keywordText, keyword.keywordText);
    }

    @Override
    public int hashCode() {
        return keywordText != null ? keywordText.hashCode() : 0;
    }

    @Override
    public String toString() {
        return keywordText;
    }
}
