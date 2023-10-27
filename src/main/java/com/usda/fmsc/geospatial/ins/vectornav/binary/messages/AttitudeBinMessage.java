package com.usda.fmsc.geospatial.ins.vectornav.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.usda.fmsc.geospatial.ins.Data3DF;
import com.usda.fmsc.geospatial.ins.NorthEastDown;
import com.usda.fmsc.geospatial.ins.YawPitchRoll;
import com.usda.fmsc.geospatial.ins.vectornav.VPEStatus;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.AttitudeGroup;

public class AttitudeBinMessage extends VNBinMessage {
    public static final int QUATERNION_SIZE = 4;
    public static final int DIRECT_COSINE_MATRIX_SIZE = 9;

    private AttitudeGroup attitudeGroup;
    
    private short vpeStatus;
    private YawPitchRoll yawPitchRoll;
    private float[] quaternion;
    private float[] directCosineMatrix;
    private NorthEastDown magneticNED;
    private NorthEastDown accelerationNED;
    private Data3DF linearAccelBody;
    private NorthEastDown linearAccelNED;
    private YawPitchRoll yprUncertainty;
    private float heave;
    private float heaveRate;
    private float delayedHeave;


    public AttitudeBinMessage(byte[] message) {
        this(null, message);
    }

    public AttitudeBinMessage(BinaryMsgConfig config, byte[] message) {
        super(config, message);
        this.attitudeGroup = getBinaryMsgConfig().getAttitudeGroup();
    }


    @Override
    protected void parseMessage(ByteBuffer message) {
        message.order(ByteOrder.LITTLE_ENDIAN);
        message.position(getBinaryMsgConfig().getHeaderSize());
        
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


    public VPEStatus getVPEStatus() {
        return new VPEStatus(vpeStatus);
    }

    public YawPitchRoll getYawPitchRoll() {
        return yawPitchRoll;
    }

    public float[] getQuaternion() {
        return quaternion;
    }

    public float[] getDirectCosineMatrix() {
        return directCosineMatrix;
    }

    public NorthEastDown getMagneticNED() {
        return magneticNED;
    }

    public NorthEastDown getAccelerationNED() {
        return accelerationNED;
    }

    public Data3DF getLinearAccelBody() {
        return linearAccelBody;
    }

    public NorthEastDown getLinearAccelNED() {
        return linearAccelNED;
    }

    public YawPitchRoll getYPRUncertainty() {
        return yprUncertainty;
    }

    public float getHeave() {
        return heave;
    }

    public float getHeaveRate() {
        return heaveRate;
    }

    public float getDelayedHeave() {
        return delayedHeave;
    }
}
