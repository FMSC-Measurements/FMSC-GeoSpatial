package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;
import com.usda.fmsc.geospatial.nmea.INmeaBurst;
import com.usda.fmsc.geospatial.nmea.exceptions.InvalidChecksumException;
import com.usda.fmsc.geospatial.nmea.exceptions.UnsupportedSentenceException;

public class VNNmeaBurst implements INmeaBurst<VNNmeaSentence> {

    @Override
    public VNNmeaSentence parseNmea(String nmea) throws UnsupportedSentenceException, InvalidChecksumException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'parseNmea'");
    }

    @Override
    public VNNmeaSentence addNmeaSentence(String nmea) throws UnsupportedSentenceException, InvalidChecksumException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addNmeaSentence'");
    }
    
}
