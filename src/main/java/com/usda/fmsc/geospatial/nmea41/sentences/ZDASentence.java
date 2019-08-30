package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

public class ZDASentence extends NmeaSentence {

    public ZDASentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        return false;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.ZDA;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }
}
