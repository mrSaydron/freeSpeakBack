package ru.mrak.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrak.domain.TokenLight;

import java.util.function.Consumer;

/**
 * Правила преобразования токена в слово
 */
@AllArgsConstructor
@Getter
public enum TokenRuleEnum {
    LEMMA(tokenLight -> {
        tokenLight.setTag(TagEnum.getByTag(tokenLight.getToken().tag()));
        tokenLight.setWord(tokenLight.getToken().lemma());
    }),
    NO_LEMMA(tokenLight -> {
        tokenLight.setTag(TagEnum.getByTag(tokenLight.getToken().tag()));
        tokenLight.setWord(tokenLight.getToken().word());
    }),
    ;

    private final Consumer<TokenLight> rule;

}
