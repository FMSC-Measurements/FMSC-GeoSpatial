package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class QTNSentence extends VNNmeaSentence {
    private float quat0, quat1, quat2, quat3;


    public QTNSentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 6) {
            try {
                quat0 = Float.parseFloat(tokens[3]);
                quat1 = Float.parseFloat(tokens[4]);
                quat2 = Float.parseFloat(tokens[5]);
                quat3 = Float.parseFloat(tokens[6]);
                
                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.QTN;
    }


    public float getQuat0() {
        return quat0;
    }

    public float getQuat1() {
        return quat1;
    }

    public float getQuat2() {
        return quat2;
    }

    public float getQuat3() {
        return quat3;
    }

}
