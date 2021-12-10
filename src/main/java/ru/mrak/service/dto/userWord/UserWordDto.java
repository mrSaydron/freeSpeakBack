package ru.mrak.service.dto.userWord;

import ru.mrak.service.dto.WordDto;

import java.util.List;

public class UserWordDto {

    private Long id;
    private WordDto word;
    private List<WordProgressDTO> wordProgresses;

    public UserWordDto() {
    }

    public UserWordDto(Long id, WordDto word, List<WordProgressDTO> wordProgresses) {
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

    public WordDto getWord() {
        return word;
    }

    public void setWord(WordDto word) {
        this.word = word;
    }

    public List<WordProgressDTO> getWordProgresses() {
        return wordProgresses;
    }

    public void setWordProgresses(List<WordProgressDTO> wordProgresses) {
        this.wordProgresses = wordProgresses;
    }

}
