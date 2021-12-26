package ru.mrak.dto;

public class BookSentenceHasWordDTO {
    private String word;
    private String afterWord;
    private Long translateId;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAfterWord() {
        return afterWord;
    }

    public void setAfterWord(String afterWord) {
        this.afterWord = afterWord;
    }

    public Long getTranslateId() {
        return translateId;
    }

    public void setTranslateId(Long translateId) {
        this.translateId = translateId;
    }
}
