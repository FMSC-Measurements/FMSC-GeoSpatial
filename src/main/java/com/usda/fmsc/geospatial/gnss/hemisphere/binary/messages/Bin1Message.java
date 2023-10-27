package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.NavMode;

/*
 * GNSS position message (position and velocity data)
 */
public class Bin1Message extends BaseBinMessage {
    protected static final int AGE_OF_DIFF_IDX = HEADER_SIZE; // byte
    protected static final int NUM_OF_SATS_IDX = AGE_OF_DIFF_IDX + 1; // byte
    protected static final int GPS_WEEK_IDX = NUM_OF_SATS_IDX + 1; // short
    protected static final int GPS_TIME_OF_WEEK_IDX = GPS_WEEK_IDX + 2; // double
    protected static final int LATITUDE_IDX = GPS_TIME_OF_WEEK_IDX + 8; // double
    protected static final int LONGITUDE_IDX = LATITUDE_IDX + 8; // double
    protected static final int HEIGHT_IDX = LONGITUDE_IDX + 8; // float
    protected static final int VNORTH_IDX = HEIGHT_IDX + 4; // float
    protected static final int VEAST_IDX = VNORTH_IDX + 4; // float
    protected static final int VUP_IDX = VEAST_IDX + 4; // float
    protected static final int STD_DEV_RESID_IDX = VUP_IDX + 4; // float
    protected static final int NAV_MODE_IDX = STD_DEV_RESID_IDX + 4; // short
    protected static final int EXT_AGE_OF_DIFF = NAV_MODE_IDX + 2; // short

    public Bin1Message(byte[] message) {
        super(message);
    }

    /**
     * @return MessageType
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.Bin1;
    }

    /**
     * Age of differential, seconds. Use Extended AgeOfDiff first. If both = 0, then
     * no differential
     * 
     * @return byte
     */
    public byte getAgeOfDiff() {
        return _message.get(AGE_OF_DIFF_IDX);
    }

    /**
     * Number of satellites used in the GPS solution
     * 
     * @return byte
     */
    public byte getNumOfSats() {
        return _message.get(NUM_OF_SATS_IDX);
    }

    /**
     * GPS week associated with this message
     * 
     * @return short
     */
    public short getGPSWeek() {
        return _message.getShort(GPS_WEEK_IDX);
    }

    /**
     * GPS Time of Week (sec) associated with this message
     * 
     * @return double
     */
    public double getGPSTimeOfWeek() {
        return _message.getDouble(GPS_TIME_OF_WEEK_IDX);
    }

    /**
     * Latitude in degrees
     * 
     * @return double
     */
    public double getLatitude() {

        return _message.getDouble(LATITUDE_IDX);
    }

    /**
     * Longitude in degrees
     * 
     * @return double
     */
    public double getLongitude() {
        return _message.getDouble(LONGITUDE_IDX);
    }

    /**
     * Altitude above the ellipsoid in meters
     * 
     * @return float
     */
    public float getHeight() {
        return _message.getFloat(HEIGHT_IDX);
    }

    /**
     * Velocity north in m/s
     * 
     * @return float
     */
    public float getVNorth() {
        return _message.getFloat(VNORTH_IDX);
    }

    /**
     * Velocity east in m/s
     * 
     * @return float
     */
    public float getVEast() {
        return _message.getFloat(VEAST_IDX);
    }

    /**
     * Velocity up in m/s
     * 
     * @return float
     */
    public float getVUp() {
        return _message.getFloat(VUP_IDX);
    }

    /**
     * Standard deviation of residuals in meters
     * 
     * @return float
     */
    public float getStdDevResid() {
        return _message.getFloat(STD_DEV_RESID_IDX);
    }

    /**
     * Navigation Mode
     * 
     * @return NavMode
     */
    public NavMode getNavMode() {
        return NavMode.parse(_message.getShort(NAV_MODE_IDX) & 0x7F);
    }

    /**
     * Is Manual Position
     * 
     * @return boolean
     */
    public boolean isManualPosition() {
        return (_message.getShort(NAV_MODE_IDX) & (1 << 7)) != 0;
    }

    /**
     * Extended age of differential, seconds. If 0, use 1 byte AgeOfDiff
     * 
     * @return short
     */
    public short getExtAgeOfDiff() {
        return _message.getShort(EXT_AGE_OF_DIFF);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return String.format(
                "Bin1 [%f, %f, %f] NavMode: %s | AoD: %d | NoS: %d | Vel(NEU): %f,%f,%f | StdDevResid: %f\n",
                getLatitude(), getLongitude(), getHeight(),
                getNavMode().toString(),
                getExtAgeOfDiff() == 0 ? getAgeOfDiff() : getExtAgeOfDiff(), getNumOfSats(),
                getVNorth(), getVEast(), getVUp(),
                getStdDevResid());
    }

}
