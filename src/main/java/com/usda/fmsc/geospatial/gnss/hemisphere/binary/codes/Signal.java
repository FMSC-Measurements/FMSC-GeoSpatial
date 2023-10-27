package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

import java.util.EnumSet;

public enum Signal {
    Unknown(Integer.MAX_VALUE),

    GPS_L1CA(0),
    GPS_L2P(1),
    GPS_L2C(2),
    GPS_L5(3),

    GLONASS_G1C_G1P(8),
    GLONASS_G2C_G2P(9),
    GLONASS_G10C(12),
    GLONASS_G20C(13),
    GLONASS_G30C(14),

    GALILEO_E1BC(16),
    GALILEO_E5A(17),
    GALILEO_E5B(18),
    GALILEO_E6(19),
    GALILEO_ALTBOC(20),

    BEIDOU_B1I(24),
    BEIDOU_B2I(25),
    BEIDOU_B3I(26),
    BEIDOU_B1BOC(27),
    BEIDOU_B2A(28),
    BEIDOU_B2B(29),
    BEIDOU_B2C(30),
    BEIDOU_ACEBOC(31),

    QZS_L1CA(32),
    QZS_L2C(34),
    QZS_L5(35),
    QZS_L1C(36);

    private final int value;

    Signal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static final Signal[] values = values();

    public static Signal parse(int id) {
        for (Signal sig : values) {
            if (sig.value == id)
                return sig;
        }

        return Signal.Unknown;
    }

    public static EnumSet<Signal> parseFromMasks(int allSigs1, int allSigs2) {
        EnumSet<Signal> signals = EnumSet.noneOf(Signal.class);

        for (int i = 0; i < Integer.SIZE; i++) {
            if (((allSigs1 >> i) & 0x1) == 1 &&
                    (i < 4 || i > 7) && (i != 10 && i != 11) && (i != 15) && (i < 21 || i > 23)) { // ignore spares
                signals.add(parse(i));
            }
        }

        for (int i = 0; i < Integer.SIZE; i++) {
            if (((allSigs2 >> i) & 0x1) == 1 &&
                    (i != 1) && (i < 6)) { // ignore spares
                signals.add(parse(i + 32));
            }
        }

        return signals;
    }

    public String toStringSignalOnly() {
        switch (this) {
            case BEIDOU_ACEBOC:
                return "ACEBOC";
            case BEIDOU_B1BOC:
                return "B1BOC";
            case BEIDOU_B1I:
                return "B1I";
            case BEIDOU_B2A:
                return "B2A";
            case BEIDOU_B2B:
                return "B2B";
            case BEIDOU_B2C:
                return "B2C";
            case BEIDOU_B2I:
                return "B2I";
            case BEIDOU_B3I:
                return "B3I";
            case GALILEO_ALTBOC:
                return "ALTBOC";
            case GALILEO_E1BC:
                return "E1BC";
            case GALILEO_E6:
                return "E6";
            case GALILEO_E5A:
                return "E5A";
            case GALILEO_E5B:
                return "E5B";
            case GLONASS_G10C:
                return "G10C";
            case GLONASS_G1C_G1P:
                return "G1C_G1P";
            case GLONASS_G20C:
                return "G20C";
            case GLONASS_G2C_G2P:
                return "G2C_G2P";
            case GLONASS_G30C:
                return "G30C";
            case QZS_L1C:
                return "L1C";
            case GPS_L1CA:
            case QZS_L1CA:
                return "L1CA";
            case GPS_L2C:
            case QZS_L2C:
                return "L2C";
            case GPS_L2P:
                return "L2P";
            case GPS_L5:
            case QZS_L5:
                return "L5";
            case Unknown:
            default:
                return "Unknown";
        }
    }
}
