package com.usda.fmsc.geospatial.ins.vectornav;

import com.usda.fmsc.geospatial.ins.Data3DF;
import com.usda.fmsc.geospatial.ins.IINSData;
import com.usda.fmsc.geospatial.ins.YawPitchRoll;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.CustomBinMessage;

public class VNInsData extends CustomBinMessage implements IINSData {
    final boolean isConsecutive;

    public VNInsData(byte[] message) {
        this(message, true);
    }

    public VNInsData(byte[] message, boolean isConsecutive) {
        this(null, message);
    }

    public VNInsData(BinaryMsgConfig config, byte[] message) {
        this(config, message, true);
    }

    public VNInsData(BinaryMsgConfig config, byte[] message, boolean isConsecutive) {
        super(config, message);
        this.isConsecutive = isConsecutive;
    }


    @Override
    public double getDistanceX() {
        return getLinearAccelBody().getX() * getTimeSpan();
    }

    @Override
    public double getDistanceY() {
        return getLinearAccelBody().getY() * getTimeSpan();
    }

    @Override
    public double getDistanceZ() {
        return getLinearAccelBody().getZ() * getTimeSpan();
    }

    @Override
    public double getTimeSpan() {
        return getDeltaTime();
    }


    @Override
    public double getLinearAccelX() {
        return getLinearAccelBody().getX();
    }

    @Override
    public double getLinearAccelY() {
        return getLinearAccelBody().getY();
    }

    @Override
    public double getLinearAccelZ() {
        return getLinearAccelBody().getZ();
    }


    @Override
    public double getAccelX() {
        return getAcceleration().getX();
    }

    @Override
    public double getAccelY() {
        return getAcceleration().getY();
    }

    @Override
    public double getAccelZ() {
        return getAcceleration().getZ();
    }


    @Override
    public double getVelocityX() {
        return getDeltaVelocity().getX();
    }

    @Override
    public double getVelocityY() {
        return getDeltaVelocity().getY();
    }

    @Override
    public double getVelocityZ() {
        return getDeltaVelocity().getZ();
    }


    @Override
    public double getYaw() {
        return getYawPitchRoll().getYaw();
    }

    @Override
    public double getPitch() {
        return getYawPitchRoll().getPitch();
    }

    @Override
    public double getRoll() {
        return getYawPitchRoll().getRoll();
    }


    @Override
    public double getRotationX() {
        return getDeltaTheta().getX();
    }

    @Override
    public double getRotationY() {
        return getDeltaTheta().getY();
    }

    @Override
    public double getRotationZ() {
        return getDeltaTheta().getZ();
    }


    @Override
    public long getTimeSinceStart() {
        return getTimeStatup();
    }

    @Override
    public boolean isConsecutive() {
        return isConsecutive;
    }

    @Override
    public String toString() {
        Data3DF ld = getLinearAccelBody();
        Data3DF dv = getDeltaVelocity();
        Data3DF dt = getDeltaTheta();
        YawPitchRoll ypr = getYawPitchRoll();

        return String.format("[INS] T%f | LD: (%f : %f : %f) | DV: (%f : %f : %f) |  DT: (%f : %f : %f) | YPR: (%f : %f : %f)%s",
                getDeltaTime(),
                getDistanceX(), getDistanceY(), getDistanceZ(),
                dv.getX(), dv.getY(), dv.getZ(),
                dt.getX(), dt.getY(), dt.getZ(),
                ypr.getYaw(), ypr.getPitch(), ypr.getRoll(),
                isConsecutive ? "" : " [Not Consecutive]");
    }
}
