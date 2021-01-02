package ru.mrak.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.mrak.domain.DictionaryHasWord} entity.
 */
public class DictionaryHasWordDTO implements Serializable {
    
    private Long id;

    private Integer count;


    private Long dictionaryId;

    private Long wordId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DictionaryHasWordDTO)) {
            return false;
        }

        return id != null && id.equals(((DictionaryHasWordDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DictionaryHasWordDTO{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", dictionaryId=" + getDictionaryId() +
            ", wordId=" + getWordId() +
            "}";
    }
}
