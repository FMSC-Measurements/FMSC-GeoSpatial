package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.NavMode;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.AltStatus;

/*
 * Lat/Lon/Hgt, Covariances, RMS, DOPs and COG, Speed, Heading
 */
public class Bin3Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // double
    protected static final int GPS_WEEK_IDX = GPS_TIME_OF_WEEK_IDX + 8; // ushort
    protected static final int NUM_SATS_TRACKED_IDX = GPS_WEEK_IDX + 2; // ushort
    protected static final int NUM_SATS_USED_IDX = NUM_SATS_TRACKED_IDX + 2; // ushort
    protected static final int NAV_MODE_IDX = NUM_SATS_USED_IDX + 2; // byte
    protected static final int SPARE_IDX = NAV_MODE_IDX + 1; // byte
    protected static final int LATITUDE_IDX = SPARE_IDX + 1; // double
    protected static final int LONGITUDE_IDX = LATITUDE_IDX + 8; // double
    protected static final int HEIGHT_IDX = LONGITUDE_IDX + 8; // float
    protected static final int HORZ_SPEED_IDX = HEIGHT_IDX + 4; // float
    protected static final int VUP_IDX = HORZ_SPEED_IDX + 4; // float
    protected static final int COG_IDX = VUP_IDX + 4; // float
    protected static final int HEADING_IDX = COG_IDX + 4; // float
    protected static final int PITCH_IDX = HEADING_IDX + 4; // float
    protected static final int ROLL_IDX = PITCH_IDX + 4; // float
    protected static final int AGE_OF_DIFF_IDX = ROLL_IDX + 4; // ushort
    protected static final int ALT_STATUS_IDX = AGE_OF_DIFF_IDX + 2; // ushort
    protected static final int STD_DEV_HEADING_IDX = ALT_STATUS_IDX + 2; // float
    protected static final int STD_DEV_PITCH_IDX = STD_DEV_HEADING_IDX + 4; // float
    protected static final int HRMS_IDX = STD_DEV_PITCH_IDX + 4; // float
    protected static final int VRMS_IDX = HRMS_IDX + 4; // float
    protected static final int HDOP_IDX = VRMS_IDX + 4; // float
    protected static final int VDOP_IDX = HDOP_IDX + 4; // float
    protected static final int TDOP_IDX = VDOP_IDX + 4; // float
    protected static final int COV_NN_IDX = TDOP_IDX + 4; // float
    protected static final int COV_NE_IDX = COV_NN_IDX + 4; // float
    protected static final int COV_NU_IDX = COV_NE_IDX + 4; // float
    protected static final int COV_EE_IDX = COV_NU_IDX + 4; // float
    protected static final int COV_EU_IDX = COV_EE_IDX + 4; // float
    protected static final int COV_UU_IDX = COV_EU_IDX + 4; // float

    public Bin3Message(byte[] message) {
        super(message);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.Bin3;
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
     * GPS week associated with this message
     * 
     * @return short
     */
    public short getGPSWeek() {
        return _message.getShort(GPS_WEEK_IDX);
    }

    /*
     * Number of satellites tracked in the GPS solution
     */
    public int getNumOfSatsTracked() {
        return _message.getShort(NUM_SATS_TRACKED_IDX) & 0xFF;
    }

    /*
     * Number of satellites used in the GPS solution
     */
    public int getNumOfSatsUsed() {
        return _message.getShort(NUM_SATS_USED_IDX) & 0xFF;
    }

    /**
     * Navigation Mode
     * 
     * @return NavMode
     */
    public NavMode getNavMode() {
        return NavMode.parse(_message.get(NAV_MODE_IDX) & 0xF);
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
     * Velocity horizontal in m/s
     * 
     * @return float
     */
    public float getHorzSpeed() {
        return _message.getFloat(HORZ_SPEED_IDX);
    }

    /**
     * Velocity up in m/s
     * 
     * @return float
     */
    public float getVelocityUp() {
        return _message.getFloat(VUP_IDX);
    }

    /**
     * Course over Ground, degrees
     * 
     * @return float
     */
    public float getCOG() {
        return _message.getFloat(COG_IDX);
    }

    /**
     * Heading (degrees), Zero unless vector
     * 
     * @return float
     */
    public float getHeading() {
        return _message.getFloat(HEADING_IDX);
    }

    /**
     * Pitch (degrees), Zero unless vector
     * 
     * @return float
     */
    public float getPitch() {
        return _message.getFloat(PITCH_IDX);
    }

    /**
     * Roll (degrees), Zero unless vector
     * 
     * @return float
     */
    public float getRoll() {
        return _message.getFloat(ROLL_IDX);
    }

    /**
     * Age of differential, seconds. Use Extended AgeOfDiff first. If both = 0, then
     * no differential
     * 
     * @return int
     */
    public int getAgeOfDiff() {
        return _message.getShort(AGE_OF_DIFF_IDX) & 0xFFFF;
    }

    /**
     * Attitude Status, zero unless vector
     * 
     * @return Alt Status
     */
    public AltStatus getAltStatus() {
        return new AltStatus(_message.getShort(ALT_STATUS_IDX));
    }

    /**
     * Yaw stddev (degrees), zero unless vector
     * 
     * @return float
     */
    public float getStdDevHeading() {
        return _message.getFloat(STD_DEV_HEADING_IDX);
    }

    /**
     * Pitch stddev (degrees), zero unless vector
     * 
     * @return float
     */
    public float getStdDevPitch() {
        return _message.getFloat(STD_DEV_PITCH_IDX);
    }

    /**
     * Horizontal RMS
     * 
     * @return float
     */
    public float getHRMS() {
        return _message.getFloat(HRMS_IDX);
    }

    /**
     * Vertical RMS
     * 
     * @return float
     */
    public float getVRMS() {
        return _message.getFloat(VRMS_IDX);
    }

    /**
     * Horizontal DOP
     * 
     * @return float
     */
    public float getHDOP() {
        return _message.getFloat(HDOP_IDX);
    }

    /**
     * Vertical DOP
     *
     * @return float
     */
    public float getVDOP() {
        return _message.getFloat(VDOP_IDX);
    }

    /**
     * Time DOP
     * 
     * @return float
     */
    public float getTDOP() {
        return _message.getFloat(TDOP_IDX);
    }

    /**
     * Covaraince North-North
     * 
     * @return float
     */
    public float getCovNN() {
        return _message.getFloat(COV_NN_IDX);
    }

    /**
     * Covaraince North-East
     * 
     * @return float
     */
    public float getCovNE() {
        return _message.getFloat(COV_NE_IDX);
    }

    /**
     * Covaraince North-Up
     * 
     * @return float
     */
    public float getCovNU() {
        return _message.getFloat(COV_NU_IDX);
    }

    /**
     * Covaraince East-East
     * 
     * @return float
     */
    public float getCovEE() {
        return _message.getFloat(COV_EE_IDX);
    }

    /**
     * Covaraince East-Up
     * 
     * @return float
     */
    public float getCovEU() {
        return _message.getFloat(COV_EU_IDX);
    }

    /**
     * Covaraince Up-Up
     * 
     * @return float
     */
    public float getCovUU() {
        return _message.getFloat(COV_UU_IDX);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Bin3 [Loc/Cov/RMS/DOP/COG/Spd/Head]\n");

        sb.append(String.format("  Sats Tracked/Used: %d/%d\n", getNumOfSatsTracked(), getNumOfSatsUsed()));
        sb.append(String.format("  Nav Mode:          %s\n", getNavMode()));
        sb.append(String.format("  Latitude:          %.8f\n", getLatitude()));
        sb.append(String.format("  Longitude:         %.8f\n", getLongitude()));
        sb.append(String.format("  Height             %.6fm\n", getHeight()));
        sb.append(String.format("  Horz Speed:        %.4fm/s\n", getHorzSpeed()));
        sb.append(String.format("  CoG:               %.2f°\n", getCOG()));
        sb.append(String.format("  Heading:           %.2f°\n", getHeading()));
        sb.append(String.format("  Pitch:             %.2f°\n", getPitch()));
        sb.append(String.format("  Roll:              %.2f°\n", getRoll()));
        sb.append(String.format("  Age of Diff:       %ds\n", getAgeOfDiff()));

        AltStatus altStatus = getAltStatus();
        sb.append(String.format("  Alt Status:        %s\n",
                altStatus.isValid()
                        ? String.format("Yaw: %.2f° | Pitch: %.2f° | Roll: %.2f° | Type:", altStatus.getYaw(),
                                altStatus.getPitch(), altStatus.getRoll(), altStatus.getType())
                        : "Invalid"));

        sb.append(String.format("  StdDevHeading:     %.2f°\n", getStdDevHeading()));
        sb.append(String.format("  StdDevPitch:       %.2f°\n", getStdDevPitch()));
        sb.append(String.format("  HRMS/VRMS:         %.2f / %.2f\n", getHRMS(), getVRMS()));
        sb.append(String.format("  HDOP/VDOP/TDOP:    %.2f / %.2f / %.2f\n", getHDOP(), getVDOP(), getTDOP()));
        sb.append(String.format("  CovNN:             %.2f\n", getCovNN()));
        sb.append(String.format("  CovNE:             %.2f\n", getCovNE()));
        sb.append(String.format("  CovNU:             %.2f\n", getCovNU()));
        sb.append(String.format("  CovEE:             %.2f\n", getCovEE()));
        sb.append(String.format("  CovEU:             %.2f\n", getCovEU()));
        sb.append(String.format("  CovUU:             %.2f\n", getCovUU()));

        return sb.toString();
    }

}
