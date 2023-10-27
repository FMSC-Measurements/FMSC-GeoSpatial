package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class YMRSentence extends VNNmeaSentence {
    private float yaw, pitch, roll;
    private float magX, magY, magZ;
    private float accelX, accelY, accelZ;
    private float gyroX, gyroY, gyroZ;


    public YMRSentence(String nmea) {
        super(MessageID.RRG, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 14) {
            try {
                yaw = Float.parseFloat(tokens[3]);
                pitch = Float.parseFloat(tokens[4]);
                roll = Float.parseFloat(tokens[5]);

                magX = Float.parseFloat(tokens[6]);
                magY = Float.parseFloat(tokens[7]);
                magZ = Float.parseFloat(tokens[8]);
                
                accelX = Float.parseFloat(tokens[9]);
                accelY = Float.parseFloat(tokens[10]);
                accelZ = Float.parseFloat(tokens[11]);
                
                gyroX = Float.parseFloat(tokens[12]);
                gyroY = Float.parseFloat(tokens[13]);
                gyroZ = Float.parseFloat(tokens[14]);

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.YMR;
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
