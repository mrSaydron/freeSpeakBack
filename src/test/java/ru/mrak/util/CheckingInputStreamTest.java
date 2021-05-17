package ru.mrak.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class CheckingInputStreamTest {

    @Test
    public void validTest() throws IOException {
        Function<CheckingInputStream, Boolean> checkFunction = stream -> {
            String s = new String(stream.getBuffer(), Charset.defaultCharset());
            return s.substring(0, 4).equals("test");
        };

        String test = "test";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        CheckingInputStream checkingInputStream = new CheckingInputStream(stream, checkFunction);
        int actual = checkingInputStream.read();

        Assertions.assertThat(actual).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void notValidTest() throws IOException {
        Function<CheckingInputStream, Boolean> checkFunction = stream -> {
            String s = new String(stream.getBuffer(), Charset.defaultCharset());
            return s.substring(0, 4).equals("test");
        };

        String test = "noValid";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        CheckingInputStream checkingInputStream = new CheckingInputStream(stream, checkFunction);
        int actual = checkingInputStream.read();

        Assertions.assertThat(actual).isEqualTo(-1);
    }

}
