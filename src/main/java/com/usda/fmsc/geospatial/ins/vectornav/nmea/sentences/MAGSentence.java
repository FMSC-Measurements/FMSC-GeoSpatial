package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class MAGSentence extends VNNmeaSentence {
    private float magX, magY, magZ;


    public MAGSentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 5) {
            try {
                magX = Float.parseFloat(tokens[3]);
                magY = Float.parseFloat(tokens[4]);
                magZ = Float.parseFloat(tokens[5]);

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.MAG;
    }


    public float getMagX() {
        return magX;
    }

    public float getMagY() {
        return magY;
    }

    public float getMagZ() {
        return magZ;
    }

    
}
