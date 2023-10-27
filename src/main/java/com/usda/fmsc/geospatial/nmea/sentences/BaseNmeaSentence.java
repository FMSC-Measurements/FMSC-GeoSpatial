package com.usda.fmsc.geospatial.nmea.sentences;

import com.usda.fmsc.geospatial.nmea.NmeaTools;

public abstract class BaseNmeaSentence implements INmeaSentence {
    
    protected abstract boolean parse(String nmea);

    public abstract boolean isValid();


    protected String[] tokenize(String nmea) {
        return NmeaTools.tokenize(nmea);
    }

    protected boolean validateChecksum(String nmea) {
        return NmeaTools.validateChecksum(nmea);
    }
}
