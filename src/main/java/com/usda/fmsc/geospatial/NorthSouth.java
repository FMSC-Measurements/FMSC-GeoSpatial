package com.usda.fmsc.geospatial;

public enum NorthSouth {
    North(0),
    South(1);

    private final int value;

    NorthSouth(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NorthSouth parse(int id) {
        NorthSouth[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid NorthSouth id: " + id);
    }

    public static NorthSouth parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        switch(value.toLowerCase()) {
            case "s":
            case "south": return South;
            case "n":
            case "north": return North;
            default: throw new IllegalArgumentException(String.format("Invalid NorthSouth Name: %s", value));
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case North: return "North";
            case South: return "South";
            default: throw new IllegalArgumentException();
        }
    }

    public String toStringAbv() {
        switch(this) {
            case North: return "N";
            case South: return "S";
            default: throw new IllegalArgumentException();
        }
    }
}
