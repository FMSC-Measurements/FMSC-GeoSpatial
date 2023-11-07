package com.usda.fmsc.geospatial.ins.vectornav.commands.system;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.PauseResume;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;
import com.usda.fmsc.geospatial.nmea.NmeaTools;

public class AsyncOutputPauseCommand extends VNCommand {
    private final PauseResume pauseResume; 

    public AsyncOutputPauseCommand(PauseResume pauseResume) {
        super(MessageID.ASY);
        this.pauseResume = pauseResume;
    }

    @Override
    protected String getPayload() {
        return Integer.toString(pauseResume.getValue());
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
