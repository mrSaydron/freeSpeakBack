package ru.mrak.dto;

import java.util.List;

public class BookSentenceDTO {
    private Long id;
    private List<BookSentenceHasWordDTO> words;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookSentenceHasWordDTO> getWords() {
        return words;
    }

    public void setWords(List<BookSentenceHasWordDTO> words) {
        this.words = words;
    }
}
