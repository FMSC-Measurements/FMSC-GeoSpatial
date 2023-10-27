package com.usda.fmsc.geospatial.gnss.hemisphere.binary.process;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.GnssSystem;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Signal;

public class ChannelSignalSystem {
    /*
     * GNSS System used on Satellite
     */
    public static GnssSystem getGnssSystem(int CHAN_SIGNAL_SYS) {
        return GnssSystem.parse(CHAN_SIGNAL_SYS & 0xF);
    }

    /*
     * Signal used from Satellite
     */
    public static Signal getSignal(int CHAN_SIGNAL_SYS) {
        int signalId = (CHAN_SIGNAL_SYS >>> 4) & 0xF;

        switch (getGnssSystem(CHAN_SIGNAL_SYS)) {
            case GPS: {
                switch (signalId) {
                    case 0:
                        return Signal.GPS_L1CA;
                    case 1:
                        return Signal.GPS_L2P;
                    case 2:
                        return Signal.GPS_L2C;
                    case 3:
                        return Signal.GPS_L5;
                    default:
                        return Signal.Unknown;
                }
            }
            case GLONASS: {
                switch (signalId) {
                    case 0:
                        return Signal.GLONASS_G1C_G1P;
                    case 1:
                        return Signal.GLONASS_G2C_G2P;
                    case 4:
                        return Signal.GLONASS_G10C;
                    case 5:
                        return Signal.GLONASS_G20C;
                    case 6:
                        return Signal.GLONASS_G30C;
                    default:
                        return Signal.Unknown;
                }
            }
            case Galileo: {
                switch (signalId) {
                    case 0:
                        return Signal.GALILEO_E1BC;
                    case 1:
                        return Signal.GALILEO_E5A;
                    case 2:
                        return Signal.GALILEO_E5B;
                    case 3:
                        return Signal.GALILEO_E6;
                    case 4:
                        return Signal.GALILEO_ALTBOC;
                    default:
                        return Signal.Unknown;
                }
            }
            case BeiDou:
                switch (signalId) {
                    case 0:
                        return Signal.BEIDOU_B1I;
                    case 1:
                        return Signal.BEIDOU_B2I;
                    case 2:
                        return Signal.BEIDOU_B3I;
                    case 3:
                        return Signal.BEIDOU_B1BOC;
                    case 4:
                        return Signal.BEIDOU_B2A;
                    case 5:
                        return Signal.BEIDOU_B2B;
                    case 6:
                        return Signal.BEIDOU_B2C;
                    case 7:
                        return Signal.BEIDOU_ACEBOC;
                    default:
                        return Signal.Unknown;
                }
            case QZSS:
                switch (signalId) {
                    case 0:
                        return Signal.QZS_L1CA;
                    case 2:
                        return Signal.QZS_L2C;
                    case 3:
                        return Signal.QZS_L5;
                    case 4:
                        return Signal.QZS_L1C;
                    default:
                        return Signal.Unknown;
                }
            case Unknown:
            default:
                return Signal.Unknown;
        }
    }

    /*
     * Channel used from Satellite
     */
    public static int getChannel(int CHAN_SIGNAL_SYS) {
        return (CHAN_SIGNAL_SYS >>> 8) & 0x1F;
    }

    /*
     * GLONASS P-Code is used
     */
    public static boolean isGlonassPCodeUsed(int CHAN_SIGNAL_SYS) {
        return ((CHAN_SIGNAL_SYS >>> 13) & 0x1) == 1;
    }

    public static String toString(int CHAN_SIGNAL_SYS) {
        return String.format("GNSS: %s (%s:%s)",
                getGnssSystem(CHAN_SIGNAL_SYS), getSignal(CHAN_SIGNAL_SYS).toStringSignalOnly(),
                getChannel(CHAN_SIGNAL_SYS));
    }
}
