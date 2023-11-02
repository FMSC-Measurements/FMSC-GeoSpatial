package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;

public class ReadRegisterCommand extends VNBaseCommand {
    private final String payload;

    public ReadRegisterCommand(RegisterID registerID, String payload) {
        super(MessageID.RRG, registerID);
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

    public static ReadRegisterCommand parse(String data) {
        return new ReadRegisterCommand(RegisterID.parse(data), data.substring(data.indexOf(","), data.lastIndexOf("*")));
    }
}
