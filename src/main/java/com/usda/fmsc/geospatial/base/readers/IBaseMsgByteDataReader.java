package com.usda.fmsc.geospatial.base.readers;

import java.io.IOException;
import java.io.InputStream;

public interface IBaseMsgByteDataReader {
    void setStream(InputStream stream);

    byte[] readBytes() throws IOException;

    boolean available() throws IOException;
}
