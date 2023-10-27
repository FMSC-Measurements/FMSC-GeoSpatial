package com.usda.fmsc.geospatial.ins;

public class InertialVector implements IVectorData, IVelocityData, IRotationData {
    private double time;
    private double velocityX, velocityY, velocityZ;
    private double rotationX, rotationY, rotationZ;

    public InertialVector(double time, double vX, double vY, double vZ, double rX, double rY, double rZ) {
        this.time = time;
        this.velocityX = vX;
        this.velocityY = vY;
        this.velocityZ = vZ;
        this.rotationX = rX;
        this.rotationY = rY;
        this.rotationZ = rZ;
    }

    @Override
    public double getDistanceX() {
        return getVelocityX() / getTime();
    }

    @Override
    public double getDistanceY() {
        return getVelocityY() / getTime();
    }

    @Override
    public double getDistanceZ() {
        return getVelocityZ() / getTime();
    }


    @Override
    public double getTime() {
        return time;
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
}
