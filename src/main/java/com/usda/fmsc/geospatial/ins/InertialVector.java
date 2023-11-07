package com.usda.fmsc.geospatial.ins;

public class InertialVector implements IVectorData, IVelocityData, IOrientationData, IRotationData {
    private final double timespan;
    private final double rotationX, rotationY, rotationZ;
    private final double velocityX, velocityY, velocityZ;
    private final double yaw, pitch, roll;

    public InertialVector(double timespan,
                          double rotX, double rotY, double rotZ,
                          double velX, double velY, double velZ,
                          double yaw, double pitch, double roll) {
        this.timespan = timespan;
        this.rotationX = rotX;
        this.rotationY = rotY;
        this.rotationZ = rotZ;
        this.velocityX = velX;
        this.velocityY = velY;
        this.velocityZ = velZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    @Override
    public double getDistanceX() {
        return getVelocityX() / getTimeSpan();
    }

    @Override
    public double getDistanceY() {
        return getVelocityY() / getTimeSpan();
    }

    @Override
    public double getDistanceZ() {
        return getVelocityZ() / getTimeSpan();
    }


    @Override
    public double getTimeSpan() {
        return timespan;
    }

    @Override
    public double getRotationX() {
        return rotationX;
    }

    @Override
    public double getRotationY() {
        return rotationY;
    }

    @Override
    public double getRotationZ() {
        return rotationZ;
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
