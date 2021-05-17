package ru.mrak.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

public class CheckingInputStream extends InputStream {
    private byte[] buffer = new byte[1024];
    private int bufLen = 0;
    private int bufIndex = 0;
    private boolean isContentValid = false;
    private boolean checked = false;
    private InputStream wrapped;
    private Function<CheckingInputStream, Boolean> checkStream;

    public CheckingInputStream(InputStream wrapped, Function<CheckingInputStream, Boolean> checkStream) {
        this.wrapped = wrapped;
        this.checkStream = checkStream;
    }

    @Override
    public int read() throws IOException {
        bufLen = wrapped.read(buffer);
        if (!checked) {
            isContentValid = checkStream.apply(this);
        }
        int result = -1;
        if (isContentValid) {
            if (bufIndex < bufLen) {
                result = buffer[bufIndex++] & 0xFF;
            } else {
                result = wrapped.read();
            }
        }
        return result;
    }

    public byte[] getBuffer() {
        return buffer;
    }
}
