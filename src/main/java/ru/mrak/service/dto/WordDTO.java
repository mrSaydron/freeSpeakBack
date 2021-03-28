package ru.mrak.service.dto;

import javax.validation.constraints.NotNull;
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
    private Long totalAmount;
    private String urlPicture;
    private String urlAudio;

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

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
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
            "id=" + id +
            ", word='" + word + '\'' +
            ", translate='" + translate + '\'' +
            ", partOfSpeech='" + partOfSpeech + '\'' +
            ", totalAmount=" + totalAmount +
            ", urlPicture='" + urlPicture + '\'' +
            ", urlAudio='" + urlAudio + '\'' +
            '}';
    }
}
