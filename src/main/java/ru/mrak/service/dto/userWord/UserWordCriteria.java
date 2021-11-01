package ru.mrak.service.dto.userWord;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.StringFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.web.rest.filter.StringRangeFilter;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter wordOrTranslate;
    private StringRangeFilter word;

    public UserWordCriteria() {
    }

    public UserWordCriteria(UserWordCriteria other) {
        this.wordOrTranslate = other.wordOrTranslate;
        this.word = other.word;
    }

    @Override
    public Criteria copy() {
        return new UserWordCriteria(this);
    }
}
