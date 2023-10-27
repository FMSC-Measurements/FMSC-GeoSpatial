package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class MARSentence extends VNNmeaSentence {
    private float magX, magY, magZ;
    private float accelX, accelY, accelZ;
    private float gyroX, gyroY, gyroZ;


    public MARSentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 11) {
            try {
                magX = Float.parseFloat(tokens[3]);
                magY = Float.parseFloat(tokens[4]);
                magZ = Float.parseFloat(tokens[5]);
                
                accelX = Float.parseFloat(tokens[6]);
                accelY = Float.parseFloat(tokens[7]);
                accelZ = Float.parseFloat(tokens[8]);
                
                gyroX = Float.parseFloat(tokens[9]);
                gyroY = Float.parseFloat(tokens[10]);
                gyroZ = Float.parseFloat(tokens[11]);

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.MAR;
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


    public float getAccelX() {
        return accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public float getAccelZ() {
        return accelZ;
    }


    public float getGyroX() {
        return gyroX;
    }

    public float getGyroY() {
        return gyroY;
    }

    public float getGyroZ() {
        return gyroZ;
    }

}
