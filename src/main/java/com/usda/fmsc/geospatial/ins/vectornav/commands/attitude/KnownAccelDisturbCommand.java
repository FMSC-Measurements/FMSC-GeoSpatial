package com.usda.fmsc.geospatial.ins.vectornav.commands.attitude;

import com.usda.fmsc.geospatial.ins.vectornav.codes.Disturbance;
import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;
import com.usda.fmsc.geospatial.nmea.NmeaTools;

public class KnownAccelDisturbCommand extends VNCommand {
    private final Disturbance disturbance; 

    public KnownAccelDisturbCommand(Disturbance disturbance) {
        super(MessageID.KMD);
        this.disturbance = disturbance;
    }

    @Override
    protected String getPayload() {
        return Integer.toString(disturbance.getValue());
    }

    @Override
    protected boolean hasPayload() {
        return true;
    }

    public static KnownAccelDisturbCommand parse(String data) {
        String[] tokens = NmeaTools.tokenize(data);
        if (tokens.length > 1) {
            return new KnownAccelDisturbCommand(Disturbance.parse(tokens[1]));
        } else {
            throw new RuntimeException("Invalid number of Disturbance parameters");
        }
    }
}
