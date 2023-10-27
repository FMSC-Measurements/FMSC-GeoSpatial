package com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.GnssSystem;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Signal;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.process.ChannelSignalSystem;

public class GenericChannelDataWSig extends GenericChannelData {
    public final int CHAN_SIGNAL_SYS;

    public GenericChannelDataWSig(byte sv, byte almEphmFlags, byte status, byte chElev, byte azimuth, byte lastMessage,
            byte slip, byte cFlags, int cliForSNR, short diffCorr, short doppHz, short NCOHz, short posResid,
            short allocType, short css) {
        super(sv, almEphmFlags, status, chElev, azimuth, lastMessage, slip, cFlags, cliForSNR, diffCorr, doppHz, NCOHz,
                posResid, allocType);

        this.CHAN_SIGNAL_SYS = css;
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
    public String toString() {
        return String.format("%s | %s",
                ChannelSignalSystem.toString(CHAN_SIGNAL_SYS),
                super.toString());
    }
}
