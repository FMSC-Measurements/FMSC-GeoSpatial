package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public enum GpsMode {
    None(Integer.MIN_VALUE),
    NotTracking(0),
    Aligning(1),
    Tracking(2),
    LossOfGps(3);

    private static final GpsMode[] VALUES = values();
    private final int value;

    GpsMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static GpsMode parse(int value) {
        for (GpsMode code : VALUES) {
            if (code.value == value)
                return code;
        }

        return None;
    }
}
