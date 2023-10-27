package com.usda.fmsc.geospatial.base.parsers;

public enum ParseMode {
    None(0),
    Time(1),
    Delimiter(2);

    private final int value;

    ParseMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ParseMode parse(int id) {
        ParseMode[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid Parse Mode: " + id);
    }

}
