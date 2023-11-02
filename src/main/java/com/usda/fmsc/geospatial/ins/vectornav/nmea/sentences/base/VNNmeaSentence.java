package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.VNNmeaTools;
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
        return VNNmeaTools.validateChecksum(nmea);
    }

    
}
