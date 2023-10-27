package com.usda.fmsc.geospatial.nmea.codes;

public enum SentenceID {
    Unknown(0),
    GGA(1),
    RMC(2),
    GSA(3),
    GSV(4),
    GLL(5),
    GNS(6),
    ZDA(7),
    GST(8);

    public static final SentenceID[] VALUES = values();
    private final int value;

    SentenceID(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SentenceID parse(int value) {
        for (SentenceID sid : VALUES) {
            if (sid.value == value)
                return sid;
        }
        throw new IllegalArgumentException("Invalid SentenceID value: " + value);
    }

    public static SentenceID parse(String nmea) {
        if (nmea == null) {
            throw new NullPointerException();
        }

        if (nmea.startsWith("$") && nmea.length() > 6)
            nmea = nmea.substring(3, 6);

        switch (nmea.toUpperCase()) {
            case "GGA":
                return GGA;
            case "GLL":
                return GLL;
            case "GNS":
                return GNS;
            case "GSA":
                return GSA;
            case "GSV":
                return GSV;
            case "RMC":
                return RMC;
            case "ZDA":
                return ZDA;
            case "GST":
                return GST;
            default:
                return Unknown;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case GGA:
                return "GGA";
            case GLL:
                return "GLL";
            case GNS:
                return "GNS";
            case GSA:
                return "GSA";
            case GSV:
                return "GSV";
            case RMC:
                return "RMC";
            case ZDA:
                return "ZDA";
            case GST:
                return "GST";
            default:
                return "Unknown";
        }
    }
}