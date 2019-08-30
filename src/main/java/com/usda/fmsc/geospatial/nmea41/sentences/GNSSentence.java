package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.sentences.base.PositionSentence;

public class GNSSentence extends PositionSentence {

    public GNSSentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        return false;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GNS;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }
}
