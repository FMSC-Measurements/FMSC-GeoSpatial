package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

public class GSTSentence extends NmeaSentence {

    public GSTSentence(String nmea) {
        super(nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        return false;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GST;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }
}
