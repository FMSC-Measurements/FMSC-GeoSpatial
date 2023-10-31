package com.usda.fmsc.geospatial.ins.vectornav;

import com.usda.fmsc.geospatial.ins.IINSData;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.CommonBinMessage;

public class VNInsData extends CommonBinMessage implements IINSData {
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

    public VNInsData(CommonBinMessage message, boolean isConsecutive) {
        super(message);
        this.isConsecutive = isConsecutive;
    }

    @Override
    public double getDistanceX() {
        return getVelocityX() / getTimespan();
    }

    @Override
    public double getDistanceY() {
        return getVelocityY() / getTimespan();
    }

    @Override
    public double getDistanceZ() {
        return getVelocityZ() / getTimespan();
    }

    @Override
    public double getTimespan() {
        return getDeltaTime();
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
    public long getTimeSinceStart() {
        return getTimeStatup();
    }

    @Override
    public boolean isConsecutive() {
        return isConsecutive;
    }

    @Override
    public String toString() {
        return String.format("[INS]%s%s", super.toString(), isConsecutive ? "" : " [Not Consecutive]");
    }

    
}
