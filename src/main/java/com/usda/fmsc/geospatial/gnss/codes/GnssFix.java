package com.usda.fmsc.geospatial.gnss.codes;

public enum GnssFix {
    Unknown(0),
    NoFix(1),
    _2D(2),
    _3D(3);

    private final int value;

    GnssFix(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GnssFix parse(int id) {
        GnssFix[] types = values();
        if (types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid Fix id: " + id);
    }

    public static GnssFix parse(String str) {
        switch (str.toLowerCase()) {
            case "0":
            case "unknown":
                return Unknown;
            case "1":
            case "nofix":
            case "no fix":
            case "no":
                return NoFix;
            case "2":
            case "2d":
                return _2D;
            case "3":
            case "3d":
                return _3D;
            default:
                throw new IllegalArgumentException("Invalid Fix Name: " + str);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case Unknown:
                return "Unknown";
            case NoFix:
                return "No Fix";
            case _2D:
                return "2D";
            case _3D:
                return "3D";
            default:
                throw new IllegalArgumentException();
        }
    }

    public String toStringF() {
        switch (this) {
            case Unknown:
                return "0 (Unknown)";
            case NoFix:
                return "1 (No Fix)";
            case _2D:
                return "2 (2D)";
            case _3D:
                return "3 (3D)";
            default:
                throw new IllegalArgumentException();
        }
    }
}
