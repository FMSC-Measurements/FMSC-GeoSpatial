package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.nmea.sentences.BaseNmeaSentence;

public abstract class VNNmeaSentence extends BaseNmeaSentence {
    private MessageID msgID;
    private boolean valid;

    public VNNmeaSentence(MessageID msgID, String nmea) {
        this.msgID = msgID;
        this.valid = parse(nmea) && validateChecksum(nmea);
    }


    public boolean isValid() {
        return valid;
    }

    public MessageID getMessageID() {
        return msgID;
    }

    public abstract RegisterID getRegisterID();


    @Override
    protected boolean validateChecksum(String nmea) {
        return super.validateChecksum(nmea) || nmea.length() > 10 && nmea.endsWith("XX");
    }

    
}
