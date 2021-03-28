package ru.mrak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Word.
 */
@Entity
@Table(name = "word")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "translate")
    private String translate;

    @Column(name = "part_of_speech")
    private String partOfSpeech;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "url_picture")
    private String urlPicture;

    @Column(name = "url_audio")
    private String urlAudio;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public Word word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public Word translate(String translate) {
        this.translate = translate;
        return this;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public Word partOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
        return this;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        if (!(o instanceof Word)) {
            return false;
        }
        return id != null && id.equals(((Word) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Word{" +
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
