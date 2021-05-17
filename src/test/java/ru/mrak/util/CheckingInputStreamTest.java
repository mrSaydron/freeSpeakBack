package ru.mrak.util;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CheckingInputStreamTest {

    @Test
    public void validTest() throws IOException {
        Function<CheckingInputStream, Boolean> checkFunction = stream -> {
            return true;
        };

        String test = "test";
        InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
        CheckingInputStream checkingInputStream = new CheckingInputStream(stream, checkFunction);
        int actual = checkingInputStream.read();

        Assertions.assertThat(actual)
    }

}
