package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

import java.util.EnumSet;

public enum Almanac {
    AVAILABLE(4),
    HEALTH_OK(5),
    Unknown(Integer.MAX_VALUE);

    private final int value;

    Almanac(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static final Almanac[] values = values();

    public static Almanac parse(int id) {
        for (Almanac sig : values) {
            if (sig.value == id)
                return sig;
        }

        return Almanac.Unknown;
    }

    public static EnumSet<Almanac> parseFromEphemerisAlmanacFlags(byte value) {
        EnumSet<Almanac> flags = EnumSet.noneOf(Almanac.class);

        for (int i = 4; i < 6; i++) {
            if (((value >> i) & 0x1) == 1) {
                flags.add(Almanac.parse(i));
            }
        }

        return flags;
    }
}
