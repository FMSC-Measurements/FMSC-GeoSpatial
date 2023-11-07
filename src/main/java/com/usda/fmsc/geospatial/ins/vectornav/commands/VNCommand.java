package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;

public abstract class VNCommand {
    public static final String DELIMITER = ",";
    protected static final String EMPTY = "";

    private final MessageID messageID;
    private final RegisterID registerID;
    

    public VNCommand(MessageID messageID) {
        this(messageID, RegisterID.NONE);
    }
    
    public VNCommand(MessageID messageID, RegisterID registerID) {
        this.messageID = messageID;
        this.registerID = registerID;

        if (registerID == RegisterID.UNKNOWN) throw new RuntimeException("Unknown RegisterID");
    }

    public MessageID getMessageID() {
        return messageID;
    }

    public RegisterID getRegisterID() {
        return registerID;
    }

    protected boolean hasPayload() {
        return false;
    }

    protected String getPayload() {
        return EMPTY;
    }

    public final String toSentence() {
        return String.format("$VN%s%s%s*XX\r\n",
            messageID.toStringCode(),
            registerID != RegisterID.NONE ? String.format("%s%d", DELIMITER, registerID.getValue()) : EMPTY,
            hasPayload() ? String.format("%s%s", DELIMITER, getPayload()) : EMPTY);
    }

    public final byte[] toBytes() {
        return toSentence().getBytes();
    }

    @Override
    public String toString() {
        return toSentence();
    }
}
