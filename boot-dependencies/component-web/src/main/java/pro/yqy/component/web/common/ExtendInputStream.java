package pro.yqy.component.web.common;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ExtendInputStream extends ServletInputStream {

    private final ByteArrayInputStream byteArrayInputStream;

    public ExtendInputStream(byte[] buffer) {
        this.byteArrayInputStream = new ByteArrayInputStream(buffer);
    }

    @Override
    public int read() {
        return byteArrayInputStream.read();
    }

    @Override
    public boolean isFinished() {
        return byteArrayInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }

}
