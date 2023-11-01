package com.usda.fmsc.geospatial.gnss.nmea;

import com.usda.fmsc.geospatial.nmea.INmeaBurstParserListener;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public abstract class GnssNmeaParserListener implements INmeaBurstParserListener<NmeaSentence, GnssNmeaBurst> {
    public abstract void onNmeaReceived(NmeaSentence sentence);
    public abstract void onNmeaBurstReceived(GnssNmeaBurst bust);

    @Override
    public final void onMessageReceived(NmeaSentence message) {
        onNmeaReceived(message);
    }

    @Override
    public final void onBurstReceived(GnssNmeaBurst burst) {
        onNmeaBurstReceived(burst);
    }

    @Override
    public void onInvalidMessageReceived(String invalidMessageData) {
        // Unneeded
    }
}
