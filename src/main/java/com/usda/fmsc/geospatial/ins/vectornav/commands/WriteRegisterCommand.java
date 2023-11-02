package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;

public class WriteRegisterCommand extends VNBaseCommand {
    private final String payload;

    protected WriteRegisterCommand(RegisterID registerID) {
        this(registerID, "");
    }

    public WriteRegisterCommand(RegisterID registerID, String payload) {
        super(MessageID.WRG, registerID);
        this.payload = payload;
    }

    @Override
    protected boolean hasPayload() {
        return true;
    }

    @Override
    protected String getPayload() {
        return payload;
    }

    public static WriteRegisterCommand parse(String data) {
        return new WriteRegisterCommand(RegisterID.parse(data), data.substring(data.indexOf(","), data.lastIndexOf("*")));
    }
}
