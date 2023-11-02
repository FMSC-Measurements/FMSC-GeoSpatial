package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;

public abstract class BaseCommand {
    public static final String DELIMITER = ",";
    protected static final String EMPTY = "";

    private final MessageID messageID;
    private final RegisterID registerID;
    

    public BaseCommand(MessageID messageID) {
        this(messageID, RegisterID.NONE);
    }
    
    public BaseCommand(MessageID messageID, RegisterID registerID) {
        this.messageID = messageID;
        this.registerID = registerID;

        if (registerID == RegisterID.UNKNOWN) throw new RuntimeException("Unknown RegisterID");
    }


    protected abstract boolean hasPayload();
    protected abstract String getPayload();

    public String toCommandSentence() {
        return String.format("$VN%s%s%s*XX\r\n",
            messageID.toStringCode(),
            registerID != RegisterID.NONE ? String.format("%s%d", DELIMITER, registerID.getValue()) : EMPTY,
            hasPayload() ? String.format("%s%s", DELIMITER, getPayload()) : EMPTY);
    }
}
