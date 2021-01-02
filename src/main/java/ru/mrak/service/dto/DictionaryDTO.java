package ru.mrak.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.mrak.domain.Dictionary} entity.
 */
public class DictionaryDTO implements Serializable {
    
    private Long id;

    private String baseLanguage;

    private String targerLanguage;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public String getTargerLanguage() {
        return targerLanguage;
    }

    public void setTargerLanguage(String targerLanguage) {
        this.targerLanguage = targerLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DictionaryDTO)) {
            return false;
        }

        return id != null && id.equals(((DictionaryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DictionaryDTO{" +
            "id=" + getId() +
            ", baseLanguage='" + getBaseLanguage() + "'" +
            ", targerLanguage='" + getTargerLanguage() + "'" +
            "}";
    }
}
