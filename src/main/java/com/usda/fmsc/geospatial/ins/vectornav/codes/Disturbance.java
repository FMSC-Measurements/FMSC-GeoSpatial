package com.usda.fmsc.geospatial.ins.vectornav.codes;

public enum Disturbance {
    None(0),
    Present(1);

    private final int value;

    private Disturbance(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Disturbance parse(int value) {
        if (value == 0) return Disturbance.None;
        else if (value == 1) return Disturbance.Present;
        
        throw new RuntimeException("Invalid Disturbance value");
    }

    public static Disturbance parse(String value) {
        if (value.length() == 1) {
            if (value.equals("0")) return Disturbance.None;
            if (value.equals("1")) return Disturbance.Present;
        }

        throw new RuntimeException("Invalid Disturbance value");
    }
}
