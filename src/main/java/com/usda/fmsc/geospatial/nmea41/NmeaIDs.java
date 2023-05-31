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

            if(value.startsWith("$") && value.length() > 3)
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

            if(value.startsWith("$") && value.length() > 6)
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
        Galileo_L1(14),

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

        public static GnssSignal parseSignalId(int sigId, TalkerID talkerID) {
            switch (talkerID) {
                case GP: {
                    switch (sigId) {
                        case 0:
                            return GPS_ALL;
                        case 1:
                        case 2:
                        case 3:
                            return GPS_L1;
                        case 4:
                        case 5:
                            return GPS_L2;
                        case 6:
                        case 7:
                        case 8:
                            return GPS_L5;
                        default:
                            return GPS_UNKNOWN;
                    }
                }
                case GL: {
                    switch (sigId) {
                        case 0:
                            return GLONASS_ALL;
                        case 1:
                        case 2:
                            return GLONASS_G1;
                        case 3:
                        case 4:
                            return GLONASS_G2;
                        default:
                            return GLONASS_UNKNOWN;
                    }
                }
                case GA: {
                    switch (sigId) {
                        case 0:
                            return Galileo_ALL;
                        case 1:
                        case 2:
                        case 3:
                            return Galileo_E5;
                        case 4:
                        case 5:
                            return Galileo_E6;
                        case 6:
                        case 7:
                            return Galileo_L1;
                        default:
                            return Galileo_UNKNOWN;
                    }
                }
                case BD:
                case PQ:
                case GB: {
                    switch (sigId) {
                        case 0:
                            return BeiDou_ALL;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            return BeiDou_B1;
                        case 5:
                        case 6:
                        case 7:
                        case 11:
                        case 12:
                            return BeiDou_B2;
                        case 8:
                        case 9:
                        case 10:
                            return BeiDou_B3;
                        default:
                            return BeiDou_UNKNOWN;
                    }
                }
                case QZ: {
                    switch (sigId) {
                        case 0:
                            return QZSS_ALL;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            return QZSS_L1;
                        case 5:
                        case 6:
                            return QZSS_L2;
                        case 7:
                        case 8:
                            return QZSS_L5;
                        case 9:
                        case 10:
                            return QZSS_L6;
                        default:
                            return QZSS_UNKNOWN;
                    }
                }
                default:
                    return Unknown;
            }
        }

        public boolean isUnknown() {
            switch (this) {
                case GPS_UNKNOWN:
                case GLONASS_UNKNOWN:
                case Galileo_UNKNOWN:
                case BeiDou_UNKNOWN:
                case QZSS_UNKNOWN:
                case NavIC_UNKNOWN:
                    return true;
            }

            return false;
        }

        @Override
        public String toString() {
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
                    return "E5+E6+L1";
                case Galileo_E5:
                    return "E5";
                case Galileo_E6:
                    return "E6";
                case Galileo_L1:
                    return "L1";

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
                case Galileo_L1:
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

    public enum SystemID {
        Unknown(0),
        GPS(1),
        GLONASS(2),
        Galileo(3),
        BeiDou(4),
        QZSS(5),
        NavIC(6);

        private final int value;

        SystemID(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static SystemID parse(int id) {
            SystemID[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid System id: " + id);
        }

        @Override
        public String toString() {
            switch (this) {
                case Unknown: return "Unknown";
                case GPS: return "GPS";
                case GLONASS: return "GLONASS";
                case Galileo: return "Galileo";
                case BeiDou: return "BeiDou";
                case QZSS: return "QZSS";
                case NavIC: return "NavIC";
                default: return "";
            }
        }
    }
}