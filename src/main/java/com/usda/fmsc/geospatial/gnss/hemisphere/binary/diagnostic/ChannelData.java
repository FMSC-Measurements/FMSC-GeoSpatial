package com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic;

public class ChannelData {
    public static final int STRUCT_SIZE = 24;

    private short diffCorr, doppHz, NCOHz, posResid, velResid;
    private byte chElev, status;
    private int channelNum, sv, lastSubFrame, ephmVFlag, ephmHealth, almVFlag, almHealth, URA, azimuth, cliForSNR;

    public ChannelData(byte channelNum, byte sv, byte status, byte lastSubFrame,
        byte ephmVFlag, byte ephmHealth, byte almVFlag, byte almHealth, byte chElev, byte azimuth,
        byte URA, short dum, short cliForSNR, short diffCorr, short posResid, short velResid,
        short doppHz, short NCOHz) {

        this.channelNum = channelNum & 0xff;
        this.sv = sv & 0xff;
        this.status = status;
        this.lastSubFrame = lastSubFrame & 0xff;
        this.ephmVFlag = ephmVFlag & 0xff;
        this.ephmHealth = ephmHealth & 0xff;
        this.almVFlag = almVFlag & 0xff;
        this.almHealth = almHealth & 0xff;
        this.chElev = chElev;
        this.azimuth = (azimuth & 0xff) * 2;
        this.URA = URA & 0xff;
        this.cliForSNR = (cliForSNR & 0xffff) / 32;
        this.diffCorr = diffCorr;
        this.posResid = posResid;
        this.velResid = velResid;
        this.doppHz = doppHz;
        this.NCOHz = NCOHz;
    }

    /*
     * Channel Number
     */
    public int getChannelNumber() {
        return this.channelNum;
    }

    /*
     * Space Vehicle Slot
     */
    public int getSvSlot() {
        return sv;
    }

    /*
     * Is Space Vehicle Tracked
     */
    public boolean isSvTracked() {
        return sv != 0;
    }

    /*
     * Status Bits (code carrier bit frame...)
     */
    public byte getStatus() {
        return this.status;
    }

    /*
     * Last subframe processed
     */
    public int getLastSubframe() {
        return this.lastSubFrame;
    }

    /* 
     * Ephemeris valid flag 
     */
    public boolean isEphemerisValid() {
        return this.ephmVFlag != 0;
    }

    /* 
     * Ephemeris health 
     */
    public int getEphemerisHealth() {
        return this.ephmHealth;
    }

    /* 
     * Almanac valid flag 
     */
    public boolean isAlmanacValid() {
        return this.almVFlag != 0;
    }

    /* 
     * Almanac health 
     */
    public int getAlmanacHealth() {
        return this.almHealth;
    }

    /*
     * Channel Elevation angle
     */
    public byte getElevAngle() {
        return chElev;
    }

    /*
     * Azimuth Angle
     */
    public int getAzimuth() {
        return azimuth;
    }

    /*
     * User Range Error
     */
    public int getURA() {
        return this.URA;
    }

    /*
     * Code lock indicator for SNR
     */
    public int getCliForSNR() {
        return cliForSNR;
    }

    /*
     * Differential correction
     */
    public int getDiffCorr() {
        return diffCorr * 100;
    }

    /*
     * Position Residual
     */
    public int getPosResid() {
        return posResid * 10;
    }

    /*
     * Velocity Residual
     */
    public int getVelResid() {
        return velResid * 10;
    }

    /*
     * Doppler in HZ at B1 frequency
     */
    public short getDoppHz() {
        return doppHz;
    }

    /*
     * Track from NCO in HZ
     */
    public short getNCOHz() {
        return NCOHz;
    }


    @Override
    public String toString() {
        String eph = isEphemerisValid() ? String.format("Valid:%d", getEphemerisHealth()) : "Invalid";
        String alm = isAlmanacValid() ? String.format("Valid:%d", getAlmanacHealth()) : "Invalid";
        
        return String.format(
                "Ch: %d | SV: %d | Eph: (%s) | Alm: (%s) | ElevAngle: %d | Az: %d | DiffCor: %d | URA: %d | DopHz: %d | NCOHz: %d | PosResid: %d | VelResid: %d",
                getChannelNumber(), getSvSlot(), eph, alm,
                getElevAngle(), getAzimuth(),
                getDiffCorr(), getURA(),
                getDoppHz(), getNCOHz(),
                getPosResid(), getVelResid());
    }

}