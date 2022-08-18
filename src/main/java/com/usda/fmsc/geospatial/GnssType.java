package com.usda.fmsc.geospatial;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs;

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
    SNAS(12),
    IMES(13);

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
            case "WAAS": return WAAS;
            case "EGNOS": return EGNOS;
            case "SDCM": return SDCM;
            case "GAGAN": return GAGAN;
            case "MSAS": return MSAS;
            case "SNAS": return SNAS;
            case "SBAS":
            case "UNKNOWNSBAS": return UnknownSBAS;
            default: return Unknown;
        }
    }

    public static GnssType parseNmeaId(int nmeaId) {
        if (nmeaId > 0 && nmeaId < 33) {
            return GPS;
        } else if ((nmeaId > 32 && nmeaId < 55) || (nmeaId > 119 && nmeaId < 159)) {
            if (nmeaId == 33 || nmeaId == 37 || nmeaId == 39 || nmeaId == 49 || nmeaId == 36) {
                return EGNOS;
            } else if (nmeaId == 46 || nmeaId == 48 || nmeaId == 51 || nmeaId == 35 || nmeaId == 47 || nmeaId == 44) {
                return WAAS;
            } else if (nmeaId == 38 || nmeaId > 52) {
                return SDCM;
            } else if (nmeaId == 40 || nmeaId == 41 ) {
                return GAGAN;
            } else if (nmeaId == 42 || nmeaId == 50 ) {
                return MSAS;
            } else {
                return UnknownSBAS;
            }
        } else if (nmeaId > 64 && nmeaId < 97) {
            return GLONASS;
        } else if (nmeaId > 172 && nmeaId < 183) {
            return IMES;
        } else if (nmeaId > 192 && nmeaId < 201) {
            return QZSS;
        } else if (nmeaId > 300 && nmeaId < 337) {
            return GALILEO;
        } else if (nmeaId > 200 && nmeaId < 236 || nmeaId > 400 && nmeaId < 438) {
            return BEIDOU;
        } else {
            return Unknown;
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case GPS: return "GPS";
            case GLONASS: return "GLONASS";
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