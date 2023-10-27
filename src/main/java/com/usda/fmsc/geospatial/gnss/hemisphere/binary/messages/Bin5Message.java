package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;

/*
 * Base station information
 */
public class Bin5Message extends BaseBinMessage {
    protected static final int LATITUDE_IDX = HEADER_SIZE; // double
    protected static final int LONGITUDE_IDX = LATITUDE_IDX + 8; // double
    protected static final int HEIGHT_IDX = LONGITUDE_IDX + 8; // float
    protected static final int BASE_ID_IDX = HEIGHT_IDX + 4; // ushort
    protected static final int SPARE_1 = BASE_ID_IDX + 2; // ushort
    protected static final int DIFF_FORMAT_IDX = SPARE_1 + 2; // char[16]
    protected static final int SPARE_2 = DIFF_FORMAT_IDX + 16; // ushort[16]

    public Bin5Message(byte[] message) {
        super(message);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.Bin5;
    }

    /*
     * Latitude of base station in degrees north
     */
    public double getLatitude() {
        return _message.getDouble(LATITUDE_IDX);
    }

    /*
     * Longitude of base station in degrees north
     */
    public double getLongitude() {
        return _message.getDouble(LONGITUDE_IDX);
    }

    /*
     * Base station altitude in meters
     */
    public float getHeight() {
        return _message.getFloat(HEIGHT_IDX);
    }

    /*
     * Base station ID
     */
    public int getBaseID() {
        return _message.getShort(BASE_ID_IDX) & 0xFFFF;
    }

    /*
     * String giving the format of the differential (i.e. RTCM3)
     */
    public String getDiffFormat() {
        return new String(Arrays.copyOfRange(_message.array(), DIFF_FORMAT_IDX, DIFF_FORMAT_IDX + 16),
                StandardCharsets.UTF_8).trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Bin5 [Base Station Info]\n");
        sb.append(String.format("  ID: %s\n", getBaseID()));
        sb.append(String.format("  Lat: %.8f\n", getLatitude()));
        sb.append(String.format("  Lon: %.8f\n", getLongitude()));
        sb.append(String.format("  Height: %.2f\n", getHeight()));
        sb.append(String.format("  Format: %s\n", getDiffFormat()));

        return sb.toString();
    }
}
