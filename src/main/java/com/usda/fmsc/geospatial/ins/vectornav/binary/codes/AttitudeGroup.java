package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class AttitudeGroup extends BaseGroup {
    public static final int None = 0;
    public static final int VpeStatus = 1 << 0;
    public static final int YawPitchRoll = 1 << 1;
    public static final int Quaternion = 1 << 2;
    public static final int DCM = 1 << 3;
    public static final int MagNed = 1 << 4;
    public static final int AccelNed = 1 << 5;
    public static final int LinearAccelBody = 1 << 6;
    public static final int LinearAccelNed = 1 << 7;
    public static final int YprU = 1 << 8;
    public static final int Heave = 1 << 12;

    public static final int ALL_FIELDS_VN100 = 0b10111111111;

    public static final byte[] FIELD_SIZES = { 2, 12, 16, 36, 12, 12, 12, 12, 12, 12, 28, 24, 0,  0, 0, 0 };


    public AttitudeGroup(int value) {
        super(value);
    }
    

    @Override
    protected byte[] getFieldSizes() {
        return FIELD_SIZES;
    }


    public boolean hasVpeStatus() {
        return (value & VpeStatus) == VpeStatus;
    }

    public boolean hasYawPitchRoll() {
        return (value & YawPitchRoll) == YawPitchRoll;
    }

    public boolean hasQuaternion() {
        return (value & Quaternion) == Quaternion;
    }

    public boolean hasDCM() {
        return (value & DCM) == DCM;
    }

    public boolean hasMagNED() {
        return (value & MagNed) == MagNed;
    }

    public boolean hasAccelNED() {
        return (value & AccelNed) == AccelNed;
    }

    public boolean hasTimeSyncIn() {
        return (value & MagNed) == MagNed;
    }

    public boolean hasLinearAccelBody() {
        return (value & LinearAccelBody) == LinearAccelBody;
    }

    public boolean hasLinearAccelNED() {
        return (value & LinearAccelNed) == LinearAccelNed;
    }

    public boolean hasYprU() {
        return (value & YprU) == YprU;
    }

    public boolean hasHeave() {
        return (value & Heave) == Heave;
    }
}
