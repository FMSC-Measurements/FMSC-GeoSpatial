package com.usda.fmsc.geospatial.nmea;

public class NmeaIDs {

    public enum TalkerID {
        Unknown(0),
        GP(1),
        LC(2),
        II(3),
        IN(4),
        EC(5),
        CD(6),
        GA(7),
        GL(8),
        GN(9),
        GB(10),
        BD(11),
        QZ(12);

        private final int value;

        private TalkerID(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static TalkerID parse(int id) {
            TalkerID[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid Prefix id: " + id);
        }

        public static TalkerID parse(String value) {
            if (value == null) {
                throw new NullPointerException();
            }

            if(value.startsWith("$"))
                value = value.substring(1, 3);

            switch(value.toUpperCase()) {
                case "GP": return GP;
                case "LC": return LC;
                case "II": return II;
                case "IN": return IN;
                case "EC": return EC;
                case "CD": return CD;
                case "GA": return GA;
                case "GL": return GL;
                case "GN": return GN;
                case "GB": return GB;
                case "BD": return BD;
                case "QZ": return QZ;
                default: return Unknown;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case GP: return "GPS";
                case LC: return "Loran-C";
                case II: return "Integrated Instrumentation";
                case IN: return "Integrated Navigation";
                case EC: return "ECDIS";
                case CD: return "DSC";
                case GA: return "Galileo";
                case GL: return "GLONASS";
                case GN: return "GPS + GLONASS";
                case GB: return "BeiDou";   //China
                case BD: return "BeiDou";   //China
                case QZ: return "QZSS";     //Japan
                default: return "Unknown";
            }
        }
    }

    public enum SentenceID {
        Unknown(0),
        BOD(1),
        BWC(2),
        GGA(3),
        GLL(4),
        GNS(5),
        GSA(6),
        GSV(7),
        HDT(8),
        R00(9),
        RMA(10),
        RMB(11),
        RMC(12),
        RTE(13),
        TRF(14),
        STN(15),
        VBW(16),
        VTG(17),
        WPL(18),
        XTE(19),
        ZDA(20);

        private final int value;

        private SentenceID(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static SentenceID parse(int id) {
            SentenceID[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid Prefix id: " + id);
        }

        public static SentenceID parse(String value) {
            if (value == null) {
                throw new NullPointerException();
            }

            if(value.startsWith("$"))
                value = value.substring(3, 6);

            switch(value.toUpperCase()) {
                case "BOD": return BOD;
                case "BWC": return BWC;
                case "GGA": return GGA;
                case "GLL": return GLL;
                case "GNS": return GNS;
                case "GSA": return GSA;
                case "GSV": return GSV;
                case "HDT": return HDT;
                case "R00": return R00;
                case "RMA": return RMA;
                case "RMB": return RMB;
                case "RMC": return RMC;
                case "RTE": return RTE;
                case "TRF": return TRF;
                case "STN": return STN;
                case "VBW": return VBW;
                case "VTG": return VTG;
                case "WPL": return WPL;
                case "XTE": return XTE;
                case "ZDA": return ZDA;
                default: return Unknown;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case BOD: return "BOD";
                case BWC: return "BWC";
                case GGA: return "GGA";
                case GLL: return "GLL";
                case GNS: return "GNS";
                case GSA: return "GSA";
                case GSV: return "GSV";
                case HDT: return "HDT";
                case R00: return "R00";
                case RMA: return "RMA";
                case RMB: return "RMB";
                case RMC: return "RMC";
                case RTE: return "RTE";
                case TRF: return "TRF";
                case STN: return "STN";
                case VBW: return "VBW";
                case VTG: return "VTG";
                case WPL: return "WPL";
                case XTE: return "XTE";
                case ZDA: return "ZDA";
                default: return "Unknown";
            }
        }
    }
}