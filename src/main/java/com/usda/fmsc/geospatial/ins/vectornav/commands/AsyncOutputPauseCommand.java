package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.PauseResume;
import com.usda.fmsc.geospatial.nmea.NmeaTools;

public class AsyncOutputPauseCommand extends VNBaseCommand {
    private final PauseResume pauseResume; 

    public AsyncOutputPauseCommand(PauseResume pauseResume) {
        super(MessageID.ASY);
        this.pauseResume = pauseResume;
    }

    @Override
    protected String getPayload() {
        return pauseResume == PauseResume.Resume ? "1" : "0";
    }

    @Override
    protected boolean hasPayload() {
        return true;
    }

    public static AsyncOutputPauseCommand parse(String data) {
        String[] tokens = NmeaTools.tokenize(data);
        if (tokens.length > 1) {
            return new AsyncOutputPauseCommand(PauseResume.parse(tokens[1]));
        } else {
            throw new RuntimeException("Invalid number of Pause/Resume parameters");
        }
    }
}
