package com.usda.fmsc.geospatial.ins.vectornav.commands.attitude;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;

public class SetGyroBiasCommand extends VNCommand {
    public SetGyroBiasCommand() {
        super(MessageID.SGB);
    }
}
