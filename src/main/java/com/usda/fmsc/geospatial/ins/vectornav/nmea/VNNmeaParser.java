package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;
import com.usda.fmsc.geospatial.nmea.BaseNmeaParser;

public class VNNmeaParser extends BaseNmeaParser<VNNmeaSentence, VNNmeaParserListener> {
  
    public VNNmeaParser() {
        super();
    }


    @Override
    protected boolean shouldParse(String data, Object args) {
        if (data != null) {
            if (VNNmeaTools.isCommandMessage(data)) {
                VNCommand command = VNNmeaTools.parseCommand(data);
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
    protected VNNmeaSentence parseMessage(String data, Object args) {
        return VNNmeaTools.parseSentence(data);
    }

    protected void onCommandReceived(VNCommand command) {
        for (VNNmeaParserListener listener : getListeners()) {
            listener.onCommandResponseReceived(command);
        }
    }
}
