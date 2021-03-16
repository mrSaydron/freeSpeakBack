package ru.mrak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "dictionary", fetch = FetchType.LAZY)
    private List<DictionaryHasWord> dictionaryWords;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public List<DictionaryHasWord> getDictionaryWords() {
        return dictionaryWords;
    }

    public void setDictionaryWords(List<DictionaryHasWord> dictionaryWords) {
        this.dictionaryWords = dictionaryWords;
    }

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
