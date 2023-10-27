package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

import java.util.EnumSet;

public enum Ephemeris {
    AVAILABLE_TIMED_OUT(0),
    VALID(1),
    HEALTH_OK(2),
    Unknown(Integer.MAX_VALUE);

    private final int value;

    Ephemeris(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Ephemeris parse(int id) {
        Ephemeris[] types = values();
        if (id < 3 && id > -1)
            return types[id];
        return Ephemeris.Unknown;
    }

    public static EnumSet<Ephemeris> parseFromEphemerisAlmanacFlags(byte value) {
        EnumSet<Ephemeris> flags = EnumSet.noneOf(Ephemeris.class);

        for (int i = 0; i < 3; i++) {
            if (((value >> i) & 0x1) == 1) {
                flags.add(Ephemeris.parse(i));
            }
        }

        return flags;
    }
}
