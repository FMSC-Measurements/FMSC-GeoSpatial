package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

/*
 * L1 satellite code observation data
 */
public class L1SatObs extends SatObsCMP {

    public L1SatObs(int snr, int p7_dop, int cap, int cmp) {
        super(snr, p7_dop, cap, cmp);
    }
}
