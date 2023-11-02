package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class DTVSentence extends VNNmeaSentence {
    private float deltaTime;
    private float deltaThetaX, deltaThetaY, deltaThetaZ;
    private float deltaVelocityX, deltaVelocityY, deltaVelocityZ;


    public DTVSentence(MessageID msgID, String nmea) {
        super(MessageID.DTV, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 9) {
            try {
                deltaTime = Float.parseFloat(tokens[3]);

                deltaThetaX = Float.parseFloat(tokens[4]);
                deltaThetaY = Float.parseFloat(tokens[5]);
                deltaThetaZ = Float.parseFloat(tokens[6]);
                
                deltaVelocityX = Float.parseFloat(tokens[7]);
                deltaVelocityY = Float.parseFloat(tokens[8]);
                deltaVelocityZ = Float.parseFloat(tokens[9]);
                
                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }


    @Override
    public RegisterID getRegisterID() {
        return RegisterID.DTV;
    }


    public float getDeltaTime() {
        return deltaTime;
    }


    public float getDeltaThetaX() {
        return deltaThetaX;
    }

    public float getDeltaThetaY() {
        return deltaThetaY;
    }

    public float getDeltaThetaZ() {
        return deltaThetaZ;
    }


    public float getDeltaVelocityX() {
        return deltaVelocityX;
    }

    public float getDeltaVelocityY() {
        return deltaVelocityY;
    }

    public float getDeltaVelocityZ() {
        return deltaVelocityZ;
    }
}
