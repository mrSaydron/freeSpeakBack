package ru.mrak.model.enumeration;

public enum ServiceDataKeysEnum {
    totalWords, // Сумма всех слов из всех книг
    TEST_VOCABULARY_FIRST_STEP_SIZE, // Размер первого шага для тестирования словарного запаса
    TEST_VOCABULARY_BASE_STEP_SIZE, // Размер первого шага для тестирования словарного запаса
    TEST_VOCABULARY_MAX_FAIL_COUNT, // Колличество не правильных ответов, после которого определяется уровень словарного запаса
    TEST_VOCABULARY_RANDOM_SIZE, // Размер случайного отклонения при расчете шага
}
