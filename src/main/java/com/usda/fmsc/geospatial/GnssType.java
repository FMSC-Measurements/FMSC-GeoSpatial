package com.usda.fmsc.geospatial;

public enum GnssType {
    Unknown(0),
    GPS(1),
    GLONASS(2),
    GALILEO(3),
    BEIDOU(4),
    QZSS(5),
    UnknownSBAS(6),
    WAAS(7),
    EGNOS(8),
    SDCM(9),
    GAGAN(10),
    MSAS(11),
    SNAS(12);

    private final int value;

    GnssType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GnssType parse(int id) {
        GnssType[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid id: " + id);
    }

    public static GnssType parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        switch(value.toUpperCase()) {
            case "GPS": return GPS;
            case "GLONASS": return GLONASS;
            case "GALILEO": return GALILEO;
            case "BEIDOU": return BEIDOU;
            case "QZSS": return QZSS;
            case "SBAS": return UnknownSBAS;
            case "UNKNOWNSBAS": return UnknownSBAS;
            case "WAAS": return WAAS;
            case "EGNOS": return EGNOS;
            case "SDCM": return SDCM;
            case "GAGAN": return GAGAN;
            case "MSAS": return MSAS;
            case "SNAS": return SNAS;
            default: return Unknown;
        }
    }

    public static GnssType parseNmeaId(int prn) {
        if (prn > 0 && prn < 33) {
            return GPS;
        } else if (prn > 32 && prn < 55) {
            if (prn == 33 || prn == 37 || prn == 39 || prn == 44) {
                return EGNOS;
            } else if (prn == 35 || prn == 51 || (prn > 45 && prn < 49)) {
                return WAAS;
            } else if (prn == 38 || (prn > 52 && prn < 55)) {
                return SDCM;
            } else if (prn == 40 || prn == 41 ) {
                return GAGAN;
            } else if (prn == 42 || prn == 50 ) {
                return MSAS;
            } else {
                return UnknownSBAS;
            }
        } else if (prn > 64 && prn < 97) {
            return GLONASS;
        } else if (prn > 192 && prn < 201) {
            return QZSS;
        } else if (prn >=200 && prn < 236) {
            return BEIDOU;
        } else {
            return Unknown;
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case GPS: return "GPS";
            case GLONASS: return "Glonass";
            case GALILEO: return "Galileo";
            case BEIDOU: return "BeiDou";   //China
            case QZSS: return "QZSS";       //Japan
            case UnknownSBAS: return "SBAS";
            case WAAS: return "WAAS";
            case EGNOS: return "EGNOS";
            case SDCM: return "SDCM";
            case GAGAN: return "GAGAN";
            case MSAS: return "MSAS";
            case SNAS: return "SNAS";
            default: return "Unknown";
        }
    }

    public boolean isSBAS() {
        switch (this) {
            case UnknownSBAS:
            case WAAS:
            case EGNOS:
            case SDCM:
            case GAGAN:
            case MSAS:
            case SNAS: return true;
        }

        return false;
    }
}