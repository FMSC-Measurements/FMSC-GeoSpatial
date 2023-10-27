package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

/*
 * L1(P) code observation data
 */
public class L1PObs extends BaseObservation {
    public final long L1PCode;

    public L1PObs(long code) {
        L1PCode = code;
    }

    /*
     * L1(P) Range (lower 16 bits of the L1P code pseudorange); LSB = 1/256 meters,
     * MSB = 128 meters
     */
    public int getRange() {
        return (int) (L1PCode & 0xFFFF);
    }

    /*
     * Signal Noise Ratio. 0 if not tracked
     */
    public Double getSNR() {
        int snrd = (int)((L1PCode & 0xFFF0000) >> 4) & 0xFFF;
        return snrd != 0 ? (10d * Math.log10(0.1164 * snrd)) : 0;
    }

    @Override
    public boolean isValid() {
        return L1PCode != 0;
    }

    @Override
    public String toString() {
        return String.format("SNR: %.2f | Range: %d", getSNR(), getRange());
    }

}
