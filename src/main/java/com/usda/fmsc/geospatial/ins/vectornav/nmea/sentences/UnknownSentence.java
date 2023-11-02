package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class UnknownSentence extends VNNmeaSentence {
    private String payload;

    public UnknownSentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.UNKNOWN;
    }

    @Override
    protected boolean parse(String nmea) {
        try {
            int aIdx = nmea.indexOf("*");
            aIdx = aIdx > 0 ? aIdx : nmea.length();
            payload = nmea.substring(getMessageID().toStringCode().length() + 2, aIdx);
        } catch (Exception e) {
            payload = nmea;
        }
        
        return true;
    }
    
    public String getPayload() {
        return payload;
    }
}
