package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class QMRSentence extends VNNmeaSentence {
    private float quat0, quat1, quat2, quat3;
    private float magX, magY, magZ;
    private float accelX, accelY, accelZ;
    private float gyroX, gyroY, gyroZ;


    public QMRSentence(MessageID msgID, String nmea) {
        super(msgID, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 15) {
            try {
                quat0 = Float.parseFloat(tokens[3]);
                quat1 = Float.parseFloat(tokens[4]);
                quat2 = Float.parseFloat(tokens[5]);
                quat3 = Float.parseFloat(tokens[6]);

                magX = Float.parseFloat(tokens[7]);
                magY = Float.parseFloat(tokens[8]);
                magZ = Float.parseFloat(tokens[9]);
                
                accelX = Float.parseFloat(tokens[10]);
                accelY = Float.parseFloat(tokens[11]);
                accelZ = Float.parseFloat(tokens[12]);
                
                gyroX = Float.parseFloat(tokens[13]);
                gyroY = Float.parseFloat(tokens[14]);
                gyroZ = Float.parseFloat(tokens[15]);

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public RegisterID getRegisterID() {
        return RegisterID.QMR;
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

