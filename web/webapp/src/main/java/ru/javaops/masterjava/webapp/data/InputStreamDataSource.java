package ru.javaops.masterjava.webapp.data;

import javax.activation.DataSource;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamDataSource implements DataSource {

    private final InputStream inputStream;

    public InputStreamDataSource(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public String getContentType() {
        return "*/*";
    }

    @Override
    public String getName() {
        return null;
    }
}
