package ru.mrak.service.dto.userWord;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
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

    // Фильтры
    private StringFilter wordFilter;
    private StringFilter partOfSpeech;
    private IntegerFilter boxNumber;

    // Сортировки
    private StringRangeFilter startWord;
    private IntegerFilter startPriority;

    public UserWordCriteria() {
    }

    public UserWordCriteria(UserWordCriteria other) {
        this.wordFilter = other.wordFilter;
        this.partOfSpeech = other.partOfSpeech;

        this.startWord = other.startWord;
        this.startPriority = other.startPriority;
    }

    @Override
    public Criteria copy() {
        return new UserWordCriteria(this);
    }
}
