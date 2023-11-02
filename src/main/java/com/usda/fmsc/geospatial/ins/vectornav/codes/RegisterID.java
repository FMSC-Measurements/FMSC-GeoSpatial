package com.usda.fmsc.geospatial.ins.vectornav.codes;

import com.usda.fmsc.geospatial.nmea.NmeaTools;

public enum RegisterID {
    UNKNOWN(Integer.MIN_VALUE),
    NONE(Integer.MAX_VALUE),
    
    IMU(54), //IMU Measurements
    DTV(80), //Delta Theta and Delta Velocity

    YPR(8), //Yaw, Pitch, and Roll
    QTN(9), //Attitude Quaternion
    YMR(27), //Yaw, Pitch, Roll, Magnetic, Acceleration, and Angular Rates
    QMR(15), //Quaternion, Magnetic, Acceleration, and Angular Rates
    MAG(17), //Magnetic Measurements
    ACC(18), //Acceleration Measurements
    GYR(19), //Angular Rate Measurements
    MAR(20), //Magnetic, Acceleration and Angular Rates
    YBA(239), //Yaw, Pitch, Roll, True Body Acceleration, and Angular Rates
    YIA(240), //Magnetic, True Acceleration, and Angular Rates
    HVE(115), //Heave

    USER_TAG(0),
    MODEL_NUMBER(1),
    HARDWARE_REVISION(2),
    SERIAL_NUMBER(3),
    FIRMWARE_VERSION(4),
    SERIAL_BAUD_RATE(5),
    ASYNC_DATA_OUTPUT(6),
    ASYNC_DATA_OUTPUT_FREQ(7),
    COMM_PROTO_CTRL(30),
    SYNC_CTRL(32),
    SYNC_STATUS(33),
    BINARY_OUTPUT_1(75),
    BINARY_OUTPUT_2(76),
    BINARY_OUTPUT_3(77),

    MAG_GRAV_REF_VEC(21),
    MAG_COMP(23),
    ACCEL_COMP(25),
    REF_FRAME_ROT(26),
    VPE_BASIC_CTRL(35),
    VPE_MAG_BASIC_TUNE(36),
    VPE_GYRO_BASIC_TUNE(40),
    MAG_CAL_CTRL(44),
    FLTR_STARTUP_GYRO_BIAS(43),
    CALC_MAG_CAL(47),
    VEL_COMP_MEAS(50),
    VEL_COMP_CTRL(51),
    REF_VEC_CONFIG(83),
    GYRO_COMP(84),
    IMU_FILTER_CONFIG(85),
    HEAVE_CONFIG(116);

    private final int value;

    RegisterID(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RegisterID parse(int value) {
        for (RegisterID rid : values()) {
            if (rid.getValue() == value) return rid;
        }
        throw new IllegalArgumentException("Invalid RegisterId value: " + value);
    }

    public static RegisterID parse(String nmea) {
        String[] tokens = NmeaTools.tokenize(nmea);

        if (tokens.length > 2) {
            try {
                return parse(Integer.parseInt(tokens[1]));
            } catch (Exception e) {
                //
            }
        }

        return RegisterID.UNKNOWN;
    }
}
