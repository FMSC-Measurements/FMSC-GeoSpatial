package com.usda.fmsc.geospatial.base.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MsgByteReader extends BaseMsgByteDataReader {
    public static final byte[] DEFAULT_DELIMETER = new byte[] { 0x0D, 0x0A };

    private byte[] delimiter = DEFAULT_DELIMETER;


    public MsgByteReader(InputStream stream) {
        super(stream);
    }

    public MsgByteReader(InputStream stream, byte[] delimiter) {
        super(stream);
        this.delimiter = delimiter;
    }


    public byte[] readBytes() throws IOException {
        return readBytes(delimiter);
    }

    public byte[] readBytes(byte[] delimiter) throws IOException {
        byte[] data = fill();

        if (data.length < 1)
            return new byte[0];

        int idx = 0;
        for (; idx < data.length; idx++) {
            if (data[idx] == delimiter[0]) {
                int dsIdx = idx + 1, dIdx = 1;

                while (dIdx < delimiter.length && dsIdx < data.length) {
                    if (data[dsIdx++] == delimiter[dIdx]) {
                        if (dIdx == delimiter.length - 1) {
                            return Arrays.copyOf(data, idx);
                        }
                        dIdx++;
                    } else {
                        break;
                    }
                }
            }
        }

        return new byte[0];
    }
}
