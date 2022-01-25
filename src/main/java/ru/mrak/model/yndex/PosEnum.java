package ru.mrak.model.yndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

import java.util.*;

@ToString
public enum PosEnum {
    adjective(PartOfSpeechEnum.JJ, PartOfSpeechEnum.JJR, PartOfSpeechEnum.JJS, PartOfSpeechEnum.PDT), // прилагательное: прилагательное, сравнительное прилагательное
    noun(PartOfSpeechEnum.NN, PartOfSpeechEnum.NNS, PartOfSpeechEnum.IN), // существительное: существительное, сущ. множественное число, предлог
    adverb(PartOfSpeechEnum.RB, PartOfSpeechEnum.RBR, PartOfSpeechEnum.RBS), // наречие
    verb(PartOfSpeechEnum.VB, PartOfSpeechEnum.VBD, PartOfSpeechEnum.VBG, PartOfSpeechEnum.VBN, PartOfSpeechEnum.VBP, PartOfSpeechEnum.VBZ, PartOfSpeechEnum.MD), // глагол
    participle(PartOfSpeechEnum.JJ), // причастие
    preposition(PartOfSpeechEnum.RP, PartOfSpeechEnum.IN), // предлог: частица?, предлог
    pronoun(PartOfSpeechEnum.PRP, PartOfSpeechEnum.PRPS, PartOfSpeechEnum.NN, PartOfSpeechEnum.WP, PartOfSpeechEnum.WPS, PartOfSpeechEnum.WDT), // местоимение: личное местоимение, притяжательное местоимение, существительное?, вопрос к местоимению, Вопрос определитель
    numeral(PartOfSpeechEnum.CD), // цифра
    conjunction(PartOfSpeechEnum.WRB), // соединение: вопрос к наречию
    particle(PartOfSpeechEnum.RP, PartOfSpeechEnum.RB), // частица
    parenthetic(), // отрывок
    predicative(), // предикатив
    interjection(PartOfSpeechEnum.UH), // междометие
    invariable(), // неизменный

    @JsonProperty("foreign word")
    foreignWord(), // иностранное слово

    @JsonProperty("adverbial participle")
    adverbialParticiple(), // наречное причастие
    ;

    private Set<PartOfSpeechEnum> tags = new HashSet<>();

    PosEnum(PartOfSpeechEnum... partOfSpeechEnums) {
        tags.addAll(Arrays.asList(partOfSpeechEnums));
    }

    public static Map<PartOfSpeechEnum, Set<PosEnum>> tagToPos = new HashMap<>();

    static {
        for (PosEnum value : PosEnum.values()) {
            for (PartOfSpeechEnum tag : value.tags) {
                tagToPos.computeIfAbsent(tag, (key) -> new HashSet<>()).add(value);
            }
        }
    }
}
