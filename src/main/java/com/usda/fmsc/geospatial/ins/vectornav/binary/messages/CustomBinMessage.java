package com.usda.fmsc.geospatial.ins.vectornav.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.joda.time.DateTime;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.codes.UomElevation;
import com.usda.fmsc.geospatial.ins.Data3DF;
import com.usda.fmsc.geospatial.ins.NorthEastDown;
import com.usda.fmsc.geospatial.ins.YawPitchRoll;
import com.usda.fmsc.geospatial.ins.vectornav.INSStatus;
import com.usda.fmsc.geospatial.ins.vectornav.VPEStatus;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.AttitudeGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.CommonGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.IMUGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.TimeGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.TimeStatus;

public class CustomBinMessage extends VNBinMessage {
    public static final int QUATERNION_SIZE = 4;
    public static final int DIRECT_COSINE_MATRIX_SIZE = 9;

    //Common
    private Long timeStatup;            // uint64
    private Long timeSyncIn;            // uint64
    private Long timeGps;               // uint64
    private YawPitchRoll yawPitchRoll;  // float[3]
    private float[] quaternion;         // float[4]
    private Data3DF angularRate;        // float[3]
    private Position position;          //double[3]
    private Data3DF velocity;           // float[3]
    private Data3DF acceleration;       // float[3]
    private Data3DF imuAcceleration;    // float[3]
    private Data3DF imuAngularRate;     // float[3]
    private Data3DF magnetic;           // float[3]
    private Float temperature;          // float
    private Float pressure;             // float
    private Float deltaTime;            // float
    private Data3DF deltaTheta;         // float[3]
    private Data3DF deltaVelocity;      // float[3]
    private Short insStatus;            // uint16
    private Long syncInCnt;             // uint32
    private Long timeGpsPps;            // uint64

    //Time
    private Long gpsTow;
    private Integer gpsWeek;
    private DateTime utcTime;
    private Long syncOutCnt;
    private TimeStatus timeStatus;

    //IMU
    private Integer imuStatus;
    private Data3DF uncompMag;
    private Data3DF uncompAccel;
    private Data3DF uncompGyro;

    //Attitude
    private Short vpeStatus;
    private float[] directCosineMatrix;
    private NorthEastDown magneticNED;
    private NorthEastDown accelerationNED;
    private Data3DF linearAccelBody;
    private NorthEastDown linearAccelNED;
    private YawPitchRoll yprUncertainty;
    private Float heave;
    private Float heaveRate;
    private Float delayedHeave;


    public CustomBinMessage(byte[] message) {
        this(null, message);
    }

    public CustomBinMessage(BinaryMsgConfig config, byte[] message) {
        super(config, message);
    }


    @Override
    protected void parseMessage(ByteBuffer message) {
        message.order(ByteOrder.LITTLE_ENDIAN);
        message.position(getBinaryMsgConfig().getHeaderSize());

        CommonGroup commonGroup = getBinaryMsgConfig().getCommonGroup();
        
        if (commonGroup.hasTimeStartup()) {
            timeStatup = message.getLong();
        }
        
        if (commonGroup.hasTimeGps()) {
            timeGps = message.getLong();
        }

        if (commonGroup.hasTimeSyncIn()) {
            timeSyncIn = message.getLong();
        }

        if (commonGroup.hasYawPitchRoll()) {
            yawPitchRoll = new YawPitchRoll(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (commonGroup.hasQuaternion()) {
            quaternion = new float[] {
                message.getFloat(),
                message.getFloat(),
                message.getFloat(),
                message.getFloat()
            };
        }

        if (commonGroup.hasAngularRate()) {
            angularRate = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (commonGroup.hasPosition()) {
            position = new Position(
                message.getDouble(),
                message.getDouble(),
                message.getDouble(),
                UomElevation.Meters);
        }

        if (commonGroup.hasVelocity()) {
            velocity = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (commonGroup.hasAcceleration()) {
            acceleration = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (commonGroup.hasIMU()) {
            imuAcceleration = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());

            imuAngularRate = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (commonGroup.hasMagPres()) {
            magnetic = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());

            temperature = message.getFloat();
            pressure = message.getFloat();
        }

        if (commonGroup.hasDeltaTheta()) {
            deltaTime = message.getFloat();

            deltaTheta = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());;

            deltaVelocity = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (commonGroup.hasStatus()) {
            insStatus = message.getShort();
        }

        if (commonGroup.hasSyncInCnt()) {
            syncInCnt = message.getInt() & 0x00000000FFFFFFFFL;
        }

        if (commonGroup.hasTimeGpsPps()) {
            timeGpsPps = message.getLong();
        }
    

        TimeGroup timeGroup = getBinaryMsgConfig().getTimeGroup();

        if (timeGroup.hasTimeStartup()) {
            timeStatup = message.getLong();
        }

        if (timeGroup.hasTimeGps()) {
            timeGps = message.getLong();
        }
        
        if (timeGroup.hasGpsTow()) {
            gpsTow = message.getLong();
        }
        
        if (timeGroup.hasGpsWeek()) {
            gpsWeek = message.getShort() & 0xFFFF;
        }
        
        if (timeGroup.hasTimesyncin()) {
            timeSyncIn = message.getLong();
        }
        
        if (timeGroup.hasTimeGpsPps()) {
            timeGpsPps = message.getLong();
        }
        
        if (timeGroup.hasTimeUTC()) {
            utcTime = new DateTime(
                message.get() + 2000, //year
                message.get() & 0xFF, //month
                message.get() & 0xFF, //day
                message.get() & 0xFF, //hour
                message.get() & 0xFF, //minute
                message.get() & 0xFF, //second
                message.getShort() & 0xFFFF);//millisecond
        }
        
        if (timeGroup.hasSyncInCnt()) {
            syncInCnt = message.getInt() & 0xFFFFFFFFL;
        }
        
        if (timeGroup.hasSyncOutCnt()) {
            syncOutCnt = message.getInt() & 0xFFFFFFFFL;
        }
        
        if (timeGroup.hasTimeStartup()) {
            timeStatus = new TimeStatus(message.get());
        }
    
    
        IMUGroup imuGroup = getBinaryMsgConfig().getImuGroup();

        if (imuGroup.hasImuStatus()) {
            imuStatus = message.getShort() & 0xFFFF;
        }
        
        if (imuGroup.hasUncompMag()) {
            uncompMag = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasUncompAccel()) {
            uncompAccel = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasUncompGyro()) {
            uncompGyro = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasTemp()) {
            temperature = message.getFloat();
        }
        
        if (imuGroup.hasPressure()) {
            pressure = message.getFloat();
        }
        
        if (imuGroup.hasDeltaTheta()) {
            deltaTime = message.getFloat();
            deltaTheta = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasDeltaVelocity()) {
            deltaVelocity = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasMagnetic()) {
            magnetic = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasAcceleration()) {
            acceleration = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
        
        if (imuGroup.hasAngularRate()) {
            angularRate = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }
    
    
        AttitudeGroup attitudeGroup = getBinaryMsgConfig().getAttitudeGroup();

        if (attitudeGroup.hasVpeStatus()) {
            vpeStatus = message.getShort();
        }

        if (attitudeGroup.hasYawPitchRoll()) {
            yawPitchRoll = new YawPitchRoll(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (attitudeGroup.hasQuaternion()) {
            quaternion = new float[QUATERNION_SIZE];

            for (int i = 0; i < QUATERNION_SIZE; i++)
                quaternion[i] = message.getFloat(); 
        }

        if (attitudeGroup.hasDCM()) {
            directCosineMatrix = new float[DIRECT_COSINE_MATRIX_SIZE];

            for (int i = 0; i < DIRECT_COSINE_MATRIX_SIZE; i++)
                directCosineMatrix[i] = message.getFloat(); 
        }

        if (attitudeGroup.hasMagNED()) {
            magneticNED = new NorthEastDown(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (attitudeGroup.hasAccelNED()) {
            accelerationNED = new NorthEastDown(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
            
        }

        if (attitudeGroup.hasLinearAccelBody()) {
            linearAccelBody = new Data3DF(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (attitudeGroup.hasLinearAccelNED()) {
            linearAccelNED = new NorthEastDown(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (attitudeGroup.hasYprU()) {
            yprUncertainty = new YawPitchRoll(
                message.getFloat(),
                message.getFloat(),
                message.getFloat());
        }

        if (attitudeGroup.hasHeave()) {
            heave = message.getFloat();
            heaveRate = message.getFloat();
            delayedHeave = message.getFloat();
        }
    }
    

    //region Common Fields
    public boolean hasTimeStartup() {
        return timeStatup != null;
    }
    public long getTimeStatup() {
        return timeStatup;
    }

    public boolean hasTimeSyncIn() {
        return timeSyncIn != null;
    }
    public long getTimeSyncIn() {
        return timeSyncIn;
    }

    public boolean hasTimeGps() {
        return timeGps != null;
    }
    public long getTimeGps() {
        return timeGps;
    }

    public boolean hasYawPitchRoll() {
        return yawPitchRoll != null;
    }
    public YawPitchRoll getYawPitchRoll() {
        return yawPitchRoll;
    }

    public boolean hasQuaternion() {
        return quaternion != null;
    }
    public float[] getQuaternion() {
        return quaternion;
    }

    public boolean hasAngularRate() {
        return angularRate != null;
    }
    public Data3DF getAngularRate() {
        return angularRate;
    }

    public boolean hasPosition() {
        return position != null;
    }
    public Position getPosition() {
        return position;
    }

    public boolean hasVelocity() {
        return velocity != null;
    }
    public Data3DF getVelocity() {
        return velocity;
    }

    public boolean hasAcceleration() {
        return acceleration != null;
    }
    public Data3DF getAcceleration() {
        return acceleration;
    }

    public boolean hasImuAcceleration() {
        return imuAcceleration != null;
    }
    public Data3DF getImuAcceleration() {
        return imuAcceleration;
    }

    public boolean hasImuAngularRate() {
        return imuAngularRate != null;
    }
    public Data3DF getImuAngularRate() {
        return imuAngularRate;
    }

    public boolean hasMagnetic() {
        return magnetic != null;
    }
    public Data3DF getMagnetic() {
        return magnetic;
    }

    public boolean hasTemperature() {
        return temperature != null;
    }
    public float getTemperature() {
        return temperature;
    }

    public boolean hasPressure() {
        return pressure != null;
    }
    public float getPressure() {
        return pressure;
    }

    public boolean hasDeltaTime() {
        return deltaTime != null;
    }
    public float getDeltaTime() {
        return deltaTime;
    }

    public boolean hasDeltaTheta() {
        return deltaTheta != null;
    }
    public Data3DF getDeltaTheta() {
        return deltaTheta;
    }

    public boolean hasDeltaVelocity() {
        return deltaVelocity != null;
    }
    public Data3DF getDeltaVelocity() {
        return deltaVelocity;
    }

    public boolean hasVPEStatus() {
        return vpeStatus != null;
    }
    public VPEStatus getVPEStatus() {
        return new VPEStatus(vpeStatus);
    }

    public boolean hasInsStatus() {
        return insStatus != null;
    }
    public INSStatus getInsStatus() {
        return new INSStatus(insStatus);
    }

    public boolean hasSyncInCnt() {
        return syncInCnt != null;
    }
    public long getSyncInCnt() {
        return syncInCnt;
    }

    public boolean hasTimeGpsPps() {
        return timeGpsPps != null;
    }
    public long getTimeGpsPps() {
        return timeGpsPps;
    }
    //endregion

    //region Time Fields
    public boolean hasGpsTow() {
        return gpsTow != null;
    }
    public long getGpsTow() {
        return gpsTow;
    }

    public boolean hasGpsWeek() {
        return gpsWeek != null;
    }
    public int getGpsWeek() {
        return gpsWeek;
    }

    public boolean hasUtcTime() {
        return utcTime != null;
    }
    public DateTime getUtcTime() {
        return utcTime;
    }
    
    public boolean hasSyncOutCnt() {
        return syncOutCnt != null;
    }
    public long getSyncOutCnt() {
        return syncOutCnt;
    }

    public boolean hasTimeStatus() {
        return timeStatus != null;
    }
    public TimeStatus getTimeStatus() {
        return timeStatus;
    }


    //region IMU Fields
    public boolean hasImuStatus() {
        return imuStatus != null;
    }
    public int getImuStatus() {
        return imuStatus;
    }

    public boolean hasUncompMag() {
        return uncompMag != null;
    }
    public Data3DF getUncompMag() {
        return uncompMag;
    }

    public boolean hasUncompAccel() {
        return uncompAccel != null;
    }
    public Data3DF getUncompAccel() {
        return uncompAccel;
    }

    public boolean hasUncompGyro() {
        return uncompGyro != null;
    }
    public Data3DF getUncompGyro() {
        return uncompGyro;
    }
    //endregion

    //region Attitude
    public boolean hasDirectCOsineMatrix() {
        return directCosineMatrix != null;
    }
    public float[] getDirectCosineMatrix() {
        return directCosineMatrix;
    }

    public boolean hasMagNED() {
        return magneticNED != null;
    }
    public NorthEastDown getMagneticNED() {
        return magneticNED;
    }

    public boolean hasAccelNED() {
        return accelerationNED != null;
    }
    public NorthEastDown getAccelerationNED() {
        return accelerationNED;
    }

    public boolean hasLinearAccelBody() {
        return linearAccelBody != null;
    }
    public Data3DF getLinearAccelBody() {
        return linearAccelBody;
    }

    public boolean hasLinearAccelNED() {
        return linearAccelNED != null;
    }
    public NorthEastDown getLinearAccelNED() {
        return linearAccelNED;
    }

    public boolean hasYprUncertainty() {
        return yprUncertainty != null;
    }
    public YawPitchRoll getYPRUncertainty() {
        return yprUncertainty;
    }

    public boolean hasHeave() {
        return heave != null;
    }
    public float getHeave() {
        return heave;
    }

    public boolean hasHeaveRate() {
        return heaveRate != null;
    }
    public float getHeaveRate() {
        return heaveRate;
    }

    public boolean hasDelayedHeave() {
        return delayedHeave != null;
    }
    public float getDelayedHeave() {
        return delayedHeave;
    }
    //endregion
}
