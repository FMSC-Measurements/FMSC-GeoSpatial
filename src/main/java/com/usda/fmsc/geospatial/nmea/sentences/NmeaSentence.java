package com.usda.fmsc.geospatial.nmea.sentences;

import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;

public abstract class NmeaSentence extends BaseNmeaSentence {
    private TalkerID talkerID;
    private boolean valid;


    public NmeaSentence(TalkerID talkerID, String nmea) {
        this.talkerID = talkerID;
        this.valid = parse(nmea) && validateChecksum(nmea);
    }


    public boolean isValid() {
        return valid;
    }

    public TalkerID getTalkerID() {
        return talkerID;
    }


    public abstract SentenceID getSentenceID();

    public abstract boolean isMultiSentence();

}
