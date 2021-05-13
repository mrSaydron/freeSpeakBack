package ru.mrak.domain.yndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import ru.mrak.domain.enumeration.TagEnum;

import java.util.*;

@ToString
public enum PosEnum {
    adjective(TagEnum.JJ, TagEnum.JJR, TagEnum.JJS, TagEnum.PDT), // прилагательное: прилагательное, сравнительное прилагательное
    noun(TagEnum.NN, TagEnum.NNS, TagEnum.IN), // существительное: существительное, сущ. множественное число, предлог
    adverb(TagEnum.RB, TagEnum.RBR, TagEnum.RBS), // наречие
    verb(TagEnum.VB, TagEnum.VBD, TagEnum.VBG, TagEnum.VBN, TagEnum.VBP, TagEnum.VBZ, TagEnum.MD), // глагол
    participle(TagEnum.JJ), // причастие
    preposition(TagEnum.RP, TagEnum.IN), // предлог: частица?, предлог
    pronoun(TagEnum.PRP, TagEnum.PRPS, TagEnum.NN, TagEnum.WP, TagEnum.WPS, TagEnum.WDT), // местоимение: личное местоимение, притяжательное местоимение, существительное?, вопрос к местоимению, Вопрос определитель
    numeral(TagEnum.CD), // цифра
    conjunction(TagEnum.WRB), // соединение: вопрос к наречию
    particle(TagEnum.RP, TagEnum.RB), // частица
    parenthetic(), // отрывок
    predicative(), // предикатив
    interjection(TagEnum.UH), // междометие
    invariable(), // неизменный

    @JsonProperty("foreign word")
    foreignWord(), // иностранное слово
    ;

    private Set<TagEnum> tags = new HashSet<>();

    PosEnum(TagEnum... tagEnums) {
        tags.addAll(Arrays.asList(tagEnums));
    }

    public static Map<TagEnum, Set<PosEnum>> tagToPos = new HashMap<>();

    static {
        for (PosEnum value : PosEnum.values()) {
            for (TagEnum tag : value.tags) {
                tagToPos.computeIfAbsent(tag, (key) -> new HashSet<>()).add(value);
            }
        }
    }
}
