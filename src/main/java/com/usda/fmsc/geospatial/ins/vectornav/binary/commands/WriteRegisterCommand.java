package com.usda.fmsc.geospatial.ins.vectornav.binary.commands;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;

public abstract class WriteRegisterCommand extends BaseCommand {

    public WriteRegisterCommand(RegisterID registerID) {
        super(MessageID.WRG, registerID);
    }

    @Override
    protected boolean hasPayload() {
        return true;
    }
}
