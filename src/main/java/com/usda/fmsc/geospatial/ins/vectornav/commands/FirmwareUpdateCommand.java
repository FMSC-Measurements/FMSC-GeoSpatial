package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;

public class FirmwareUpdateCommand extends VNBaseCommand {
    public FirmwareUpdateCommand() {
        super(MessageID.FWU);
    }
}
