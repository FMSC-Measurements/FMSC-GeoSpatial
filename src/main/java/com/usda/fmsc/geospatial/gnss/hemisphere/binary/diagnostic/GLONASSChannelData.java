package com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic;

import java.util.EnumSet;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Almanac;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Ephemeris;

public class GLONASSChannelData {
    public static final int STRUCT_SIZE = 24;

    private short diffCorrL1, doppHz, NCOHzL1, NCOHzL2, posResid1, posResid2;
    private byte sv, almEphmFlags, statusL1, statusL2, chElev;
    private int azimuth, lastMessage, slip, cliForSNR_L1, cliForSNR_L2;

    public GLONASSChannelData(byte sv, byte almEphmFlags, byte statusL1, byte statusL2, byte chElev,
        byte azimuth, byte lastMessage, byte slip, short cliForSNR_L1, short cliForSNR_L2,
        short diffCorrL1, short doppHz, short NCOHzL1, short NCOHzL2, short posResid1, short posResid2) {

        this.sv = sv;
        this.almEphmFlags = almEphmFlags;
        this.statusL1 = statusL1;
        this.statusL2 = statusL2;
        this.chElev = chElev;
        this.azimuth = azimuth & 0xFF;
        this.lastMessage = lastMessage & 0xFF;
        this.slip = slip & 0xFF;
        this.cliForSNR_L1 = cliForSNR_L1 & 0xFFFF;
        this.cliForSNR_L2 = cliForSNR_L2 & 0xFFFF;
        this.diffCorrL1 = diffCorrL1;
        this.doppHz = doppHz;
        this.NCOHzL1 = NCOHzL1;
        this.NCOHzL2 = NCOHzL2;
        this.posResid1 = posResid1;
        this.posResid2 = posResid2;
    }

    /*
     * Space Vehicle Slot
     * 
     * Bit (0-6) = SV slot, 0 == not tracked
     * Bit 7 = Knum flag
     * = KNum+8 if bit 7 set
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
     * L1 Status Bits for carrier bit frame
     */
    public byte getStatusL1() {
        return statusL1;
    }

    /*
     * L2 Status Bits for carrier bit frame
     */
    public byte getStatus() {
        return statusL2;
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
    public int getAzimuth() {
        return azimuth;
    }

    /*
     * Last Message processed
     */
    public int getLastMessage() {
        return lastMessage;
    }

    /*
     * Cycle slip on channel 1
     */
    public int getSlip() {
        return slip;
    }

    /*
     * L1 Code lock indicator for SNR
     */
    public int getL1CliForSNR() {
        return cliForSNR_L1 / 32;
    }

    /*
     * L2 Code lock indicator for SNR
     */
    public int getL2CliForSNR() {
        return cliForSNR_L2 / 32;
    }

    /*
     * Differential correction for L1
     */
    public int getDiffCorr() {
        return diffCorrL1 * 100;
    }

    /*
     * Doppler in HZ at GLONASS L1 frequency
     */
    public short getDoppHz() {
        return doppHz;
    }

    /*
     * Track from NCO in HZ for L1
     */
    public short getL1NCOHz() {
        return NCOHzL1;
    }

    /*
     * Track from NCO in HZ for L2
     */
    public short getL2NCOHz() {
        return NCOHzL2;
    }

    /*
     * Position residual 1
     */
    public int getPosResid1() {
        return posResid1 * 1000;
    }

    /*
     * Position residual 2
     */
    public int getPosResid2() {
        return posResid2 * 1000;
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
            "SV: %d | Eph: (%s) | Alm: (%s) | EA: %d | Az: %d | Slip: %d | DiffCor: %d | DopHz: %d | NCOHz(L1/L2): %d/%d | PosResid(1/2): %d/%d",
            getSvSlot(), eph, alm,
            getElevAngle(), getAzimuth(), getSlip(),
            getDiffCorr(), getDoppHz(),
            getL1NCOHz(), getL2NCOHz(),
            getPosResid1(), getPosResid2());
    }

}