package com.usda.fmsc.geospatial.nmea.codes;

public enum TalkerID {
    UNKNOWN(0),
    GP(1),  //GPS
    LC(2),  //LORAN-C
    II(3),  //Integrated Instrumentation
    IN(4),  //Integrated Navigation
    EC(5),  //Electronic Chart Display & Information System (ECDIS)
    CD(6),  //Communications - Digital Selective Calling (DSC)
    GA(7),  //Galileo Positioning System
    GL(8),  //GLONASS
    GN(9),  //Combination of multiple satellite systems
    GB(10), //BeiDou (China)
    BD(11), //BeiDou (China)
    QZ(12), //QZSS regional GPS augmentation system (Japan)
    BIN(100),   //BINARY
    RD1(101),   //SBAS DIAG
    PROP_UNKNOWN(1000),//Proprietary Unknown
    PSAT(1001), //Hemisphere Unknown
    PSAT_GBS(1002), //Hemisphere Receiver Autonomous Integrity Monitoring
    PJSI(1101),//Juniper Unknown
    PJSI_BATT(1102); //BATTERY

    public static final TalkerID[] VALUES = values();
    private final int value;

    TalkerID(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TalkerID parse(int value) {
        for (TalkerID tid : VALUES) {
            if (tid.value == value) return tid;
        }
        throw new IllegalArgumentException("Invalid TalkerID value: " + value);
    }

    public static TalkerID parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        if (!value.startsWith("$"))
            return UNKNOWN;

        if (value.length() > 3) {
            switch (value.substring(1, 3).toUpperCase()) {
                case "GP":
                    return GP;
                case "LC":
                    return LC;
                case "II":
                    return II;
                case "IN":
                    return IN;
                case "EC":
                    return EC;
                case "CD":
                    return CD;
                case "GA":
                    return GA;
                case "GL":
                    return GL;
                case "GN":
                    return GN;
                case "GB":
                    return GB;
                case "BD":
                    return BD;
                case "QZ":
                    return QZ;
            }
        }

        if (value.length() > 4) {
            switch (value.substring(1, 4).toUpperCase()) {
                case "RD1": return RD1;
            }
        }
        
        if (value.length() > 5) {
            switch (value.substring(1, 5).toUpperCase()) {
                case "PJSI": {
                    if (value.contains("PJSI,BAT")) {
                        return PJSI_BATT;
                    }

                    return PJSI;
                }
                case "PSAT": {
                    if (value.contains("PSAT,GBS")) {
                        return PSAT_GBS;
                    }

                    return PSAT;
                }
            }
        }
        
        if (value.startsWith("$BIN")) {
            return BIN;
        }
        
        return value.startsWith("$P") ? PROP_UNKNOWN : UNKNOWN;
    }

    @Override
    public String toString() {
        switch (this) {
            case GP:
                return "GPS";
            case LC:
                return "Loran-C";
            case II:
                return "Integrated Instrumentation";
            case IN:
                return "Integrated Navigation";
            case EC:
                return "ECDIS";
            case CD:
                return "DSC";
            case GA:
                return "Galileo"; // EU
            case GL:
                return "GLONASS"; //Russia
            case GN:
                return "Multi GNSS"; //Multiple GNSS
            case GB:
            case BD:
                return "BeiDou"; // China
            case QZ:
                return "QZSS"; // Japan
            default:
                return "Unknown";
        }
    }

    public String toStringCode() {
        switch (this) {
            case GP:
                return "$GP";
            case LC:
                return "$LC";
            case II:
                return "$II";
            case IN:
                return "$IN";
            case EC:
                return "$EC";
            case CD:
                return "$CD";
            case GA:
                return "$GA";
            case GL:
                return "$GL";
            case GN:
                return "$GN";
            case GB:
                return "$GB";
            case BD:
                return "$BD";
            case QZ:
                return "$QZ";
            default:
                return "$??";
        }
    }
}
