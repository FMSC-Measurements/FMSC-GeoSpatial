package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;

public class WriteSettingsCommand extends VNBaseCommand {
    public WriteSettingsCommand() {
        super(MessageID.WNV);
    }
}
