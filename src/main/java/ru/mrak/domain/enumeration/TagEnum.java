package ru.mrak.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum TagEnum {
    CC("CC", "coordin. conjuction", "and, but, or"), // Соединитель
    CD("CD", "cardinal number", "one, two"), // Количественное числительное
    DT("DT", "determiner", "a, the"), // Определитьель
    EX("EX", "existential 'there'", "there"), // Экзистенциальное 'там' ???
    FW("FW", "foreign word", "mea culpa"), // Иностранное слово
    IN("IN", "preposition/sub-conj", "of, in, by"), // Предлог/подтекст
    JJ("JJ", "adjective", "yellow"), // Прилагательное
    JJR("JJR", "adj., comparative", "bigger"), // Прилагательное сравнительное
    JJS("JJS", "adj., superlative", "wildest"), // Прилагательное, превосходная степень
    LS("LS", "list item marker", "1, 2, One"), // Номер в списке
    MD("MD", "modal", "can, should"), // Модальный глагол
    NN("NN", "noun, sing. or mass", "llama"), // Существительное
    NNS("NNS", "noun, plural", "llamas"), // Существительное, множественное число
    NNP("NNP", "proper noun, sing", "IBM"), // Наименование
    NNPS("NNPS", "proper noun, plural", "Carolinas"), // Наименование, множественное число
    PDT("PDT", "predeterminer", "all, both"), // Предопределитель ???
    POS("POS", "possessive ending", "`s"), // Притяжательное окончание
    PRP("PRP", "personal pronoun", "I, you, he"), // Личное местоимение
    PRPS("PRP$", "possessive pronoun", "your, one`s"), // Притяжательное местоимение
    RB("RB", "adverb", "quickly, never"), // Наречие
    RBR("RBR", "adverb, comparative", "faster"), // Наречие, сравниетльная степень
    RBS("RBS", "adverb, superlative", "fastest"), // Наречие, превосходная степень
    RP("RP", "particle", "up, off"), // Частица
    SYM("SYM", "symbol", "+, %, &"), // Символ
    TO("TO", "to", "to"), // ???
    UH("UH", "interjection", "ah, oops"), // Междометие
    VB("VB", "verb base form", "eat"), // Глагол, базовая форма
    VBD("VBD", "verb past tense", "ate"), // Глагол в прошедшей форме
    VBG("VBG", "verb gerund", "eating"), // Глагор, герундий
    VBN("VBN", "verb past participle", "eaten"), // Глагол причастие в прошедшем времени
    VBP("VBP", "verb non-3sg pres", "eat"), // Глагол ???
    VBZ("VBZ", "verb 3sg pers", "eats"), // Глагол ???
    WDT("WDT", "wh-determiner", "which, that"), // Вопрос определитель
    WP("WP", "wh-pronoun", "what, who"), // Вопрос к местоимению
    WPS("WP$", "possessive wh-", "whose"), // Вопрос к притяжательному местоимению
    WRB("WRB", "wh-adverb", "how, where"), // Вопрос к наречию
    DOLLAR_SIGN("$", "dollar sign", "$"),
    POUND_SIGN("#", "pound sign", "#"),
    LEFT_QUOTE("" + '\u201C', "left quote", "" + '\u201C'),
    RIGHT_QUOTE("" + '\u201D', "right quote", "" + '\u201D'),
    LEFT_PARENTHESIS("(", "left parenthesis", "[, (, {, <"),
    RIGHT_PARENTHESIS(")", "right parenthesis", "], ), }, >"),
    COMMA(",", "comma", ","),
    FINAL_PUNC(".", "sentence-final punc", ". ! ?"),
    SENTENCE_PUNC(":", "mid-sentence punc", ": ; ... - --"),
    ;

    private String tag;
    private String description;
    private String example;

    public static final Map<String, TagEnum> byTag;
    public static final Set<TagEnum> filterTags = new HashSet<>(
        Arrays.asList(CD, EX, IN, JJ, JJR, JJS, MD, NN, NNS, PDT, PRP, PRPS, RB, RBR, RBS, RP, VB, VBD, VBG, VBN, VBP, VBZ, WDT, WP, WPS, WRB)
    );

    static {
        byTag = Arrays.stream(TagEnum.values()).collect(Collectors.toMap(item -> item.tag, Function.identity()));
    }
}
