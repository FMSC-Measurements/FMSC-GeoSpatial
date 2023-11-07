package com.usda.fmsc.geospatial.ins;

public interface IINSData extends IVectorData, IVelocityData, IRotationData, IOrientationData {
    long getTimeSinceStart();
    boolean isConsecutive();
}
