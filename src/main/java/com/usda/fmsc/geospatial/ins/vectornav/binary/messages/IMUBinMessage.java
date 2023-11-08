package com.usda.fmsc.geospatial.ins.vectornav.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.usda.fmsc.geospatial.ins.Data3DF;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.IMUGroup;

public class IMUBinMessage extends VNBinMessage {
    private int imuStatus;
    private Data3DF uncompMag;
    private Data3DF uncompAccel;
    private Data3DF uncompGyro;
    private float temperature;
    private float pressure;
    private float deltaTime;
    private Data3DF deltaTheta;
    private Data3DF deltaVelocity;
    private Data3DF magnetic;
    private Data3DF acceleration;
    private Data3DF angularRate;


    public IMUBinMessage(byte[] message) {
        this(null, message);
    }

    public IMUBinMessage(BinaryMsgConfig config, byte[] message) {
        super(config, message);
    }


    @Override
    protected void parseMessage(ByteBuffer message) {
        message.order(ByteOrder.LITTLE_ENDIAN);
        message.position(getBinaryMsgConfig().getHeaderSize());
        
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
    }

    public int getImuStatus() {
        return imuStatus;
    }

    public Data3DF getUncompMag() {
        return uncompMag;
    }

    public Data3DF getUncompAccel() {
        return uncompAccel;
    }

    public Data3DF getUncompGyro() {
        return uncompGyro;
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

    public Data3DF getMagnetic() {
        return magnetic;
    }

    public Data3DF getAcceleration() {
        return acceleration;
    }

    public Data3DF getAngularRate() {
        return angularRate;
    }
}
