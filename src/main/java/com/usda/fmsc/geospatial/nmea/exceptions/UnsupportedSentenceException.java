package com.usda.fmsc.geospatial.nmea.exceptions;

import com.usda.fmsc.geospatial.nmea.codes.SentenceID;

public class UnsupportedSentenceException extends NmeaException {
    private SentenceID sentenceID;

    public UnsupportedSentenceException(String sentenceID, String sentence) {
        super(String.format("The SentenceID %s is unsupported.", sentenceID), sentence);
    }

    public SentenceID getSentenceID() {
        return sentenceID;
    }
}
