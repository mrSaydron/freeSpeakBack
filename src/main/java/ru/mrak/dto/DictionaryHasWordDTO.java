package ru.mrak.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link BookDictionaryHasWord} entity.
 */
public class DictionaryHasWordDTO implements Serializable {

    private Long id;
    private Integer count;
    private WordDto word;

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

    public WordDto getWord() {
        return word;
    }

    public void setWord(WordDto word) {
        this.word = word;
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
            "}";
    }
}
