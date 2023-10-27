package com.usda.fmsc.geospatial.codes;

public enum EastWest {
    East(0), West(1);

    private final int value;

    EastWest(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EastWest parse(int id) {
        EastWest[] types = values();
        if (types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid NorthSouth id: " + id);
    }

    public static EastWest parse(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        switch (value.toLowerCase()) {
        case "e":
        case "east":
            return East;
        case "w":
        case "west":
            return West;
        case "":
            return null;
        default:
            throw new IllegalArgumentException(String.format("Invalid EastWest Name: %s", value));
        }
    }

    @Override
    public String toString() {
        switch (this) {
        case East:
            return "East";
        case West:
            return "West";
        default:
            throw new IllegalArgumentException();
        }
    }

    public String toStringAbv() {
        switch (this) {
        case East:
            return "E";
        case West:
            return "W";
        default:
            throw new IllegalArgumentException();
        }
    }
}
