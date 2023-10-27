package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

public enum GnssSystem {
    GPS(0),
    GLONASS(1),
    Galileo(2),
    BeiDou(3),
    QZSS(4),

    Unknown(Integer.MAX_VALUE);

    private final int value;

    GnssSystem(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GnssSystem parse(int id) {
        GnssSystem[] types = values();
        if (id < 5 && id > -1)
            return types[id];
        return GnssSystem.Unknown;
    }

    @Override
    public String toString() {
        switch (this) {
            case GPS:
                return "GPS";
            case GLONASS:
                return "GLONASS";
            case Galileo:
                return "Galileo";
            case BeiDou:
                return "BeiDou";
            case QZSS:
                return "QZSS";
            default:
                return "Unknown";
        }
    }
}
