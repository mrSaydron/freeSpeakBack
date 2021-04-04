package ru.mrak.service.dto.userWord;

import ru.mrak.service.dto.WordDTO;

import java.util.List;

public class UserWordDTO {

    private Long id;
    private WordDTO word;
    private List<WordProgressDTO> progress;
    private Integer priority;

    public UserWordDTO() {
    }

    public UserWordDTO(WordDTO word, List<WordProgressDTO> progress, Integer priority) {
        this.word = word;
        this.progress = progress;
        this.priority = priority;
    }

    public WordDTO getWord() {
        return word;
    }

    public void setWord(WordDTO word) {
        this.word = word;
    }

    public List<WordProgressDTO> getProgress() {
        return progress;
    }

    public void setProgress(List<WordProgressDTO> progress) {
        this.progress = progress;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


}
