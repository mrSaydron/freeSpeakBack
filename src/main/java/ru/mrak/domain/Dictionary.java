package ru.mrak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Dictionary.
 */
@Entity
@Table(name = "dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_language")
    private String baseLanguage;

    @Column(name = "targer_language")
    private String targerLanguage;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public Dictionary baseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
        return this;
    }

    public void setBaseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public String getTargerLanguage() {
        return targerLanguage;
    }

    public Dictionary targerLanguage(String targerLanguage) {
        this.targerLanguage = targerLanguage;
        return this;
    }

    public void setTargerLanguage(String targerLanguage) {
        this.targerLanguage = targerLanguage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dictionary)) {
            return false;
        }
        return id != null && id.equals(((Dictionary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dictionary{" +
            "id=" + getId() +
            ", baseLanguage='" + getBaseLanguage() + "'" +
            ", targerLanguage='" + getTargerLanguage() + "'" +
            "}";
    }
}
