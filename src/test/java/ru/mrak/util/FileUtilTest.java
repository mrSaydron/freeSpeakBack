package ru.mrak.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void getName() {
        String testString = "test.txt";
        String actual = FileUtil.getName(testString);
        Assertions.assertThat(actual).isEqualTo("test");
    }

    @Test
    void getNameNotDot() {
        String testString = "test";
        String actual = FileUtil.getName(testString);
        Assertions.assertThat(actual).isNull();
    }

    @Test
    void getNameSomeDot() {
        String testString = "test.test.txt";
        String actual = FileUtil.getName(testString);
        Assertions.assertThat(actual).isEqualTo("test.test");
    }

    @Test
    void getExtends() {
        String testString = "test.txt";
        String actual = FileUtil.getExtends(testString);
        Assertions.assertThat(actual).isEqualTo("txt");
    }

    @Test
    void getExtendsNotDot() {
        String testString = "test";
        String actual = FileUtil.getExtends(testString);
        Assertions.assertThat(actual).isNull();
    }

    @Test
    void getExtendsSomeDot() {
        String testString = "test.text.txt";
        String actual = FileUtil.getExtends(testString);
        Assertions.assertThat(actual).isEqualTo("txt");
    }
}
