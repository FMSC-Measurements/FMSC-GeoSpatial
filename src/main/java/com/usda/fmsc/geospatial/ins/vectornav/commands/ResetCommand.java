package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;

public class ResetCommand extends VNBaseCommand {
    public ResetCommand() {
        super(MessageID.RST);
    }
}
