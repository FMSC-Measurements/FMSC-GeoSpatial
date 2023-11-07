package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;
import com.usda.fmsc.geospatial.nmea.INmeaParserListener;

public interface VNNmeaParserListener extends INmeaParserListener<VNNmeaSentence> {
    void onCommandResponseReceived(VNCommand command);
}