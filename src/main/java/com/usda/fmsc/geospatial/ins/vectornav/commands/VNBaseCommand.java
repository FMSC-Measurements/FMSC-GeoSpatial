package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;

public abstract class VNBaseCommand {
    public static final String DELIMITER = ",";
    protected static final String EMPTY = "";

    private final MessageID messageID;
    private final RegisterID registerID;
    

    public VNBaseCommand(MessageID messageID) {
        this(messageID, RegisterID.NONE);
    }
    
    public VNBaseCommand(MessageID messageID, RegisterID registerID) {
        this.messageID = messageID;
        this.registerID = registerID;

        if (registerID == RegisterID.UNKNOWN) throw new RuntimeException("Unknown RegisterID");
    }


    protected boolean hasPayload() {
        return false;
    }

    protected String getPayload() {
        return EMPTY;
    }

    public final String toCommandSentence() {
        return String.format("$VN%s%s%s*XX\r\n",
            messageID.toStringCode(),
            registerID != RegisterID.NONE ? String.format("%s%d", DELIMITER, registerID.getValue()) : EMPTY,
            hasPayload() ? String.format("%s%s", DELIMITER, getPayload()) : EMPTY);
    }
}
