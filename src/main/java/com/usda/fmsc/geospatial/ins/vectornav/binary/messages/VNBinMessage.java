package com.usda.fmsc.geospatial.ins.vectornav.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.usda.fmsc.geospatial.Utils;
import com.usda.fmsc.geospatial.base.messages.IBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.BaseGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.OutputGroups;

public abstract class VNBinMessage implements IBinMessage {
    public static final int SYNC_IDX = 0;
    public static final int MIN_PACKET_SIZE = 8;
    public static final byte SYNC_BYTE = (byte)0xFA;
    public static final int GROUPS_IDX = 1;

    private final boolean _isValid;
    private BinaryMsgConfig _config;


    protected VNBinMessage() {
        this._isValid = true;
    }
    
    public VNBinMessage(byte[] message) {
        this(null, message);
    }

    protected VNBinMessage(BinaryMsgConfig config) {
        this._config = config;
        this._isValid = true;
    }

    public VNBinMessage(BinaryMsgConfig config, byte[] message) {
        ByteBuffer msgBuf = ByteBuffer.wrap(message).order(ByteOrder.LITTLE_ENDIAN);
        this._isValid = (message.length >= MIN_PACKET_SIZE) && validateChecksum(message) &&
            (this._config = config != null ? config : BinaryMsgConfig.fromBytes(msgBuf)) != null &&
            validate(msgBuf);

        if (this._isValid) {
            parseMessage(msgBuf);
        }
    }

    
    protected abstract void parseMessage(ByteBuffer message);


    public BinaryMsgConfig getBinaryMsgConfig() {
        return _config;
    }

    public OutputGroups getUsedGroups() {
        return _config.getOutputGroups();
    }

    public int getHeaderSize() {
        return _config.getHeaderSize();
    }


    public boolean isValid() {
        return _isValid;
    }


    public final int getFieldDataOffset(BaseGroup group, int groupOffset, int field) {
        return group.getFieldDataOffset(field) + groupOffset + getBinaryMsgConfig().getHeaderSize();
    }

    public final int getFieldDataOffsetByIndex(BaseGroup group, int groupOffset, int index) {
        return group.getFieldDataOffsetByIndex(index) + groupOffset + getBinaryMsgConfig().getHeaderSize();
    }


    protected final boolean checkUsedGroupsMatch(ByteBuffer message) {
        return getUsedGroups().getValue() == message.get(GROUPS_IDX);
    }

    protected boolean validate(ByteBuffer message) {
        return checkUsedGroupsMatch(message);
    }

    
    public static boolean validateChecksum(byte[] message) {
        return Utils.validateChecksum16(message, 1, message.length - 1);
    }
}
