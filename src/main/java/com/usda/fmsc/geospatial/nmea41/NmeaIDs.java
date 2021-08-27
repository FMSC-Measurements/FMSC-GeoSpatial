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
                case GN: return "Multiple GNSS";
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
        GPS_ALL(0),
        Unknown(1),
        GPS_UNKNOWN(3),
        GPS_L1(4),
        GPS_L2(5),
        GPS_L5(6),
        GLONASS_ALL(7),
        GLONASS_UNKNOWN(8),
        GLONASS_G1(9),
        GLONASS_G2(10),
        GLONASS_G3(11),
        Galileo_ALL(12),
        Galileo_UNKNOWN(13),
        Galileo_E1(14),
        Galileo_E5(15),
        Galileo_E6(16),
        BeiDou_ALL(17),
        BeiDou_UNKNOWN(18),
        BeiDou_B1(19),
        BeiDou_B2(20),
        BeiDou_B3(21),
        QZSS_ALL(22),
        QZSS_UNKNOWN(23),
        QZSS_L1(24),
        QZSS_L2(25),
        QZSS_L5(26),
        QZSS_L6(27),
        Mixed(28);

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
                    if (sigId == 0) return GPS_ALL;
                    else if (sigId >= 1 && sigId <= 3) return GPS_L1;
                    else if (sigId >= 4 && sigId <= 6) return GPS_L2;
                    else if (sigId >= 7 && sigId <= 8) return GPS_L5;
                    else return GPS_UNKNOWN;
                }
                case GL: {
                    if (sigId == 0) return GLONASS_ALL;
                    else if (sigId >= 1 && sigId <= 2) return GLONASS_G1;
                    else if (sigId >= 3 && sigId <= 4) return GLONASS_G2;
                    else return GLONASS_UNKNOWN;
                }
                case GA: {
                    if (sigId == 0) return Galileo_ALL;
                    else if (sigId >= 1 && sigId <= 3) return Galileo_E5;
                    else if (sigId >= 4 && sigId <= 5) return Galileo_E6;
                    else if (sigId >= 6 && sigId <= 7) return Galileo_E1;
                    else return Galileo_UNKNOWN;
                }
                case BD:
                case PQ:
                case GB: {
                    if (sigId == 0) return BeiDou_ALL;
                    else if (sigId >= 1 && sigId <= 4) return BeiDou_B1;
                    else if (sigId >= 5 && sigId <= 7) return BeiDou_B2;
                    else if (sigId >= 8 && sigId <= 9) return BeiDou_B3;
                    else return BeiDou_UNKNOWN;
                }
                case QZ: {
                    if (sigId == 0) return QZSS_ALL;
                    else if (sigId >= 1 && sigId <= 4) return QZSS_L1;
                    else if (sigId >= 5 && sigId <= 6) return QZSS_L2;
                    else if (sigId >= 7 && sigId <= 8) return QZSS_L5;
                    else if (sigId >= 9 && sigId <= 10) return QZSS_L6;
                    else return QZSS_UNKNOWN;
                }
                default: return Unknown;
            }
        }

        public static GnssSignal fromTalkerID(TalkerID talkerID) {
            switch (talkerID) {
                case GP: return GPS_UNKNOWN;
                case GA: return Galileo_UNKNOWN;
                case GL: return GLONASS_UNKNOWN;
                case GN: return Mixed;
                case GB:
                case BD:
                case PQ: return BeiDou_UNKNOWN;
                case QZ: return QZSS_UNKNOWN;
                case Unknown:
                default: return Unknown;
            }
        }

        public boolean isUnknown() {
            return  (this == Unknown || this == GPS_UNKNOWN || this == GLONASS_UNKNOWN ||
                    this == Galileo_UNKNOWN || this == BeiDou_UNKNOWN || this == QZSS_UNKNOWN);
        }
    }
}