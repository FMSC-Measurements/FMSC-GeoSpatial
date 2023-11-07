package com.usda.fmsc.geospatial.base.readers;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseMsgByteDataReader {
    private InputStream stream;

    private byte[] data;

    public BaseMsgByteDataReader() {
        this.stream = null;
    }

    public BaseMsgByteDataReader(InputStream stream) {
        this.stream = stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public abstract byte[] readBytes() throws IOException;

    public boolean available() throws IOException  {
        return stream.available() > 0;
    }

    protected byte[] fill() throws IOException {
        if (stream == null) {
            throw new RuntimeException("Stream not set");
        }

        int available = stream.available();

        if (available > 0) {
            if (data == null) {
                data = new byte[available];
                stream.read(data);
            } else {
                byte[] buffer = new byte[available];
                stream.read(buffer);

                byte[] dataNew = new byte[data.length + buffer.length];

                System.arraycopy(data, 0, dataNew, 0, data.length);
                System.arraycopy(buffer, 0, dataNew, data.length, buffer.length);

                data = dataNew;
            }

            return data;
        }
        
        return new byte[0];
    }

    protected boolean dequeue(int length) {
        if (data.length > length) {
            byte[] dataNew = new byte[data.length - length];

            System.arraycopy(data, length, dataNew, 0, data.length - length);

            data = dataNew;
            return true;
        } else if (data.length == length) {
            data = new byte[0];
        }

        return false;
    }
}
