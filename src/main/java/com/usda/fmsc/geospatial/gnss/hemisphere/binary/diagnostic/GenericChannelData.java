package com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic;

import java.util.EnumSet;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Almanac;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.ChannelFlags;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Ephemeris;

public class GenericChannelData {
    public static final int STRUCT_SIZE = 20;

    private short azimuth, lastMessage, slip, diffCorr, doppHz, NCOHz, posResid;
    private byte sv, almEphmFlags, status, chElev, cFlags;
    private int cliForSNR, allocType;

    public GenericChannelData(byte sv, byte almEphmFlags, byte status, byte chElev,
            byte azimuth, byte lastMessage, byte slip, byte cFlags, int cliForSNR, short diffCorr,
            short doppHz, short NCOHz, short posResid, short allocType) {

        this.sv = sv;
        this.almEphmFlags = almEphmFlags;
        this.status = status;
        this.chElev = chElev;
        this.azimuth = (short)(azimuth & 0xff);
        this.lastMessage = (short)(lastMessage & 0xff);
        this.slip = (short)(slip & 0xff);
        this.cFlags = cFlags;
        this.cliForSNR = cliForSNR & 0xFFFF;
        this.diffCorr = diffCorr;
        this.doppHz = doppHz;
        this.NCOHz = NCOHz;
        this.posResid = posResid;
        this.allocType = allocType & 0xFFFF;
    }

    /*
     * Space Vehicle Slot
     */
    public int getSvSlot() {
        return sv & 0x7F;
    }

    /*
     * Is Space Vehicle Tracked
     */
    public boolean isSvTracked() {
        return sv != 0;
    }

    /*
     * Ephemeris Flags (AVAILABLE_TIMED_OUT | VALID | HEALTH_OK)
     */
    public EnumSet<Ephemeris> getEphemerisFlags() {
        return Ephemeris.parseFromEphemerisAlmanacFlags(almEphmFlags);
    }

    /*
     * Almanac Flags (AVAILABLE | HEALTH_OK)
     */
    public EnumSet<Almanac> getAlmanacFlags() {
        return Almanac.parseFromEphemerisAlmanacFlags(almEphmFlags);
    }

    /*
     * EphemerisAlmanac Flag of Satellite not exisiting
     */
    public boolean satelliteDoesNotExist() {
        return ((almEphmFlags >>> 7) & 0x1) == 1;
    }

    /*
     * Status Bits for carrier bit frame
     */
    public byte getStatus() {
        return status;
    }

    /*
     * Channel Elevation angle
     */
    public byte getElevAngle() {
        return chElev;
    }

    /*
     * 1/2 the Azimuth Angle
     */
    public short getAzimuth() {
        return azimuth;
    }

    /*
     * Last Message processed
     */
    public short getLastMessage() {
        return lastMessage;
    }

    /*
     * Cycle slip on channel 1
     */
    public short getSlip() {
        return slip;
    }

    /*
     * Channel Flags
     */
    public EnumSet<ChannelFlags> getChannelFlags() {
        return ChannelFlags.parseFromMask(cFlags);
    }

    /*
     * Channel is enabled
     */
    public boolean isEnabled() {
        return (cFlags & 0x1) == 1;
    }

    /*
     * Channel is used in the solution
     */
    public boolean usedInSolution() {
        return ((cFlags >>> 1) & 0x1) == 1;
    }

    /*
     * Code lock indicator for SNR
     */
    public int getCliForSNR() {
        return cliForSNR / 32;
    }

    /*
     * Differential correction
     */
    public int getDiffCorr() {
        return diffCorr * 100;
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

    /*
     * Position residual
     */
    public int getPosResid() {
        return posResid * 1000;
    }

    /*
     * RFR_150501 was m_nSpare2
     */
    public int getAllocType() {
        return allocType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Ephemeris e : getEphemerisFlags()) {
            sb.append(String.format("%s|", e));
        }

        String eph = sb.toString();
        if (eph.length() > 0) {
            eph = eph.substring(0, eph.length() - 1);
        }

        sb = new StringBuilder();
        for (Almanac a : getAlmanacFlags()) {
            sb.append(String.format("%s|", a));
        }

        String alm = sb.toString();
        if (alm.length() > 0) {
            alm = alm.substring(0, alm.length() - 1);
        }

        return String.format(
                "SV: %d | Eph: (%s) | Alm: (%s) | EA: %d | Az: %d | Slip: %d | Enabled/Used: %s:%s | DiffCor: %d | DopHz: %d | NCOHz: %d | PosResid: %d",
                getSvSlot(), eph, alm,
                getElevAngle(), getAzimuth(), getSlip(),
                isEnabled(), usedInSolution(),
                getDiffCorr(), getDoppHz(), getNCOHz(), getPosResid());
    }

}