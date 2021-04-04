package ru.mrak.service.dto.userWord;

import ru.mrak.domain.enumeration.UserWordProgressTypeEnum;

import java.util.Objects;

public class WordProgressDTO {

    private Long id;
    private UserWordProgressTypeEnum type;
    private int successfulAttempts;
    private int boxNumber;

    public WordProgressDTO() {
    }

    public WordProgressDTO(Long id, UserWordProgressTypeEnum type, int successfulAttempts, int boxNumber) {
        this.id = id;
        this.type = type;
        this.successfulAttempts = successfulAttempts;
        this.boxNumber = boxNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserWordProgressTypeEnum getType() {
        return type;
    }

    public void setType(UserWordProgressTypeEnum type) {
        this.type = type;
    }

    public int getSuccessfulAttempts() {
        return successfulAttempts;
    }

    public void setSuccessfulAttempts(int successfulAttempts) {
        this.successfulAttempts = successfulAttempts;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordProgressDTO that = (WordProgressDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "WordProgressDTO{" +
            "id=" + id +
            ", type=" + type +
            ", successfulAttempts=" + successfulAttempts +
            ", boxNumber=" + boxNumber +
            '}';
    }
}
