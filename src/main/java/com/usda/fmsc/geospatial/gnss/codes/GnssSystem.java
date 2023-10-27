package com.usda.fmsc.geospatial.gnss.codes;

public enum GnssSystem {
    UNKOWN(0),
    GPS(1),
    GLONASS(2),
    GALILEO(3),
    BEIDOU(4),
    QZSS(5),
    NAV_IC(6),
    MULTI_GNSS(Integer.MAX_VALUE);

    
    private final int value;

    GnssSystem(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }


    public static GnssSystem parse(int id) {
        GnssSystem[] types = values();
        if (types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid id: " + id);
    }

    public static GnssSystem parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        switch (value.toUpperCase()) {
            case "GPS":
                return GPS;
            case "GLONASS":
                return GLONASS;
            case "GALILEO":
                return GALILEO;
            case "BEIDOU":
                return BEIDOU;
            case "QZSS":
                return QZSS;
            default:
                return UNKOWN;
        }
    }

    
    @Override
    public String toString() {
        switch (this) {
            case GPS:
                return "GPS"; // USA
            case GLONASS:
                return "GLONASS"; // Russia
            case GALILEO:
                return "Galileo"; // EU
            case BEIDOU:
                return "BeiDou"; // China
            case QZSS:
                return "QZSS"; // Japan
            case NAV_IC:
                return "NAV_IC";
            default:
                return "Unknown";
        }
    }
}