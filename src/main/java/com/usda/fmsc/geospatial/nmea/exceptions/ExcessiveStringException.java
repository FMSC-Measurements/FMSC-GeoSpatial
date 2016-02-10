package com.usda.fmsc.geospatial.nmea.exceptions;

import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;

public class ExcessiveStringException extends NmeaException {
    public ExcessiveStringException(SentenceID sentenceID) {
        super(String.format("An Excessive amount of %s type strings were added to the burst.", sentenceID));
    }
}