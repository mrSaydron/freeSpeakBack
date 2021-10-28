package ru.mrak.service.dto.userWord;

import ru.mrak.service.dto.WordDTO;

import java.util.List;

public class UserWordDTO {

    private Long id;
    private WordDTO word;
    private List<WordProgressDTO> wordProgresses;

    public UserWordDTO() {
    }

    public UserWordDTO(Long id, WordDTO word, List<WordProgressDTO> wordProgresses) {
        this.id = id;
        this.word = word;
        this.wordProgresses = wordProgresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WordDTO getWord() {
        return word;
    }

    public void setWord(WordDTO word) {
        this.word = word;
    }

    public List<WordProgressDTO> getWordProgresses() {
        return wordProgresses;
    }

    public void setWordProgresses(List<WordProgressDTO> wordProgresses) {
        this.wordProgresses = wordProgresses;
    }

}
