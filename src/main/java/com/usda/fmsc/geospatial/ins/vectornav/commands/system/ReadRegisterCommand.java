package com.usda.fmsc.geospatial.ins.vectornav.commands.system;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;

public class ReadRegisterCommand extends VNCommand {
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
