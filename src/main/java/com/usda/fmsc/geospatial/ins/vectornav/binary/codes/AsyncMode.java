package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public enum AsyncMode {
    None(0),
    Port1FixedRate(1),
    Port2FixedRate(2),
    BothPortsFixedRate(3);

    private static final AsyncMode[] VALUES = values();
    private final int value;

    AsyncMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static AsyncMode parse(int value) {
        for (AsyncMode code : VALUES) {
            if (code.value == value)
                return code;
        }

        return None;
    }
}
