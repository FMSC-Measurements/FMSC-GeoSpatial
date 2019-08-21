package com.usda.fmsc.geospatial.nmea41.exceptions;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;

public class MissingNmeaDataException extends NmeaException {
    public MissingNmeaDataException(SentenceID sentenceID) {
        super(String.format("Missing %s sentence data.", sentenceID.toString()), null);
    }

    public MissingNmeaDataException(SentenceID ... sentenceIDs) {
        super(packMessage(sentenceIDs), null);
    }

    private static String packMessage(SentenceID[] sentenceIDs) {
        StringBuilder sb = new StringBuilder();
        sb.append("Missing sentences: ");

        for (SentenceID sid : sentenceIDs) {
            sb.append(sid.toString());
            sb.append(" ");
        }

        return sb.toString();
    }
}