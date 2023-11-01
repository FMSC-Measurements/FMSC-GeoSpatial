package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.base.listeners.IStringMsgListener;
import com.usda.fmsc.geospatial.nmea.sentences.INmeaSentence;

public interface INmeaParserListener<NmeaSentence extends INmeaSentence> extends IStringMsgListener<NmeaSentence> {
    public void onNmeaReceived(NmeaSentence sentence);
}
