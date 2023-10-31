package com.usda.fmsc.geospatial.ins;

public interface IINSData extends IVectorData, IVelocityData, IRotationData{
    long getTimeSinceStart();
    boolean isConsecutive();
}
