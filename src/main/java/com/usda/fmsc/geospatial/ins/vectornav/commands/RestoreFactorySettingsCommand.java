package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;

public class RestoreFactorySettingsCommand extends VNBaseCommand {
    public RestoreFactorySettingsCommand() {
        super(MessageID.RFS);
    }
}
