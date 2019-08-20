package com.usda.fmsc.geospatial.nmea41;

import android.text.TextUtils;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position2;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.exceptions.InvalidChecksumException;
import com.usda.fmsc.geospatial.nmea41.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea41.sentences.*;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;
import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;
import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

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

    //GGA Sentence
    private HashMap<TalkerID, ArrayList<GGASentence>> gga = new HashMap<>();

    //GSA Sentence
    private HashMap<TalkerID, ArrayList<GSASentence>> gsa = new HashMap<>();

    //GSV Sentence
    private HashMap<TalkerID, ArrayList<GSVSentence>> gsv = new HashMap<>();

    private ArrayList<NmeaSentence> allSenteneces = new ArrayList<>();


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
                allSenteneces.add(ggaSentence);
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
                allSenteneces.add(rmcSentence);
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
                allSenteneces.add(gsaSentence);
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
                allSenteneces.add(gsvSentence);
                return gsvSentence;
            }
            default: throw new UnsupportedSentenceException(sentenceID, sentence);
        }
    }

    public boolean isValid() {
        for (NmeaSentence sentence : allSenteneces) {
            if (!sentence.isValid())
                return false;
        }

        return true;
    }
    public boolean isValid(SentenceID id) {
        return false;
    }
    public boolean isComplete() {
        return false;
    }

    public DateTime getFixTime() {
        return null;
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
