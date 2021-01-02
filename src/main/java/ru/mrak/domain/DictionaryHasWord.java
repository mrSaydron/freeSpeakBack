package ru.mrak.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A DictionaryHasWord.
 */
@Entity
@Table(name = "dictionary_has_word")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryHasWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    @JsonIgnoreProperties(value = "dictionaryHasWords", allowSetters = true)
    private Dictionary dictionary;

    @ManyToOne
    @JsonIgnoreProperties(value = "dictionaryHasWords", allowSetters = true)
    private Word word;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public DictionaryHasWord count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public DictionaryHasWord dictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Word getWord() {
        return word;
    }

    public DictionaryHasWord word(Word word) {
        this.word = word;
        return this;
    }

    public void setWord(Word word) {
        this.word = word;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DictionaryHasWord)) {
            return false;
        }
        return id != null && id.equals(((DictionaryHasWord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DictionaryHasWord{" +
            "id=" + getId() +
            ", count=" + getCount() +
            "}";
    }
}
