package com.usda.fmsc.geospatial.nmea.exceptions;

import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;

public class MissingNmeaDataException extends NmeaException {
    public MissingNmeaDataException() {
        super("Missing Nmea sentence data.");
    }

    public MissingNmeaDataException(String message) {
        super(message);
    }

    public MissingNmeaDataException(SentenceID sentenceID) {
        super(String.format("Missing %s sentence data.", sentenceID.toString()));
    }
}