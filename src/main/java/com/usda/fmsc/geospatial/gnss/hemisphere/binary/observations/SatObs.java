package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

public class SatObs extends BaseObservation {
    public static final int STRUCT_SIZE = 12;

    public final int CS_TT_W3_SNR;
    public final int P7_Doppler_FL;
    public final int CodeAndPhase;

    public SatObs(int snr, int p7_dop, int cap) {
        CS_TT_W3_SNR = snr;
        P7_Doppler_FL = p7_dop;
        CodeAndPhase = cap;
    }

    /*
     * For All signals except GPS L2, SNR = 10.0 * log10(0.1024 * SNR_value)
     * For GPS L2, SNR = 10.0 * log10(0.1164 * SNR_value)
     */
    protected double getSnrMultiRatio() {
        return 0.0124;
    }

    /*
     * Signal Noise Ratio. 0 if not tracked
     */
    public Double getSNR() {
        int snrd = (int) (CS_TT_W3_SNR & 0xFFF);
        return snrd != 0 ? (10d * Math.log10(getSnrMultiRatio() * snrd)) : 0;
    }

    /*
     * Cycle Slip Warn (warning for potential 1/2 cycle slips); a warning exists if
     * any of these bits are set
     */
    public byte getCycleSlipWarn() {
        return (byte) ((CS_TT_W3_SNR & 0x7000) >>> 12);
    }

    /*
     * Long Track Time. Track Time > 25.5 sec if true
     */
    public boolean isLongTrackTime() {
        return ((CS_TT_W3_SNR & 0x8000) >>> 15) == 1;
    }

    /*
     * Track Time (signal tracking time in seconds); LSB = 0.1 seconds; Range = 0 to
     * 25.5 seconds
     */
    public float getTrackTime() {
        return ((CS_TT_W3_SNR & 0xFF0000) >>> 16) / 10f;
    }

    /*
     * Cycle Slips; increments by 1 every cycle slip with natural roll-over after
     * 255
     */
    public int getCycleSlips() {
        return ((CS_TT_W3_SNR & 0xFF000000) >>> 24) & 0xFF;
    }

    /*
     * Phase is Valid
     */
    public boolean isPhaseValid() {
        return (P7_Doppler_FL & 0x01) == 1;
    }

    /*
     * Doppler (magnitude of Doppler); LSB = 1/512 cycle/sec; Range = 0 to 16384
     * cycle/sec
     */
    public int getDoppler() {
        int dop = (int) ((P7_Doppler_FL & 0xFFFFFE) >>> 1) / 512;
        return dop;
    }

    /*
     * Carrier Phase (High part) (Upper 7 bits of the 23 bit carrier phase): LSB =
     * 64 cycles, MSB = 4096 cycles
     */
    public int getCarrierPhaseH() {
        return ((P7_Doppler_FL & 0xFE000000) >>> 25) * 64;
    }

    /*
     * Pseudorange (lower 16 bits of code pseudorange); LSB = 1/256 meters, MSB =
     * 128 meters
     */
    public int getPseudorange() {
        return ((CodeAndPhase & 0xFFFF) / 256);
    }

    /*
     * Carrier Phase (lower 16 bits of the carrier phase); LSB = 1/1024 cycles, MSB
     * = 32 cycles
     */
    public int getCarrierPhaseL() {
        return (((CodeAndPhase & 0xFFFF0000) >> 16) & 0xFFFF) / 1024;
    }

    @Override
    public boolean isValid() {
        return CS_TT_W3_SNR != 0 || P7_Doppler_FL != 0 || CodeAndPhase != 0;
    }

    @Override
    public String toString() {
        return String.format("SNR: %.2f | TT: %s | CS: %d%s | PV: %s | DOP: %d | CP: %d:%d | PS: %d",
                getSNR(),
                isLongTrackTime() ? "(>25.5)" : getTrackTime(),
                getCycleSlips(), getCycleSlipWarn() > 0 ? "!" : "",
                isPhaseValid(),
                getDoppler(),
                getCarrierPhaseH(), getCarrierPhaseL(),
                getPseudorange());
    }
}
