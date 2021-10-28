package ru.mrak.service.dto.userWord;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.model.enumeration.PartOfSpeechEnum;
import ru.mrak.web.rest.filter.StringRangeFilter;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringRangeFilter word;
    private Filter<PartOfSpeechEnum> partOfSpeech;
    private IntegerFilter boxNumber;

    public UserWordCriteria() {
    }

    public UserWordCriteria(UserWordCriteria other) {
        this.word = other.word;
        this.partOfSpeech = other.partOfSpeech;
        this.boxNumber = other.boxNumber;
    }

    @Override
    public Criteria copy() {
        return new UserWordCriteria(this);
    }
}
