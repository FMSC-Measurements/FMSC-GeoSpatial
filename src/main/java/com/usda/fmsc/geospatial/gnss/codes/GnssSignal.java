package com.usda.fmsc.geospatial.gnss.codes;

public enum GnssSignal {
    Unknown(0),

    GPS_UNKNOWN(1),
    GPS_ALL(2),
    GPS_L1(3),
    GPS_L2(4),
    GPS_L5(5),

    GLONASS_UNKNOWN(6),
    GLONASS_ALL(7),
    GLONASS_G1(8),
    GLONASS_G2(9),

    Galileo_UNKNOWN(10),
    Galileo_ALL(11),
    Galileo_E5(12),
    Galileo_E6(13),
    Galileo_E1(14),

    BeiDou_UNKNOWN(15),
    BeiDou_ALL(16),
    BeiDou_B1(17),
    BeiDou_B2(18),
    BeiDou_B3(19),

    QZSS_UNKNOWN(20),
    QZSS_ALL(21),
    QZSS_L1(22),
    QZSS_L2(23),
    QZSS_L5(24),
    QZSS_L6(25),

    NavIC_UNKNOWN(26),
    NavIC_ALL(27),
    NavIC_L1(28),
    NavIC_L5(29),
    NavIC_S(30);

    private final int value;

    GnssSignal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GnssSignal parse(int index) {
        GnssSignal[] types = values();
        if (types.length > index && index > -1)
            return types[index];
        throw new IllegalArgumentException("Invalid Prefix id: " + index);
    }

    @Override
    public String toString() {
        return this.toStringSignalOnly();
    }

    public String toStringSignalOnly() {
        switch (this) {
            case GPS_UNKNOWN:
                return "Unknown";
            case GPS_ALL:
                return "L1+L2+L5";
            case GPS_L1:
                return "L1";
            case GPS_L2:
                return "L2";
            case GPS_L5:
                return "L3";

            case GLONASS_UNKNOWN:
                return "Unknown";
            case GLONASS_ALL:
                return "G1+G2";
            case GLONASS_G1:
                return "G1";
            case GLONASS_G2:
                return "G2";

            case Galileo_UNKNOWN:
                return "Unknown";
            case Galileo_ALL:
                return "E1+E5+E6";
            case Galileo_E5:
                return "E5";
            case Galileo_E6:
                return "E6";
            case Galileo_E1:
                return "E1";

            case BeiDou_UNKNOWN:
                return "Unknown";
            case BeiDou_ALL:
                return "B1+B2+B3";
            case BeiDou_B1:
                return "B1";
            case BeiDou_B2:
                return "B2";
            case BeiDou_B3:
                return "B3";

            case QZSS_UNKNOWN:
                return "Unknown";
            case QZSS_ALL:
                return "L1+L2+L5+L6";
            case QZSS_L1:
                return "L1";
            case QZSS_L2:
                return "L2";
            case QZSS_L5:
                return "L5";
            case QZSS_L6:
                return "L6";

            case NavIC_UNKNOWN:
                return "Unknown";
            case NavIC_ALL:
                return "L1+L5+S";
            case NavIC_L1:
                return "L1";
            case NavIC_L5:
                return "L5";
            case NavIC_S:
                return "L6";

            case Unknown:
            default:
                return "Unknown";
        }
    }

    public String toStringX() {
        switch (this) {
            case GPS_UNKNOWN:
            case GPS_ALL:
            case GPS_L1:
            case GPS_L2:
            case GPS_L5:
                return "GPS " + this.toString();

            case GLONASS_UNKNOWN:
            case GLONASS_ALL:
            case GLONASS_G1:
            case GLONASS_G2:
                return "GLONASS " + this.toString();

            case Galileo_UNKNOWN:
            case Galileo_ALL:
            case Galileo_E5:
            case Galileo_E6:
            case Galileo_E1:
                return "Galileo " + this.toString();

            case BeiDou_UNKNOWN:
            case BeiDou_ALL:
            case BeiDou_B1:
            case BeiDou_B2:
            case BeiDou_B3:
                return "BeiDou " + this.toString();

            case QZSS_UNKNOWN:
            case QZSS_ALL:
            case QZSS_L1:
            case QZSS_L2:
            case QZSS_L5:
            case QZSS_L6:
                return "QZSS " + this.toString();

            case NavIC_UNKNOWN:
            case NavIC_ALL:
            case NavIC_L1:
            case NavIC_L5:
            case NavIC_S:
                return "NavIC " + this.toString();

            case Unknown:
            default:
                return this.toString();
        }
    }
}
