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
        tokenLight.setTag(PartOfSpeechEnum.getByTag(tokenLight.getToken().tag()));
        tokenLight.setWord(tokenLight.getToken().lemma());
    }),
    NO_LEMMA((tokenLight, targetPOS) -> {
        tokenLight.setTag(PartOfSpeechEnum.getByTag(tokenLight.getToken().tag()));
        tokenLight.setWord(tokenLight.getToken().word());
    }),
    NUMBER((tokenLight, targetPOS) -> {
        if (tokenLight.getToken().word().matches("[0-9]*")) {
            tokenLight.setTag(PartOfSpeechEnum.REMOVE);
        } else {
            tokenLight.setTag(PartOfSpeechEnum.getByTag(tokenLight.getToken().tag()));
            tokenLight.setWord(tokenLight.getToken().word());
        }
    }),
    REMOVE((tokenLight, targetPOS) -> {
        tokenLight.setTag(PartOfSpeechEnum.REMOVE);
    })
    ;

    private final BiConsumer<TokenLight, PartOfSpeechEnum> rule;

}
