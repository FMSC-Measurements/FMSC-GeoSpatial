package com.usda.fmsc.geospatial.ins.vectornav.commands.system;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;

public class ResetCommand extends VNCommand {
    public ResetCommand() {
        super(MessageID.RST);
    }
}
