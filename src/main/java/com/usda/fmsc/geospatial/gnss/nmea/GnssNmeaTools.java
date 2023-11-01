package com.usda.fmsc.geospatial.gnss.nmea;

import com.usda.fmsc.geospatial.gnss.codes.GnssSignal;
import com.usda.fmsc.geospatial.gnss.codes.GnssSystem;
import com.usda.fmsc.geospatial.gnss.hemisphere.nmea.sentences.GBSSentence;
import com.usda.fmsc.geospatial.gnss.juniper.nmea.sentences.BATTSentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.GGASentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.GLLSentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.GNSSentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.GSASentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.GSTSentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.GSVSentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.RD1Sentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.RMCSentence;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.ZDASentence;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class GnssNmeaTools {

    private static final GnssSystem[] SYSTEM_IDS_TO_SYSTEMS = new GnssSystem[] {
        GnssSystem.UNKOWN, // 0
        GnssSystem.GPS,     // 1
        GnssSystem.GLONASS, // 2
        GnssSystem.GALILEO, // 3
        GnssSystem.BEIDOU,  // 4
        GnssSystem.QZSS,    // 5
        GnssSystem.NAV_IC   // 6
    };

    public static GnssSystem getGnssSystemFromNmeaSystemId(int sysId) {
        if (sysId < 1 || sysId > 6) {
            return GnssSystem.UNKOWN;
        } else {
            return SYSTEM_IDS_TO_SYSTEMS[sysId];
        }
    }

    
    public static GnssSystem getGnssSystemFromTalkerId(TalkerID talkerID) {
        switch (talkerID) {
            case GP:
                return GnssSystem.GPS;
            case GA:
                return GnssSystem.GALILEO;
            case GL:
                return GnssSystem.GLONASS;
            case GB:
            case BD:
                return GnssSystem.BEIDOU;
            case QZ:
                return GnssSystem.QZSS;
            case GN:
                return GnssSystem.MULTI_GNSS;
            default:
                return GnssSystem.UNKOWN;
        }
    }


    public static int getPRNfromNmeaId(GnssSystem system, int nmeaId) {
        switch (system) {
            case GPS:
                return nmeaId > 32 ? nmeaId + 87 : nmeaId;
            case GLONASS:
                return nmeaId - 64;
            case BEIDOU:
                return nmeaId - 100;
            default:
                return nmeaId;
        }
    }


    public static boolean isSBASfromNmeaId(GnssSystem system, int nmeaId) {
        switch (system) {
            case GPS:
                return nmeaId > 32;
            case GLONASS:
            case NAV_IC:
                return nmeaId > 32 && nmeaId < 65;
            case GALILEO:
                return nmeaId > 36 && nmeaId < 65;
            case QZSS:
                return nmeaId > 54 && nmeaId < 64;
            default:
                return false;
        }
    }


    public static GnssSignal getSignalFromIdAndTalker(int sigId, TalkerID talkerID) {
        switch (talkerID) {
            case GP: {
                switch (sigId) {
                    case 0:
                        return GnssSignal.GPS_ALL;
                    case 1:
                    case 2:
                    case 3:
                        return GnssSignal.GPS_L1;
                    case 4:
                    case 5:
                        return GnssSignal.GPS_L2;
                    case 6:
                    case 7:
                    case 8:
                        return GnssSignal.GPS_L5;
                    default:
                        return GnssSignal.GPS_UNKNOWN;
                }
            }
            case GL: {
                switch (sigId) {
                    case 0:
                        return GnssSignal.GLONASS_ALL;
                    case 1:
                    case 2:
                        return GnssSignal.GLONASS_G1;
                    case 3:
                    case 4:
                        return GnssSignal.GLONASS_G2;
                    default:
                        return GnssSignal.GLONASS_UNKNOWN;
                }
            }
            case GA: {
                switch (sigId) {
                    case 0:
                        return GnssSignal.Galileo_ALL;
                    case 1:
                    case 2:
                    case 3:
                        return GnssSignal.Galileo_E5;
                    case 4:
                    case 5:
                        return GnssSignal.Galileo_E6;
                    case 6:
                    case 7:
                        return GnssSignal.Galileo_E1;
                    default:
                        return GnssSignal.Galileo_UNKNOWN;
                }
            }
            case BD:
            case GB: {
                switch (sigId) {
                    case 0:
                        return GnssSignal.BeiDou_ALL;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return GnssSignal.BeiDou_B1;
                    case 5:
                    case 6:
                    case 7:
                    case 11:
                    case 12:
                        return GnssSignal.BeiDou_B2;
                    case 8:
                    case 9:
                    case 10:
                        return GnssSignal.BeiDou_B3;
                    default:
                        return GnssSignal.BeiDou_UNKNOWN;
                }
            }
            case QZ: {
                switch (sigId) {
                    case 0:
                        return GnssSignal.QZSS_ALL;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        return GnssSignal.QZSS_L1;
                    case 5:
                    case 6:
                        return GnssSignal.QZSS_L2;
                    case 7:
                    case 8:
                        return GnssSignal.QZSS_L5;
                    case 9:
                    case 10:
                        return GnssSignal.QZSS_L6;
                    default:
                        return GnssSignal.QZSS_UNKNOWN;
                }
            }
            default:
                return GnssSignal.Unknown;
        }
    }

    public static NmeaSentence parseGnssNmeaSentence(String nmea) {
        TalkerID talkerID = TalkerID.parse(nmea);

        switch (talkerID) {
            case RD1: return new RD1Sentence(nmea);
            case PSAT_GBS: return new GBSSentence(nmea);
            case PJSI_BATT: return new BATTSentence(nmea);
            default: break;
        }

        switch (SentenceID.parse(nmea)) {
            case GGA:
                return new GGASentence(talkerID, nmea);
            case RMC:
                return new RMCSentence(talkerID, nmea);
            case GSA:
                return new GSASentence(talkerID, nmea);
            case GSV:
                return new GSVSentence(talkerID, nmea);
            case GLL:
                return new GLLSentence(talkerID, nmea);
            case GNS:
                return new GNSSentence(talkerID, nmea);
            case ZDA:
                return new ZDASentence(talkerID, nmea);
            case GST:
                return new GSTSentence(talkerID, nmea);
            default: break;
        }

        return null;
    }
}
