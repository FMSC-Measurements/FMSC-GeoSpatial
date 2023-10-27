package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

/*
 * L1(CA) satellite code observation data
 */
public class L1CASatObs extends SatObsCMP {

    public L1CASatObs(int snr, int p7_dop, int cap, int cmp) {
        super(snr, p7_dop, cap, cmp);
    }
}
