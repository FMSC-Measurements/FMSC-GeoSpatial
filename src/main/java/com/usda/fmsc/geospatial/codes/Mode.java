package com.usda.fmsc.geospatial.codes;

public enum Mode {
    Auto(0), Manual(1);

    private final int value;

    Mode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Mode parse(int id) {
        Mode[] types = values();
        if (types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid Mode id: " + id);
    }

    public static Mode parse(String str) {
        switch (str.toLowerCase()) {
            case "0":
            case "a":
            case "auto":
                return Auto;
            case "1":
            case "m":
            case "manual":
                return Manual;
            default:
                throw new IllegalArgumentException("Invalid Mode Name: " + str);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case Auto:
                return "Auto";
            case Manual:
                return "Manual";
            default:
                throw new IllegalArgumentException();
        }
    }

    public String toStringF() {
        switch (this) {
            case Auto:
                return "0 (Auto)";
            case Manual:
                return "1 (Manual)";
            default:
                throw new IllegalArgumentException();
        }
    }
}
