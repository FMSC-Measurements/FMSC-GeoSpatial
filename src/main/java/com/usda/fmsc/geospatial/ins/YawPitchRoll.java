package com.usda.fmsc.geospatial.ins;


public class YawPitchRoll {
    private double yaw, pitch, roll;
    
    public YawPitchRoll(double yaw, double pitch, double roll) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public double getRoll() {
        return roll;
    }
}
