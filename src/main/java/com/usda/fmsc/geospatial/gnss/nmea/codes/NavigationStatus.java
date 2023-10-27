package com.usda.fmsc.geospatial.gnss.nmea.codes;

public enum NavigationStatus {
    Unknown(0),
    Safe(1),
    Caution(2),
    Unsafe(3),
    NotValid(4);

    private final int value;

    NavigationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NavigationStatus parse(int id) {
        NavigationStatus[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid Status id: " + id);
    }

    public static NavigationStatus parse(String value) {
        switch(value.toLowerCase()) {
            case "unknown":
            case "0": return Unknown;
            case "s":
            case "safe":
            case "1": return Safe;
            case "c":
            case "caution":
            case "2": return Caution;
            case "u":
            case "unsafe":
            case "3": return Unsafe;
            case "v":
            case "not valid":
            case "notvalid":
            case "4": return NotValid;
            default: throw new IllegalArgumentException("Invalid Status Name: " + value);
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case Unknown: return "Unknown";
            case Safe: return "Safe";
            case Caution: return "Caution";
            case Unsafe: return "Unsafe";
            case NotValid: return "Not Valid";
            default: throw new IllegalArgumentException();
        }
    }

    public String toStringF() {
        switch(this) {
            case Unknown: return "0 (Unknown)";
            case Safe: return "1 (Safe)";
            case Caution: return "2 (Caution)";
            case Unsafe: return "3 (Unsafe)";
            case NotValid: return "4 (Not Valid)";
            default: throw new IllegalArgumentException();
        }
    }
}