package ru.mrak.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class FileUtilTest {

    @Test
    void getName() {
        String testString = "test.txt";
        Optional<String> actual = FileUtil.getName(testString);
        Assertions.assertThat(actual).hasValue("test");
    }

    @Test
    void getNameNotDot() {
        String testString = "test";
        Optional<String> actual = FileUtil.getName(testString);
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void getNameSomeDot() {
        String testString = "test.test.txt";
        Optional<String> actual = FileUtil.getName(testString);
        Assertions.assertThat(actual).hasValue("test.test");
    }

    @Test
    void getExtends() {
        String testString = "test.txt";
        Optional<String> actual = FileUtil.getExtends(testString);
        Assertions.assertThat(actual).hasValue("txt");
    }

    @Test
    void getExtendsNotDot() {
        String testString = "test";
        Optional<String> actual = FileUtil.getExtends(testString);
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    void getExtendsSomeDot() {
        String testString = "test.text.txt";
        Optional<String> actual = FileUtil.getExtends(testString);
        Assertions.assertThat(actual).hasValue("txt");
    }
}
