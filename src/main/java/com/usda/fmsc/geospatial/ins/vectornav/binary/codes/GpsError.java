package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class GpsError {
    public static final int None = 0;
    public static final int IMU = 1 << 1;
    public static final int MAG_PRESS = 1 << 2;
    public static final int GPS = 1 << 3;
    

    private final int value;

    
    public GpsError(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }


    public boolean hasAny() {
        return value != None;
    }
    
    public boolean hasImuError() {
        return (value & IMU) == IMU;
    }

    public boolean hasMagPressError() {
        return (value & MAG_PRESS) == MAG_PRESS;
    }

    public boolean hasGpsError() {
        return (value & GPS) == GPS;
    }

    
    public static GpsError parse(int value) {
        for (int i = 0; i < 4; i++) {
            if (i == value)
                return new GpsError(value);
        }
        return new GpsError(None);
    }
}
