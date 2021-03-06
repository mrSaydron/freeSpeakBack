package ru.mrak.dto.userWord;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.RangeFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.controller.filter.StringRangeFilter;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringRangeFilter wordOrTranslate;
    private StringRangeFilter word;
    private RangeFilter<Integer> boxNumber;
    private RangeFilter<Long> priority;

    public UserWordCriteria() {
    }

    public UserWordCriteria(UserWordCriteria other) {
        this.wordOrTranslate = other.wordOrTranslate;
        this.word = other.word;
        this.boxNumber = other.boxNumber;
        this.priority = other.priority;
    }

    @Override
    public Criteria copy() {
        return new UserWordCriteria(this);
    }
}
