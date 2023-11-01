package com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class YIASentence extends VNNmeaSentence {
    private float yaw, pitch, roll;
    private float inertialAccelX, inertialAccelY, inertialAccelZ;
    private float gyroX, gyroY, gyroZ;


    public YIASentence(MessageID msgID, String nmea) {
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

                inertialAccelX = Float.parseFloat(tokens[6]);
                inertialAccelY = Float.parseFloat(tokens[7]);
                inertialAccelZ = Float.parseFloat(tokens[8]);
                
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
        return RegisterID.YIA;
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


    public float getInertialAccelX() {
        return inertialAccelX;
    }

    public float getInertialAccelY() {
        return inertialAccelY;
    }

    public float getInertialAccelZ() {
        return inertialAccelZ;
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
