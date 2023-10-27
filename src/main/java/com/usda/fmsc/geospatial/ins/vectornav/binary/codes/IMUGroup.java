package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class IMUGroup extends BaseGroup {
    public static final int None = 0;
    public static final int ImuStatus = 1 << 0;
    public static final int UncompMag = 1 << 1;
    public static final int UncompAccel = 1 << 2;
    public static final int UncompGyro = 1 << 3;
    public static final int Temp = 1 << 4;
    public static final int Pres = 1 << 5;
    public static final int DeltaTheta = 1 << 6;
    public static final int DeltaVel = 1 << 7;
    public static final int Mag = 1 << 8;
    public static final int Accel = 1 << 9;
    public static final int AngularRate = 1 << 10;
    
    public static final int ALL_FIELDS_VN100 = 0b1111111111;

    public static final byte[] FIELD_SIZES = { 2, 12, 12, 12, 4,  4,  16, 12, 12, 12, 12, 2,  40, 0, 0, 0 };


    public IMUGroup(int value) {
        super(value);
    }

    
    @Override
    protected byte[] getFieldSizes() {
        return FIELD_SIZES;
    }


    public boolean hasImuStatus() {
        return (value & ImuStatus) == ImuStatus;
    }

    public boolean hasUncompMag() {
        return (value & UncompMag) == UncompMag;
    }

    public boolean hasUncompAccel() {
        return (value & UncompAccel) == UncompAccel;
    }

    public boolean hasUncompGyro() {
        return (value & UncompGyro) == UncompGyro;
    }

    public boolean hasTemp() {
        return (value & Temp) == Temp;
    }

    public boolean hasPressure() {
        return (value & Pres) == Pres;
    }

    public boolean hasDeltaTheta() {
        return (value & DeltaTheta) == DeltaTheta;
    }

    public boolean hasDeltaVelocity() {
        return (value & DeltaVel) == DeltaVel;
    }

    public boolean hasMagnetic() {
        return (value & Mag) == Mag;
    }

    public boolean hasAcceleration() {
        return (value & Accel) == Accel;
    }

    public boolean hasAngularRate() {
        return (value & AngularRate) == AngularRate;
    }
}
