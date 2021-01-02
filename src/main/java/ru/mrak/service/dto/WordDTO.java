package ru.mrak.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.mrak.domain.Word} entity.
 */
public class WordDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String word;

    private String translate;

    private String partOfSpeech;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordDTO)) {
            return false;
        }

        return id != null && id.equals(((WordDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordDTO{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", translate='" + getTranslate() + "'" +
            ", partOfSpeech='" + getPartOfSpeech() + "'" +
            "}";
    }
}
