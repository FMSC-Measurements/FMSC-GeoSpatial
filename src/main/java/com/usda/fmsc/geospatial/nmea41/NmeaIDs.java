package com.usda.fmsc.geospatial.nmea41;

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
        QZ(12),
        PQ(13);

        private final int value;

        TalkerID(int value) {
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
                case "PQ": return PQ;
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
                case GB:
                case BD:
                case PQ: return "BeiDou";   //China
                case QZ: return "QZSS";     //Japan
                default: return "Unknown";
            }
        }

        public String toStringCode() {
            switch(this) {
                case GP: return "$GP";
                case LC: return "$LC";
                case II: return "$II";
                case IN: return "$IN";
                case EC: return "$EC";
                case CD: return "$CD";
                case GA: return "$GA";
                case GL: return "$GL";
                case GN: return "$GN";
                case GB: return "$GB";
                case BD: return "$BD";
                case QZ: return "$QZ";
                case PQ: return "$PQ";
                default: return "$??";
            }
        }
    }

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

        private final int value;

        SentenceID(int value) {
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
                case "GGA": return GGA;
                case "GLL": return GLL;
                case "GNS": return GNS;
                case "GSA": return GSA;
                case "GSV": return GSV;
                case "RMC": return RMC;
                case "ZDA": return ZDA;
                case "GST": return GST;
                default: return Unknown;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case GGA: return "GGA";
                case GLL: return "GLL";
                case GNS: return "GNS";
                case GSA: return "GSA";
                case GSV: return "GSV";
                case RMC: return "RMC";
                case ZDA: return "ZDA";
                case GST: return "GST";
                default: return "Unknown";
            }
        }
    }

    public enum GnssSignal {
        Unknown(0),
        GPS_L1(1),
        GPS_L2(2),
        GPS_L5(3),
        GLONASS_G1(4),
        GLONASS_G2(5),
        GLONASS_G3(6),
        Galileo_E1(7),
        Galileo_E6(8),
        Galileo_E5(9),
        BeiDou_B1(10),
        BeiDou_B2(11);

        private final int value;

        GnssSignal(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static GnssSignal parse(int index) {
            GnssSignal[] types = values();
            if(types.length > index && index > -1)
                return types[index];
            throw new IllegalArgumentException("Invalid Prefix id: " + index);
        }

        public static GnssSignal parseSignalId(int sigId, TalkerID talkerID) {
            switch (talkerID) {
                case GP: {
                    switch (sigId) {
                        case 1: return GPS_L1;
                        case 5:
                        case 6: return GPS_L2;
                        default: return Unknown;
                    }
                }
                case GL: {
                    switch (sigId) {
                        case 1: return GLONASS_G1;
                        case 3: return GLONASS_G2;
                        default: return Unknown;
                    }
                }
                case GA: {
                    switch (sigId) {
                        case 7: return Galileo_E1;
                        case 2: return Galileo_E5;
                        default: return Unknown;
                    }
                }
                case BD:
                case PQ:
                case GB: {
                    switch (sigId) {
                        case 1: return BeiDou_B1;
                        case 3: return BeiDou_B2;
                        default: return Unknown;
                    }
                }
                default: return Unknown;
            }
        }
    }
}