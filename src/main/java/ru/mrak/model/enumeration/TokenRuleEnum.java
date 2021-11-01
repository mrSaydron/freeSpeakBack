package ru.mrak.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.mrak.model.TokenLight;

import java.util.function.BiConsumer;

/**
 * Правила преобразования токена в слово
 */
@AllArgsConstructor
@Getter
public enum TokenRuleEnum {
    LEMMA((tokenLight, targetPOS) -> {
        tokenLight.setTag(targetPOS);
        tokenLight.setWord(tokenLight.getToken().lemma().toLowerCase());
    }),
    NO_LEMMA((tokenLight, targetPOS) -> {
        tokenLight.setTag(targetPOS);
        tokenLight.setWord(tokenLight.getToken().word().toLowerCase());
    }),
    NUMBER((tokenLight, targetPOS) -> {
        if (tokenLight.getToken().word().matches("[0-9]*")) {
            tokenLight.setTag(PartOfSpeechEnum.REMOVE);
        } else {
            tokenLight.setTag(targetPOS);
            tokenLight.setWord(tokenLight.getToken().word().toLowerCase());
        }
    }),
    REMOVE((tokenLight, targetPOS) -> {
        tokenLight.setTag(PartOfSpeechEnum.REMOVE);
    })
    ;

    private final BiConsumer<TokenLight, PartOfSpeechEnum> rule;

}
