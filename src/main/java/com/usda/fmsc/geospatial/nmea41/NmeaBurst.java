package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position2;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.exceptions.MissingNmeaDataException;
import com.usda.fmsc.geospatial.nmea41.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea41.sentences.*;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;
import com.usda.fmsc.geospatial.utm.UTMTools;

import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;
import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class NmeaBurst implements INmeaBurst {
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
    private ArrayList<Satellite> cachedSatellites;

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
                cachedSatellites = null;
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

    private ArrayList<? extends NmeaSentence> getSentencesByID(SentenceID id) {
        switch (id) {
            case GGA: return (cachedPriorityGGA == null ? (cachedPriorityGGA = (ArrayList<GGASentence>) getSentencesByPriority(gga)) : cachedPriorityGGA);
            case RMC: return (cachedPriorityRMC == null ? (cachedPriorityRMC = (ArrayList<RMCSentence>) getSentencesByPriority(rmc)) : cachedPriorityRMC);
            case GSA: return (cachedPriorityGSA == null ? (cachedPriorityGSA = (ArrayList<GSASentence>) getSentencesByPriority(gsa)) : cachedPriorityGSA);
            case GSV: return (cachedPriorityGSV == null ? (cachedPriorityGSV = (ArrayList<GSVSentence>) getSentencesByPriority(gsv)) : cachedPriorityGSV);
        }

        throw new MissingNmeaDataException(id);
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

        throw new MissingNmeaDataException(SentenceID.RMC, SentenceID.GGA);
    }

    public double getMagVar() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getMagVar();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC);
    }
    public EastWest getMagVarDir() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getMagVarDir();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC);
    }


    public double getTrackAngle() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getTrackAngle();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC);
    }
    public double getGroundSpeed() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getGroundSpeed();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC);
    }


    public Position2 getPosition() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid() && s.hasPosition()) {
                return s.getPosition();
            }
        }

        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid() && s.hasPosition()) {
                return s.getPosition();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC, SentenceID.GGA);
    }
    public boolean hasPosition() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid() && s.hasPosition()) {
                return true;
            }
        }

        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid() && s.hasPosition()) {
                return true;
            }
        }

        return false;
    }

    public double getLatitude() {
        return getPosition().getLatitude();
    }
    public NorthSouth getLatDir() {
        return getPosition().getLatDir();
    }

    public double getLongitude() {
        return getPosition().getLongitude();
    }
    public EastWest getLonDir() {
        return getPosition().getLonDir();
    }

    public double getElevation() {
        return getPosition().getElevation();
    }
    public boolean hasElevation() {
        return getPosition().hasElevation();
    }
    public UomElevation getUomElevation() {
        return getPosition().getUomElevation();
    }

    public UTMCoords getTrueUTM() {
        Position2 pos = getPosition();
        return UTMTools.convertLatLonSignedDecToUTM(pos.getLatitudeSigned(), pos.getLongitudeSigned(), null);
    }
    public UTMCoords getUTM(int utmZone) {
        Position2 pos = getPosition();
        return UTMTools.convertLatLonSignedDecToUTM(pos.getLatitudeSigned(), pos.getLongitudeSigned(), utmZone);
    }

    public double getHorizDilution() {
        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid()) {
                return s.getHorizDilution();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GGA);
    }
    public double getGeoidHeight() {
        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid()) {
                return s.getGeoidHeight();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GGA);
    }

    public UomElevation getGeoUom() {
        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid()) {
                return s.getGeoUom();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GGA);
    }

    public GGASentence.GpsFixType getFixQuality() {
        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid()) {
                return s.getFixQuality();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GGA);
    }

    public int getTrackedSatellitesCount() {
        int count = 0;
        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid()) {
                count += s.getTrackedSatellitesCount();
            }
        }

        return count;
    }
    public ArrayList<Satellite> getSatellitesInView() {
        if (cachedSatellites == null) {
            HashMap<Integer, Satellite> sats = new HashMap<>();

            for (GSVSentence s : (ArrayList<GSVSentence>) getSentencesByID(SentenceID.GSV)) {
                if (s.isValid()) {
                    for (Satellite sat : s.getSatellites()) {
                        if (!sats.containsKey(sat.getNmeaID())) {
                            sats.put(sat.getNmeaID(), sat);
                        } else {
                            sats.get(sat.getNmeaID()).addSignals(sat.getSignals());
                        }
                    }
                }
            }

            return (cachedSatellites = new ArrayList<>(sats.values()));
        } else {
            return cachedSatellites;
        }
    }
    public int getSatellitesInViewCount() {
        int count = 0;
        for (GSVSentence s : (ArrayList<GSVSentence>) getSentencesByID(SentenceID.GSV)) {
            if (s.isValid() && s.getSentenceNumber() == 1) {
                count += s.getSatellitesInViewCount();
            }
        }

        return count;
    }
    public ArrayList<Integer> getUsedSatelliteIDs() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                for (Integer id : s.getSatellitesUsed()) {
                    if (!ids.contains(id)) {
                        ids.add(id);
                    }
                }
            }
        }

        return ids;
    }
    public int getUsedSatellitesCount() {
        int count = 0;
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                count += s.getSatellitesUsedCount();
            }
        }

        return count;
    }

    public GSASentence.Fix getFix() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getFix();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }
    public GSASentence.Mode getMode() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getMode();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }

    public float getHDOP() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getHDOP();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }
    public float getPDOP() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getPDOP();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }
    public float getVDOP() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getVDOP();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }
}
