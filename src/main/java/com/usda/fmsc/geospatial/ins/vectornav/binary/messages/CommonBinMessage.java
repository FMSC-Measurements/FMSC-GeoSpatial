package com.usda.fmsc.geospatial.ins.vectornav.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.codes.UomElevation;
import com.usda.fmsc.geospatial.ins.Data3DF;
import com.usda.fmsc.geospatial.ins.YawPitchRoll;
import com.usda.fmsc.geospatial.ins.vectornav.INSStatus;
import com.usda.fmsc.geospatial.ins.vectornav.VPEStatus;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.CommonGroup;

public class CommonBinMessage extends VNBinMessage {
    private long timeStatup;            // uint64
    private long timeSyncIn;            // uint64
    private long timeGps;               // uint64
    private YawPitchRoll yawPitchRoll;  // float[3]
    private float[] quaternion;         // float[4]
    private Data3DF angularRate;        // float[3]
    private Position position;          //double[3]
    private Data3DF velocity;           // float[3]
    private Data3DF acceleration;       // float[3]
    private Data3DF imuAcceleration;    // float[3]
    private Data3DF imuAngularRate;     // float[3]
    private Data3DF magnetic;           // float[3]
    private float temperature;          // float
    private float pressure;             // float
    private float deltaTime;            // float
    private Data3DF deltaTheta;         // float[3]
    private Data3DF deltaVelocity;      // float[3]
    private short status;               // uint16
    private long syncInCnt;             // uint32
    private long timeGpsPps;            // uint64


    public CommonBinMessage(byte[] message) {
        this(null, message);
    }

    public CommonBinMessage(BinaryMsgConfig config, byte[] message) {
        super(config, message);
    }

    public CommonBinMessage(CommonBinMessage message) {
        super();

        this.timeStatup = message.timeStatup;
        this.timeSyncIn = message.timeSyncIn;
        this.timeGps = message.timeGps;
        this.yawPitchRoll = message.yawPitchRoll;
        this.quaternion = message.quaternion;
        this.angularRate = message.angularRate;
        this.position = message.position;
        this.velocity = message.velocity;
        this.acceleration = message.acceleration;
        this.imuAcceleration = message.imuAcceleration;
        this.imuAngularRate = message.imuAngularRate;
        this.magnetic = message.magnetic;
        this.temperature = message.temperature;
        this.pressure = message.pressure;
        this.deltaTime = message.deltaTime;
        this.deltaTheta = message.deltaTheta;
        this.deltaVelocity = message.deltaVelocity;
        this.status = message.status;
        this.syncInCnt = message.syncInCnt;
        this.timeGpsPps = message.timeGpsPps;
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
            status = message.getShort();
        }

        if (commonGroup.hasSyncInCnt()) {
            syncInCnt = message.getInt() & 0x00000000FFFFFFFFL;
        }

        if (commonGroup.hasTimeGpsPps()) {
            timeGpsPps = message.getLong();
        }
    }


    public long getTimeStatup() {
        return timeStatup;
    }

    public long getTimeSyncIn() {
        return timeSyncIn;
    }

    public long getTimeGps() {
        return timeGps;
    }

    public YawPitchRoll getYawPitchRoll() {
        return yawPitchRoll;
    }

    public float[] getQuaternion() {
        return quaternion;
    }

    public Data3DF getAngularRate() {
        return angularRate;
    }

    public Position getPosition() {
        return position;
    }

    public Data3DF getVelocity() {
        return velocity;
    }

    public Data3DF getAcceleration() {
        return acceleration;
    }

    public Data3DF getImuAcceleration() {
        return imuAcceleration;
    }

    public Data3DF getImuAngularRate() {
        return imuAngularRate;
    }

    public Data3DF getMagnetic() {
        return magnetic;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public Data3DF getDeltaTheta() {
        return deltaTheta;
    }

    public Data3DF getDeltaVelocity() {
        return deltaVelocity;
    }

    public VPEStatus getVpeStatus() {
        return new VPEStatus(status);
    }

    public INSStatus getInsStatus() {
        return new INSStatus(status);
    }

    public long getSyncInCnt() {
        return syncInCnt;
    }

    public long getTimeGpsPps() {
        return timeGpsPps;
    }


    @Override
    public String toString() {
        Data3DF dv = getDeltaVelocity();
        Data3DF acc = getAcceleration();
        YawPitchRoll ypr = getYawPitchRoll();

        return String.format("[CMN] Delta: T%f, Vel(%f : %f : %f) | Accel: (%f : %f : %f) | YPR: (%f : %f : %f)",
                getDeltaTime(), dv.getX(), dv.getY(), dv.getZ(),
                acc.getX(), acc.getY(), acc.getZ(),
                ypr.getYaw(), ypr.getPitch(), ypr.getRoll());
    }
}
