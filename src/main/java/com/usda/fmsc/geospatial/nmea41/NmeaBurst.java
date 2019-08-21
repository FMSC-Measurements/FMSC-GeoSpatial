package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position2;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.exceptions.NmeaException;
import com.usda.fmsc.geospatial.nmea41.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea41.sentences.*;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;
import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;
import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class NmeaBurst {
    private static TalkerID[] priorityIds = new TalkerID[] {
            TalkerID.GP,
            TalkerID.GN,
            TalkerID.GL,
            TalkerID.GA,
            TalkerID.GB,
            TalkerID.BD,
            TalkerID.PQ,
            TalkerID.QZ,
            TalkerID.LC,
            TalkerID.II,
            TalkerID.IN,
            TalkerID.EC,
            TalkerID.CD
    };

    //RMC Sentence
    private HashMap<TalkerID, ArrayList<RMCSentence>> rmc = new HashMap<>();
    private ArrayList<RMCSentence> cachedPriorityRMC;

    //GGA Sentence
    private HashMap<TalkerID, ArrayList<GGASentence>> gga = new HashMap<>();
    private ArrayList<GGASentence> cachedPriorityGGA;

    //GSA Sentence
    private HashMap<TalkerID, ArrayList<GSASentence>> gsa = new HashMap<>();
    private ArrayList<GSASentence> cachedPriorityGSA;

    //GSV Sentence
    private HashMap<TalkerID, ArrayList<GSVSentence>> gsv = new HashMap<>();
    private ArrayList<GSVSentence> cachedPriorityGSV;

    private ArrayList<NmeaSentence> allSentences = new ArrayList<>();


    public NmeaSentence addNmeaSentence(String sentence) {
        if (sentence == null) throw new NullPointerException("sentence");
        //if (!NmeaSentence.validateChecksum(sentence)) throw new InvalidChecksumException(sentence);

        TalkerID talkerID = TalkerID.parse(sentence);
        SentenceID sentenceID = SentenceID.parse(sentence);



        switch (sentenceID) {
            case GGA: {
                ArrayList<GGASentence> ggas;
                if (!gga.containsKey(talkerID) || (ggas = gga.get(talkerID)) == null) {
                    ggas = new ArrayList<>();
                    gga.put(talkerID, ggas);
                }

                GGASentence ggaSentence = new GGASentence(sentence);
                ggas.add(ggaSentence);
                allSentences.add(ggaSentence);
                cachedPriorityGGA = null;
                return ggaSentence;
            }
            case RMC: {
                ArrayList<RMCSentence> rmcs;
                if (!rmc.containsKey(talkerID) || (rmcs = rmc.get(talkerID)) == null) {
                    rmcs = new ArrayList<>();
                    rmc.put(talkerID, rmcs);
                }

                RMCSentence rmcSentence = new RMCSentence(sentence);
                rmcs.add(rmcSentence);
                allSentences.add(rmcSentence);
                cachedPriorityRMC = null;
                return rmcSentence;
            }
            case GSA: {
                ArrayList<GSASentence> gsas;
                if (!gsa.containsKey(talkerID) || (gsas = gsa.get(talkerID)) == null) {
                    gsas = new ArrayList<>();
                    gsa.put(talkerID, gsas);
                }

                GSASentence gsaSentence = new GSASentence(sentence);
                gsas.add(gsaSentence);
                allSentences.add(gsaSentence);
                cachedPriorityGSA = null;
                return gsaSentence;
            }
            case GSV: {
                ArrayList<GSVSentence> gsvs;
                if (!gsv.containsKey(talkerID) || (gsvs = gsv.get(talkerID)) == null) {
                    gsvs = new ArrayList<>();
                    gsv.put(talkerID, gsvs);
                }

                GSVSentence gsvSentence = new GSVSentence(sentence);
                gsvs.add(gsvSentence);
                allSentences.add(gsvSentence);
                cachedPriorityGSV = null;
                return gsvSentence;
            }
            default: throw new UnsupportedSentenceException(sentenceID, sentence);
        }
    }

    public boolean isValid() {
        for (NmeaSentence sentence : allSentences) {
            if (!sentence.isValid())
                return false;
        }

        return true;
    }
    public boolean isValid(SentenceID id) {
        for (NmeaSentence sentence : getSentencesByID(id)) {
            if (!sentence.isValid())
                return false;
        }

        return true;
    }
    public boolean isComplete() {
        return rmc.size() > 0 && gga.size() > 0 && gsa.size() > 0 && gsv.size() > 0;
    }


    private ArrayList<? extends NmeaSentence> getSentencesByPriority(HashMap<TalkerID, ? extends ArrayList<? extends NmeaSentence>> map) {
        ArrayList<NmeaSentence> sentences = new ArrayList<>();
        ArrayList<? extends NmeaSentence> temp;

        for (TalkerID talkerID : priorityIds) {
            if (map.containsKey(talkerID) && (temp = map.get(talkerID)) != null) {
                sentences.addAll(temp);
            }
        }

        return sentences;
    }

    public ArrayList<? extends NmeaSentence> getSentencesByID(SentenceID id) {
        switch (id) {
            case GGA: return (cachedPriorityGGA == null ? (cachedPriorityGGA = (ArrayList<GGASentence>) getSentencesByPriority(gga)) : cachedPriorityGGA);
            case RMC: return (cachedPriorityRMC == null ? (cachedPriorityRMC = (ArrayList<RMCSentence>) getSentencesByPriority(rmc)) : cachedPriorityRMC);
            case GSA: return (cachedPriorityGSA == null ? (cachedPriorityGSA = (ArrayList<GSASentence>) getSentencesByPriority(gsa)) : cachedPriorityGSA);
            case GSV: return (cachedPriorityGSV == null ? (cachedPriorityGSV = (ArrayList<GSVSentence>) getSentencesByPriority(gsv)) : cachedPriorityGSV);
        }

        throw new RuntimeException("Sentences Not Available");
    }

    public DateTime getFixTime() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getFixTime();
            }
        }

        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid()) {
                return s.getFixTime().toDateTimeToday();
            }
        }

        throw new RuntimeException("No Fix Time containing sentence found");
    }

    public double getMagVar() {
        return 0;
    }
    public EastWest getMagVarDir() {
        return null;
    }


    public double getTrackAngle() {
        return 0;
    }
    public double getGroundSpeed() {
        return 0;
    }


    public Position2 getPosition() {

        return null;
    }
    public boolean hasPosition() {
        return false;
    }

    public double getLatitude() {
        return 0;
    }
    public NorthSouth getLatDir() {
        return null;
    }

    public double getLongitude() {
        return 0;
    }
    public EastWest getLonDir() {
        return null;
    }

    public double getElevation() {
        return 0;
    }
    public boolean hasElevation() {
        return false;
    }
    public UomElevation getUomElevation() {
        return null;
    }

    public UTMCoords getTrueUTM() {
        return null;
    }
    public UTMCoords getUTM(int utmZone) {
        return null;
    }

    public double getHorizDilution() {
        return 0;
    }
    public double getGeoidHeight() {
        return 0;
    }

    public UomElevation getGeoUom() {
        return null;
    }

    public GGASentence.GpsFixType getFixQuality() {
        return null;
    }

    public int getTrackedSatellitesCount() {
        return 0;
    }
    public ArrayList<Satellite> getSatellitesInView() {
        return null;
    }
    public int getSatellitesInViewCount() {
        return 0;
    }
    public ArrayList<Integer> getUsedSatelliteIDs() {
        return null;
    }
    public int getUsedSatellitesCount() {
        return 0;
    }

    public GSASentence.Fix getFix() {
        return null;
    }
    public GSASentence.Mode getMode() {
        return null;
    }

    public float getHDOP() {
        return 0;
    }
    public float getPDOP() {
        return 0;
    }
    public float getVDOP() {
        return 0;
    }
}
