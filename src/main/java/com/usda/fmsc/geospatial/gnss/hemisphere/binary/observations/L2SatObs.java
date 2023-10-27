package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

/*
 * L2 satellite observation data
 */
public class L2SatObs extends SatObs {

    public L2SatObs(int snr, int p7_dop, int cap) {
        super(snr, p7_dop, cap);
    }

    /*
     * For GPS L2, SNR = 10.0 * log10(0.1164 * SNR_value)
     */
    @Override
    protected double getSnrMultiRatio() {
        return 0.1164;
    }
}
