package com.usda.fmsc.geospatial.base.readers;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseMsgByteDataReader implements IBaseMsgByteDataReader {
    private static final int DEFAULT_FILL_SIZE = 256;

    private InputStream stream;
    private int fillSize = DEFAULT_FILL_SIZE;

    private byte[] data;

    public BaseMsgByteDataReader() {
        this.stream = null;
    }

    public BaseMsgByteDataReader(InputStream stream) {
        this.stream = stream;
    }

    public BaseMsgByteDataReader(InputStream stream, int fillSize) {
        this.stream = stream;
        this.fillSize = fillSize;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public abstract byte[] readBytes() throws IOException;

    public boolean available() throws IOException  {
        return stream.available() > 0;
    }

    static int lastFill = 0;

    protected byte[] fill() throws IOException {
        if (stream == null) {
            throw new RuntimeException("Stream not set");
        }

        if (data != null && data.length > fillSize)
            return data;

        int available = stream.available();

        if (available > 0) {
            if (data == null) {
                data = new byte[available];
                stream.read(data, 0, available);
            } else {
                byte[] dataNew = new byte[data.length + available];
                System.arraycopy(data, 0, dataNew, 0, data.length);
                stream.read(dataNew, data.length, available);
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
