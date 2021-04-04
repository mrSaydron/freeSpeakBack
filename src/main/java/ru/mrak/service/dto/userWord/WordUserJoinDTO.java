package ru.mrak.service.dto.userWord;

import lombok.ToString;
import ru.mrak.domain.Word;

@ToString
public class WordUserJoinDTO {
    private Word word;
    private Boolean userHas;

    public WordUserJoinDTO() {
    }

    public WordUserJoinDTO(Word word, Boolean userHas) {
        this.word = word;
        this.userHas = userHas;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Boolean getUserHas() {
        return userHas;
    }

    public void setUserHas(Boolean userHas) {
        this.userHas = userHas;
    }
}
