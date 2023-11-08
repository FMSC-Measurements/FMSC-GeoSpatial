package com.usda.fmsc.geospatial.ins;

public interface IINSData extends IVectorData, ILinearAccelerationData, IAccelerationData, IVelocityData, IRotationData, IOrientationData {
    long getTimeSinceStart();
    boolean isConsecutive();
}
