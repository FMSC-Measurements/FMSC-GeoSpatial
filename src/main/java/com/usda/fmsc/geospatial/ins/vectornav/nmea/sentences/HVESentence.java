package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class HVESentence extends VNNmeaSentence {
    private float heave, heaveRate, delayedHeave;

    public HVESentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 5) {
            try {
                heave = Float.parseFloat(tokens[3]);
                heaveRate = Float.parseFloat(tokens[4]);
                delayedHeave = Float.parseFloat(tokens[5]);

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.HVE;
    }


    public float getHeave() {
        return heave;
    }

    public float getHeaveRate() {
        return heaveRate;
    }

    public float getDelayedHeave() {
        return delayedHeave;
    }

}
