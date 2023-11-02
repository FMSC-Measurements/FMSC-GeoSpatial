package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.nmea.NmeaTools;

public class BinaryOutputPollCommand extends VNBaseCommand {
    private final int outputRegister; 

    public BinaryOutputPollCommand(int outputRegister) {
        super(MessageID.ASY);
        this.outputRegister = outputRegister;
    }

    @Override
    protected String getPayload() {
        return Integer.toString(outputRegister);
    }

    public static BinaryOutputPollCommand parse(String data) {
        String[] tokens = NmeaTools.tokenize(data);
        if (tokens.length > 1) {
            return new BinaryOutputPollCommand(Integer.parseInt(tokens[1]));
        } else {
            throw new RuntimeException("Invalid number of Output Register parameters");
        }
    }
}
