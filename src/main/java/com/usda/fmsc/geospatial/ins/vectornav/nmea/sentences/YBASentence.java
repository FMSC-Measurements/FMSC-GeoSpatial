package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class YBASentence extends VNNmeaSentence {
    private float yaw, pitch, roll;
    private float bodyAccelX, bodyAccelY, bodyAccelZ;
    private float gyroX, gyroY, gyroZ;


    public YBASentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 11) {
            try {
                yaw = Float.parseFloat(tokens[3]);
                pitch = Float.parseFloat(tokens[4]);
                roll = Float.parseFloat(tokens[5]);

                bodyAccelX = Float.parseFloat(tokens[6]);
                bodyAccelY = Float.parseFloat(tokens[7]);
                bodyAccelZ = Float.parseFloat(tokens[8]);
                
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
        return RegisterID.YBA;
    }


    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }


    public float getBodyAccelX() {
        return bodyAccelX;
    }

    public float getBodyAccelY() {
        return bodyAccelY;
    }

    public float getBodyAccelZ() {
        return bodyAccelZ;
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
