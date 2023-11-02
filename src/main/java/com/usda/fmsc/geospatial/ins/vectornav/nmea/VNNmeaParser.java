package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.ins.vectornav.commands.VNBaseCommand;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;
import com.usda.fmsc.geospatial.nmea.BaseNmeaParser;

public class VNNmeaParser extends BaseNmeaParser<VNNmeaSentence, VNNmeaParserListener> {
  
    public VNNmeaParser() {
        super();
    }


    @Override
    protected boolean shouldParse(String data) {
        if (data != null) {
            if (VNNmeaTools.isCommandMessage(data)) {
                VNBaseCommand command = VNNmeaTools.parseCommand(data);
                if (command != null)
                    onCommandReceived(command);
                else
                    onInvalidMessageReceived(data);
                return false;
            }
        }
        return true;
    }

    @Override
    protected VNNmeaSentence parseMessage(String data) {
        return VNNmeaTools.parseSentence(data);
    }

    protected void onCommandReceived(VNBaseCommand command) {
        for (VNNmeaParserListener listener : getListeners()) {
            listener.onCommandReceived(command);
        }
    }
}
