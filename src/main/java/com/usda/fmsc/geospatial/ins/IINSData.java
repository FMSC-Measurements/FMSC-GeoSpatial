package com.usda.fmsc.geospatial.ins;

public interface IINSData extends IVectorData, ILinearAccelerationData, IVelocityData, IRotationData, IOrientationData {
    long getTimeSinceStart();
    boolean isConsecutive();
}
