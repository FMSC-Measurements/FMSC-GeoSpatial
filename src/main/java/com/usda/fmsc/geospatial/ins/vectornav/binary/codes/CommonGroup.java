package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class CommonGroup extends BaseGroup {
    public static final int None = 0;
    public static final int TimeStartup = 1 << 0;
    public static final int TimeGps = 1 << 1;
    public static final int TimeSyncIn = 1 << 2;
    public static final int YawPitchRoll = 1 << 3;
    public static final int Quaternion = 1 << 4;
    public static final int AngularRate = 1 << 5;
    public static final int Position = 1 << 6;
    public static final int Velocity = 1 << 7;
    public static final int Accel = 1 << 8;
    public static final int IMU = 1 << 9;
    public static final int MagPres = 1 << 10;
    public static final int DeltaTheta = 1 << 11;
    public static final int Status = 1 << 12;
    public static final int SyncInCnt = 1 << 13;
    public static final int TimeGpsPps = 1 << 14;

    public static final int ALL_FIELDS_VN100 = 0b0011111100111101;

    public static final byte[] FIELD_SIZES = { 8, 8, 8, 12, 16, 12, 24, 12, 12, 24, 20, 28, 2, 4, 8, 0 };

    
    public CommonGroup(int value) {
        super(value);
    }


    @Override
    protected byte[] getFieldSizes() {
        return FIELD_SIZES;
    }

    public boolean hasTimestartup() {
        return (value & TimeStartup) == TimeStartup;
    }

    public boolean hasTimeGps() {
        return (value & TimeGps) == TimeGps;
    }

    public boolean hasTimeSyncIn() {
        return (value & TimeSyncIn) == TimeSyncIn;
    }

    public boolean hasYawPitchRoll() {
        return (value & YawPitchRoll) == YawPitchRoll;
    }

    public boolean hasQuaternion() {
        return (value & Quaternion) == Quaternion;
    }

    public boolean hasAngularRate() {
        return (value & AngularRate) == AngularRate;
    }

    public boolean hasPosition() {
        return (value & Position) == Position;
    }

    public boolean hasVelocity() {
        return (value & Velocity) == Velocity;
    }

    public boolean hasAccel() {
        return (value & Accel) == Accel;
    }

    public boolean hasIMU() {
        return (value & IMU) == IMU;
    }

    public boolean hasMagPres() {
        return (value & MagPres) == MagPres;
    }

    public boolean hasDeltaTheta() {
        return (value & DeltaTheta) == DeltaTheta;
    }

    public boolean hasStatus() {
        return (value & Status) == Status;
    }

    public boolean hasSyncInCnt() {
        return (value & SyncInCnt) == SyncInCnt;
    }

    public boolean hasTimeGpsPps() {
        return (value & TimeGpsPps) == TimeGpsPps;
    }
}
