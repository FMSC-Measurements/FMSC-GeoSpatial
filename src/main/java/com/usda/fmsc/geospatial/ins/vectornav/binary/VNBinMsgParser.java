package com.usda.fmsc.geospatial.ins.vectornav.binary;

import com.usda.fmsc.geospatial.base.parsers.BaseBinMsgParser;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.OutputGroups;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.AttitudeBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.CommonBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.IMUBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.TimeBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;

public class VNBinMsgParser extends BaseBinMsgParser<VNBinMessage, IVNBinMsgListener<VNBinMessage>> {
    private BinaryMsgConfig config;

    
    public VNBinMsgParser() {
        setBinMsgConfig(null);
    }
    
    public VNBinMsgParser(BinaryMsgConfig config) {
        setBinMsgConfig(config);
    }

    public VNBinMsgParser(BinaryMsgConfig config, byte[] delimiter) {
        super(delimiter);
        setBinMsgConfig(config);
    }

    public VNBinMsgParser(BinaryMsgConfig config, long longestPause) {
        super(longestPause);
        setBinMsgConfig(config);
    }


    public void setBinMsgConfig(BinaryMsgConfig config) {
        this.config = config;
    }


    @Override
    protected VNBinMessage parseMessage(byte[] data) {
        VNBinMessage message = null;

        if (data.length >= VNBinMessage.MIN_PACKET_SIZE && data[0] == VNBinMessage.SYNC_BYTE) {
            BinaryMsgConfig config = this.config == null ? BinaryMsgConfig.fromBytes(data) : this.config;

            if (!config.hasGroupField2()) {
                switch (config.getOutputGroups().getValue()) {
                    case OutputGroups.Common: {
                        return new CommonBinMessage(config, data);
                    }
                    case OutputGroups.Time: {
                        return new TimeBinMessage(config, data);
                    }
                    case OutputGroups.IMU: {
                        return new IMUBinMessage(config, data);
                    }
                    case OutputGroups.Attitude: {
                        return new AttitudeBinMessage(config, data);
                    }
                }
            }
        }

        return message;
    }
}
