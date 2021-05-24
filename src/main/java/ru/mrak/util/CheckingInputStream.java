package ru.mrak.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * Класс обертка над входным потоков. Проверяет входной поток, если потк не проходит проверку возвращает -1 при чтении
 */
public class CheckingInputStream extends InputStream {
    private byte[] buffer = new byte[1024];
    private int bufLen = 0;
    private int bufIndex = 0;
    private boolean checked = false;
    private InputStream wrapped;
    private Function<CheckingInputStream, Boolean> checkStream;

    public CheckingInputStream(InputStream wrapped, Function<CheckingInputStream, Boolean> checkStream) {
        this.wrapped = wrapped;
        this.checkStream = checkStream;
    }

    @Override
    public int read() throws IOException {
        if (!checked) {
            checked = true;
            bufLen = wrapped.read(buffer);
            if(!checkStream.apply(this)) {
                return -1;
            }
        }
        if (bufIndex < bufLen) {
            return buffer[bufIndex++] & 0xFF;
        } else {
            return wrapped.read();
        }
    }

    public byte[] getBuffer() {
        return buffer;
    }
}
