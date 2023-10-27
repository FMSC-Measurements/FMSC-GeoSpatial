package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

import java.util.EnumSet;

public enum ChannelFlags {
    ENABLED(0),
    USED_IN_SOLUTION(1),
    Unknown(Integer.MAX_VALUE);

    private final int value;

    ChannelFlags(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    private static final ChannelFlags[] values = values();

    public static ChannelFlags parse(int id) {
        if (id < 2 && id > -1)
            return values[id];
        return ChannelFlags.Unknown;
    }

    public static EnumSet<ChannelFlags> parseFromMask(byte value) {
        EnumSet<ChannelFlags> flags = EnumSet.noneOf(ChannelFlags.class);

        for (int i = 0; i < 3; i++) {
            if (((value >> i) & 0x1) == 1) {
                flags.add(ChannelFlags.parse(i));
            }
        }

        return flags;
    }
}
