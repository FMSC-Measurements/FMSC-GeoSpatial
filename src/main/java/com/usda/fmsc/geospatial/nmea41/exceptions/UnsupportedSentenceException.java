package com.usda.fmsc.geospatial.nmea41.exceptions;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;

public class UnsupportedSentenceException extends NmeaException {
    private SentenceID sentenceID;

    public UnsupportedSentenceException(SentenceID sentenceID, String sentence) {
        super(sentenceID == SentenceID.Unknown ? "This SentenceID is unsupported." : String.format("The SentenceID %s is unsupported.", sentenceID.toString()), sentence);
    }

    public SentenceID getSentenceID() {
        return sentenceID;
    }
}
