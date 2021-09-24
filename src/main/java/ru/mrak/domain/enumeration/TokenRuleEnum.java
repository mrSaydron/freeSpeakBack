package ru.mrak.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrak.domain.TokenLight;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Правила преобразования токена в слово
 */
@AllArgsConstructor
@Getter
public enum TokenRuleEnum {
    LEMMA((tokenLight, targetPOS) -> {
        tokenLight.setTag(TagEnum.getByTag(tokenLight.getToken().tag()));
        tokenLight.setWord(tokenLight.getToken().lemma());
    }),
    NO_LEMMA((tokenLight, targetPOS) -> {
        tokenLight.setTag(TagEnum.getByTag(tokenLight.getToken().tag()));
        tokenLight.setWord(tokenLight.getToken().word());
    }),
    NUMBER((tokenLight, targetPOS) -> {
        if (tokenLight.getToken().word().matches("[0-9]*")) {
            tokenLight.setTag(TagEnum.REMOVE);
        } else {
            tokenLight.setTag(TagEnum.getByTag(tokenLight.getToken().tag()));
            tokenLight.setWord(tokenLight.getToken().word());
        }
    }),
    REMOVE((tokenLight, targetPOS) -> {
        tokenLight.setTag(TagEnum.REMOVE);
    })
    ;

    private final BiConsumer<TokenLight, TagEnum> rule;

}
