package com.usda.fmsc.geospatial.ins;

public interface IRawIMUData {
    double getMagX();
    double getMagY();
    double getMagZ();

    double getAccelX();
    double getAccelY();
    double getAccelZ();

    double getGyroX();
    double getGyroY();
    double getGyroZ();
}
