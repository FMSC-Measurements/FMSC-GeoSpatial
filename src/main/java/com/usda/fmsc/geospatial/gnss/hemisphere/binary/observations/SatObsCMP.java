package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

/*
 * Satellite code observation data with PRN, Time since last stressed or cycle slipper and Range
 */
public class SatObsCMP extends SatObs {
    public final int CodeMSBsPRN;

    public SatObsCMP(int snr, int p7_dop, int cap, int cmp) {
        super(snr, p7_dop, cap);
        CodeMSBsPRN = cmp;
    }

    /*
     * PRN (space vehicle ID); PRN = 0 if no data
     */
    public int getPRN() {
        return (CodeMSBsPRN & 0xFF);
    }

    /*
     * Time, in units of 1/100th sec, since carrier phase tracking was last stressed
     * or cycle slipped
     */
    public double getTimeSinceStressedOrSlipped() {
        return (Math.log(((CodeMSBsPRN & 0x1F00) >>> 8) + 1) / Math.log(2)) / 100d;
    }

    /*
     * Range (upper 19 bits of L1CA); LSB = 256 meters, MSB = 67,108,864
     * meters
     */
    public int getRange() {
        return ((CodeMSBsPRN & 0xFFFFE000) >>> 13) * 256;
    }

    @Override
    public boolean isValid() {
        return super.isValid() || CodeMSBsPRN != 0;
    }

    @Override
    public String toString() {
        return String.format("PRN: %d | TSLS: %f | Range: %d | %s",
                getPRN(),
                getTimeSinceStressedOrSlipped(),
                getRange(),
                super.toString());
    }

}
