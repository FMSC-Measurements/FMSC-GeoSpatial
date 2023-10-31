package com.usda.fmsc.geospatial.ins;

public class InertialVector implements IVectorData, IVelocityData, IRotationData {
    private double timespan;
    private double velocityX, velocityY, velocityZ;
    private double yaw, pitch, roll;

    public InertialVector(double timespan, double vX, double vY, double vZ, double yaw, double pitch, double roll) {
        this.timespan = timespan;
        this.velocityX = vX;
        this.velocityY = vY;
        this.velocityZ = vZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    @Override
    public double getDistanceX() {
        return getVelocityX() / getTimespan();
    }

    @Override
    public double getDistanceY() {
        return getVelocityY() / getTimespan();
    }

    @Override
    public double getDistanceZ() {
        return getVelocityZ() / getTimespan();
    }


    @Override
    public double getTimespan() {
        return timespan;
    }

    @Override
    public double getVelocityX() {
       return velocityX;
    }

    @Override
    public double getVelocityY() {
       return velocityY;
    }

    @Override
    public double getVelocityZ() {
       return velocityZ;
    }


    @Override
    public double getYaw() {
        return yaw;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public double getRoll() {
        return roll;
    }
}
