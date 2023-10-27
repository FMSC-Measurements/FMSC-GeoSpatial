package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public enum AttitudeQuality {
    Unknown(Integer.MIN_VALUE),
    Excellent(0),
    Good(1),
    Bad(2),
    NotTracking(3);

    private static final AttitudeQuality[] VALUES = values();
    private final int value;

    AttitudeQuality(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static AttitudeQuality parse(int value) {
        for (AttitudeQuality code : VALUES) {
            if (code.value == value)
                return code;
        }

        return Unknown;
    }
}
