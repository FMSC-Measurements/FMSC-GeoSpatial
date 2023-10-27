package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public enum MessageType {
    Unknown(0),
    Bin1(1), // GNSS position message (position and velocity data)
    Bin2(2), // GPS DOPs (Dilution of Precision)
    Bin3(3), // Lat/Lon/Hgt, Covariances, RMS, DOPs and COG, Speed, Heading
    Bin5(5), // Base station information
    Bin6(6), // Manual Mark Tag
    Bin16(16), // All constellation code and phase observation data
    Bin19(19), // GNSS diagnostic information
    Bin22(22), // QZSS Almanac
    Bin32(32), // BeiDou Almanac
    Bin34(34), // BeiDou -> GPS -> GLO -> GAL -> UTC time offset parameters
    Bin35(35), // BeiDou ephemeris information
    Bin36(36), // BeiDou code and carrier phase information (all frequencies)
    Bin42(42), // Galileo Almanac
    Bin44(44), // GALILEO time conversion
    Bin45(45), // GALILEO ephemeris
    Bin62(62), // GLONASS almanac information
    Bin65(65), // GLONASS ephemeris information
    Bin66(66), // GLONASS L1/L2 code and carrier phase information
    Bin69(69), // GLONASS L1/L2 diagnostic information
    Bin76(76), // GPS L1/L2 code and carrier phase information
    Bin80(80), // SBAS data frame information
    Bin89(89), // SBAS satellite tracking information
    Bin93(93), // SBAS ephemeris information
    Bin94(94), // Ionospheric and UTC conversion parameters
    Bin95(95), // GPS ephemeris information
    Bin96(96), // GPS L1 code and carrier phase information
    Bin97(97), // Processor statistics
    Bin98(98), // GPS satellite and almanac information
    Bin99(99), // GPS L1 diagnostic information
    Bin100(100), // GPS L2 diagnostic information
    Bin122(122), // Alternate position solution data
    Bin209(209); // SNR and status for all GNSS tracks

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageType parse(int value) {
        switch (value) {
            case 1:
                return Bin1;
            case 2:
                return Bin2;
            case 3:
                return Bin3;
            case 5:
                return Bin5;
            case 6:
                return Bin6;
            case 16:
                return Bin16;
            case 19:
                return Bin19;
            case 22:
                return Bin22;
            case 32:
                return Bin32;
            case 34:
                return Bin34;
            case 35:
                return Bin35;
            case 36:
                return Bin36;
            case 42:
                return Bin42;
            case 44:
                return Bin44;
            case 45:
                return Bin45;
            case 62:
                return Bin62;
            case 65:
                return Bin65;
            case 66:
                return Bin66;
            case 69:
                return Bin69;
            case 76:
                return Bin76;
            case 80:
                return Bin80;
            case 89:
                return Bin89;
            case 93:
                return Bin93;
            case 94:
                return Bin94;
            case 95:
                return Bin95;
            case 96:
                return Bin96;
            case 97:
                return Bin97;
            case 98:
                return Bin98;
            case 99:
                return Bin99;
            case 100:
                return Bin100;
            case 122:
                return Bin122;
            case 209:
                return Bin209;
            default:
                return Unknown;
        }
    }

    public static MessageType parseFromMessage(byte[] data) {

        int v = 0;
        try {
            v = ByteBuffer.wrap(data, 4, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parse(v);
    }
}
