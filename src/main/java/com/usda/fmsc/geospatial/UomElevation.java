package com.usda.fmsc.geospatial;

public enum UomElevation {
    Feet(0),
    Meters(1);

    private final int value;

    UomElevation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UomElevation parse(int id) {
        UomElevation[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException(String.format("Invalid UomAltitude id: %d", id));
    }

    public static UomElevation parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        switch(value.toLowerCase()) {
            case "m":
            case "meters": return Meters;
            case "f":
            case "feet": return Feet;
            default: throw new IllegalArgumentException(String.format("Invalid UomAltitude Name: %s", value));
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case Feet: return "Feet";
            case Meters: return "Meters";
            default: throw new IllegalArgumentException();
        }
    }


    public String toStringAbv() {
        switch(this) {
            case Feet: return "Ft";
            case Meters: return "M";
            default: throw new IllegalArgumentException();
        }
    }
}
