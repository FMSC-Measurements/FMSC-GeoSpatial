package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.GnssSystem;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Signal;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.process.ChannelSignalSystem;

public class SatObsCMPwSig extends SatObsCMP {
    public final int CHAN_SIGNAL_SYS;

    public SatObsCMPwSig(int snr, int p7_dop, int cap, int cmp, short css) {
        super(snr, p7_dop, cap, cmp);
        CHAN_SIGNAL_SYS = css;
    }

    /*
     * GNSS System used on Satellite
     */
    public GnssSystem getGnssSystem() {
        return ChannelSignalSystem.getGnssSystem(CHAN_SIGNAL_SYS);
    }

    /*
     * Signal used from Satellite
     */
    public Signal getSignal() {
        return ChannelSignalSystem.getSignal(CHAN_SIGNAL_SYS);
    }

    /*
     * Channel used from Satellite
     */
    public int getChannel() {
        return ChannelSignalSystem.getChannel(CHAN_SIGNAL_SYS);
    }

    /*
     * GLONASS P-Code is used
     */
    public boolean isGlonassPCodeUsed() {
        return ChannelSignalSystem.isGlonassPCodeUsed(CHAN_SIGNAL_SYS);
    }

    @Override
    public boolean isValid() {
        return super.isValid() || CHAN_SIGNAL_SYS != 0;
    }

    @Override
    public String toString() {
        return String.format("GNSS: %s (%s:%s) | %s",
                getGnssSystem(), getSignal().toStringSignalOnly(), getChannel(),
                super.toString());
    }
}
