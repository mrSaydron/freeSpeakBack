package ru.mrak.domain;

import edu.stanford.nlp.ling.CoreLabel;
import lombok.Getter;
import lombok.Setter;
import ru.mrak.domain.enumeration.TagEnum;

import java.util.Objects;

/**
 * Обертка для словарного токена
 * Сравнение производится по полям итогового слова
 */
@Getter
@Setter
public class TokenLight {
    private CoreLabel token;

    private String word;
    private TagEnum tag;
    private boolean done;

    public TokenLight(CoreLabel token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenLight that = (TokenLight) o;
        return Objects.equals(word, that.word) &&
            Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, tag);
    }
}
