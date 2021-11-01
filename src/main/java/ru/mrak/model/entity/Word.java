package ru.mrak.model.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "word_seq")
    @SequenceGenerator(name = "word_seq", sequenceName = "word_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "translate")
    private String translate;

    @Column(name = "part_of_speech")
    @Enumerated(EnumType.STRING)
    private PartOfSpeechEnum partOfSpeech;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "audio_id")
    private String audioId;

    @Column(name = "transcription")
    private String transcription;

    @NotNull
    @Column(name = "audio_file_requested")
    private boolean audioFileRequested;

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

    public PartOfSpeechEnum getPartOfSpeech() {
        return partOfSpeech;
    }

    public Word partOfSpeech(PartOfSpeechEnum partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
        return this;
    }

    public void setPartOfSpeech(PartOfSpeechEnum partOfSpeech) {
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

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public boolean isAudioFileRequested() {
        return audioFileRequested;
    }

    public void setAudioFileRequested(boolean audioFileRequested) {
        this.audioFileRequested = audioFileRequested;
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
            ", urlAudio='" + audioId + '\'' +
            ", transcription='" + transcription + '\'' +
            ", audioFileRequested='" + audioFileRequested + '\'' +
            '}';
    }
}
