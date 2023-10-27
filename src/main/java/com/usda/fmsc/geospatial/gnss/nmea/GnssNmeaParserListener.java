package com.usda.fmsc.geospatial.gnss.nmea;

import com.usda.fmsc.geospatial.nmea.INmeaParserListener;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public abstract class GnssNmeaParserListener implements INmeaParserListener<NmeaSentence, GnssNmeaBurst> {
    @Override
    public void onMessageReceived(NmeaSentence message) {
        //use onNmeaSentence for now
    }

    @Override
    public void onInvalidMessageReceived(String invalidMessageData) {
        // Unneeded
    }
}
