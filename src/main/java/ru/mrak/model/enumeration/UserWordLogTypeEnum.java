package ru.mrak.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum UserWordLogTypeEnum {
    ADD_TO_DICTIONARY(1, "слово добавлено в словарь пользователя"),
    REMOVE_FROM_DICTIONARY(2, "слово удалено из словаря пользователя"),
    SUCCESS(3, "пользователь верно ответил верно"),
    FAIL(4, "пользователь ответил неверно"),
    SUCCESS_FROM_TEST(5, "пользователь ответил верно в тесте"),
    FAIL_FROM_TEST(6, "пользователь ответил неверно в тесте"),
    KNOW_FROM_TEST(7, "по результату теста пользователь знает слово"),
    KNOW(8, "пользователь отметил, что знает слово"),
    DO_NOT_KNOW(9, "пользователь отметил, что не знает слово"),
    ;

    private final int id;
    private final String description;

    private static final Map<Integer, UserWordLogTypeEnum> byId
        = Stream.of(UserWordLogTypeEnum.values()).collect(Collectors.toMap(UserWordLogTypeEnum::getId, Function.identity()));

    public static UserWordLogTypeEnum of(int id) {
        return byId.get(id);
    }
}
