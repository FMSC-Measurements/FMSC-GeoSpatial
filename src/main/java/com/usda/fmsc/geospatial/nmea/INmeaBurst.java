package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.nmea.exceptions.InvalidChecksumException;
import com.usda.fmsc.geospatial.nmea.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea.sentences.INmeaSentence;

public interface INmeaBurst<NmeaSentence extends INmeaSentence> {
    public NmeaSentence parseNmea(String nmea) throws UnsupportedSentenceException, InvalidChecksumException;
    public NmeaSentence addNmeaSentence(String nmea) throws UnsupportedSentenceException, InvalidChecksumException;
}
