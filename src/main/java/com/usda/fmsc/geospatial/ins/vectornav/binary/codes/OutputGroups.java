package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

import com.usda.fmsc.geospatial.Utils;

public class OutputGroups {
    public static final int None = 0;
    public static final int Common = 1 << 0;
    public static final int Time = 1 << 1;
    public static final int IMU = 1 << 2;
    public static final int Attitude = 1 << 4;

    private final int value;

    public OutputGroups(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean hasAny() {
        return value != None;
    }

    public boolean isValid() {
        return hasAny() && (value & 0b1111) != 0b1111;
    }

    public int getGroupCount() {
        return Utils.getNumberOfSetBits((byte)value);
    }

    public boolean hasCommon() {
        return (value & Common) == Common;
    }

    public boolean hasTime() {
        return (value & Time) == Time;
    }

    public boolean hasIMU() {
        return (value & IMU) == IMU;
    }

    public boolean hasAttitude() {
        return (value & Attitude) == Attitude;
    }

    @Override
    public String toString() {
        int count = getGroupCount();
        return String.format("%s%s%s",
                hasCommon() ? (count-- > 0 ? "Common | " : "Common") : "",
                hasTime() ? (count-- > 0 ? "Time | " : "Time") : "",
                hasIMU() ? (count-- > 0 ? "IMU | " : "IMU") : "",
                hasAttitude() ? "Attitude" : "");
    }
}
