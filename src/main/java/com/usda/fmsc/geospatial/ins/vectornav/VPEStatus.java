package com.usda.fmsc.geospatial.ins.vectornav;

import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.AttitudeQuality;

public class VPEStatus {
    private static final int ATTITUDE_QUALITY_MASK = 0b11;
    private static final int GYRO_SATUARTION_MASK = 1 << 2;
    private static final int GYRO_SATUARTION_RECOVERY_MASK = 1 << 3;
    private static final int MAG_DISTURBANCE_BIT_OFFSET = 4;
    private static final int MAG_SATURATION_MASK = 1 << 6;
    private static final int ACC_DISTURBANCE_BIT_OFFSET = 7;
    private static final int ACC_SATURATION_MASK = 1 << 9;
    private static final int KNOWN_MAG_DISTURBANCE_MASK = 1 << 11;
    private static final int KNOWN_ACCEL_DISTURBANCE_MASK = 1 << 12;


    private short statusData;

    public VPEStatus(short data) {
        this.statusData = data;
    }


    public short getValue() {
        return statusData;
    }


    public AttitudeQuality getAttitudeQuiality() {
        return AttitudeQuality.parse(statusData & ATTITUDE_QUALITY_MASK);
    }

    public boolean hasGyroSaturation() {
        return (statusData & GYRO_SATUARTION_MASK) == GYRO_SATUARTION_MASK;
    }

    public boolean hasGyroSaturationRecovery() {
        return (statusData & GYRO_SATUARTION_RECOVERY_MASK) == GYRO_SATUARTION_RECOVERY_MASK;
    }

    public int getMagDisturbance() {
        return (statusData >> MAG_DISTURBANCE_BIT_OFFSET) & 0b11;
    }

    public boolean hasMagSaturation() {
        return (statusData & MAG_SATURATION_MASK) == MAG_SATURATION_MASK;
    }

    public int getAccelDisturbance() {
        return (statusData >> ACC_DISTURBANCE_BIT_OFFSET) & 0b11;
    }

    public boolean hasAccelSaturation() {
        return (statusData & ACC_SATURATION_MASK) == ACC_SATURATION_MASK;
    }

    public boolean hasKnownMagDisturbance() {
        return (statusData & KNOWN_MAG_DISTURBANCE_MASK) == KNOWN_MAG_DISTURBANCE_MASK;
    }

    public boolean hasKnownAccelDisturbance() {
        return (statusData & KNOWN_ACCEL_DISTURBANCE_MASK) == KNOWN_ACCEL_DISTURBANCE_MASK;
    }
}
