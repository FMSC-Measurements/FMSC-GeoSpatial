package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.codes.PositionMode;
import com.usda.fmsc.geospatial.nmea41.exceptions.InvalidChecksumException;
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
    private static final TalkerID[] priorityIds = new TalkerID[] {
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
    private final HashMap<TalkerID, ArrayList<RMCSentence>> rmc = new HashMap<>();
    private ArrayList<RMCSentence> cachedPriorityRMC;

    //GGA Sentence
    private final HashMap<TalkerID, ArrayList<GGASentence>> gga = new HashMap<>();
    private ArrayList<GGASentence> cachedPriorityGGA;

    //GSA Sentence
    private final HashMap<TalkerID, ArrayList<GSASentence>> gsa = new HashMap<>();
    private ArrayList<GSASentence> cachedPriorityGSA;

    //GSV Sentence
    private final HashMap<TalkerID, ArrayList<GSVSentence>> gsv = new HashMap<>();
    private ArrayList<GSVSentence> cachedPriorityGSV;
    private ArrayList<Satellite> cachedSatellites;

    //GLL Sentence
    private HashMap<TalkerID, ArrayList<GLLSentence>> gll;
    private ArrayList<GLLSentence> cachedPriorityGLL;

    //GNS Sentence
    private HashMap<TalkerID, ArrayList<GNSSentence>> gns;
    private ArrayList<GNSSentence> cachedPriorityGNS;

    //GST Sentence
    private HashMap<TalkerID, ArrayList<GSTSentence>> gst;
    private ArrayList<GSTSentence> cachedPriorityGST;

    //ZDA Sentence
    private HashMap<TalkerID, ArrayList<ZDASentence>> zda;
    private ArrayList<ZDASentence> cachedPriorityZDA;

    private Position cachedPosition;


    public NmeaSentence addNmeaSentence(String sentence) {
        if (sentence == null) throw new NullPointerException("sentence");
        if (!NmeaSentence.validateChecksum(sentence)) throw new InvalidChecksumException(sentence);

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
                cachedPriorityGGA = null;
                cachedPosition = null;
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
                cachedPriorityRMC = null;
                cachedPosition = null;
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
                cachedPriorityGSV = null;
                cachedSatellites = null;
                return gsvSentence;
            }
            case GLL: {
                if (gll == null) gll = new HashMap<>();

                ArrayList<GLLSentence> glls;
                if (!gll.containsKey(talkerID) || (glls = gll.get(talkerID)) == null) {
                    glls = new ArrayList<>();
                    gll.put(talkerID, glls);
                }

                GLLSentence gllSentence = new GLLSentence(sentence);
                glls.add(gllSentence);
                cachedPriorityGLL = null;
                cachedPosition = null;
                return gllSentence;
            }
            case GNS: {
                if (gns == null) gns = new HashMap<>();

                ArrayList<GNSSentence> gnss;
                if (!gns.containsKey(talkerID) || (gnss = gns.get(talkerID)) == null) {
                    gnss = new ArrayList<>();
                    gns.put(talkerID, gnss);
                }

                GNSSentence gnsSentence = new GNSSentence(sentence);
                gnss.add(gnsSentence);
                cachedPriorityGNS = null;
                cachedPosition = null;
                return gnsSentence;
            }
            case GST: {
                if (gst == null) gst = new HashMap<>();

                ArrayList<GSTSentence> gsts;
                if (!gst.containsKey(talkerID) || (gsts = gst.get(talkerID)) == null) {
                    gsts = new ArrayList<>();
                    gst.put(talkerID, gsts);
                }

                GSTSentence gstSentence = new GSTSentence(sentence);
                gsts.add(gstSentence);
                cachedPriorityGST = null;
                return gstSentence;
            }
            case ZDA: {
                if (zda == null) zda = new HashMap<>();

                ArrayList<ZDASentence> zdas;
                if (!zda.containsKey(talkerID) || (zdas = zda.get(talkerID)) == null) {
                    zdas = new ArrayList<>();
                    zda.put(talkerID, zdas);
                }

                ZDASentence zdaSentence = new ZDASentence(sentence);
                zdas.add(zdaSentence);
                cachedPriorityGSA = null;
                return zdaSentence;
            }
            default: throw new UnsupportedSentenceException(sentenceID, sentence);
        }
    }

    public boolean isValid() {
        return areAnyValid(SentenceID.RMC) &&
                areAnyValid(SentenceID.GGA) &&
                isValid(SentenceID.GSV) &&
                areAnyValid(SentenceID.GSA);
    }
    public boolean isValid(SentenceID id) {
        boolean hasNmea = false;
        for (NmeaSentence sentence : getSentencesByID(id)) {
            if (!sentence.isValid())
                return false;

            if (!hasNmea) {
                hasNmea = true;
            }
        }

        return hasNmea;
    }
    public boolean areAnyValid(SentenceID id) {
        for (NmeaSentence sentence : getSentencesByID(id)) {
            if (sentence.isValid())
                return true;
        }

        return false;
    }

    public boolean isComplete() {
        return rmc.size() > 0 && gga.size() > 0 && gsa.size() > 0 && gsv.size() > 0;
    }

    public boolean hasSentence(SentenceID id) {
        return getSentencesByID(id).size() > 0;
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
            case GLL: return (cachedPriorityGLL == null ? (cachedPriorityGLL = (gll == null ? new ArrayList<>() : (ArrayList<GLLSentence>) getSentencesByPriority(gll))) : cachedPriorityGLL);
            case GNS: return (cachedPriorityGNS == null ? (cachedPriorityGNS = (gns == null ? new ArrayList<>() : (ArrayList<GNSSentence>) getSentencesByPriority(gns))) : cachedPriorityGNS);
            case GST: return (cachedPriorityGST == null ? (cachedPriorityGST = (gst == null ? new ArrayList<>() : (ArrayList<GSTSentence>) getSentencesByPriority(gst))) : cachedPriorityGST);
            case ZDA: return (cachedPriorityZDA == null ? (cachedPriorityZDA = (zda == null ? new ArrayList<>() : (ArrayList<ZDASentence>) getSentencesByPriority(zda))) : cachedPriorityZDA);
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

        for (GLLSentence s : (ArrayList<GLLSentence>) getSentencesByID(SentenceID.GLL)) {
            if (s.isValid()) {
                return s.getFixTime().toDateTimeToday();
            }
        }

        for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
            if (s.isValid() && s.hasPosition()) {
                return s.getFixTime().toDateTimeToday();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC, SentenceID.GGA, SentenceID.GLL, SentenceID.GNS);
    }

    public Double getMagVar() {
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


    public Double getTrackAngle() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getTrackAngle();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC);
    }
    public Double getGroundSpeed() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.getGroundSpeed();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC);
    }


    public Position getPosition() {
        if (cachedPosition == null) {
            for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
                if (s.isValid() && s.hasPosition()) {
                    return (cachedPosition = s.getPosition());
                }
            }

            for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
                if (s.isValid() && s.hasPosition()) {
                    return (cachedPosition = s.getPosition());
                }
            }

            for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
                if (s.isValid() && s.hasPosition()) {
                    return (cachedPosition = s.getPosition());
                }
            }

            for (GLLSentence s : (ArrayList<GLLSentence>) getSentencesByID(SentenceID.GLL)) {
                if (s.isValid() && s.hasPosition()) {
                    return (cachedPosition = s.getPosition());
                }
            }
        } else {
            return cachedPosition;
        }

        throw new MissingNmeaDataException(SentenceID.RMC, SentenceID.GGA, SentenceID.GLL, SentenceID.GNS);
    }
    public boolean hasPosition() {
        for (GGASentence s : (ArrayList<GGASentence>) getSentencesByID(SentenceID.GGA)) {
            if (s.isValid() && s.hasPosition()) {
                cachedPosition = s.getPosition();
                return true;
            }
        }

        for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
            if (s.isValid() && s.hasPosition()) {
                cachedPosition = s.getPosition();
                return true;
            }
        }

        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid() && s.hasPosition()) {
                cachedPosition = s.getPosition();
                return true;
            }
        }

        for (GLLSentence s : (ArrayList<GLLSentence>) getSentencesByID(SentenceID.GLL)) {
            if (s.isValid() && s.hasPosition()) {
                cachedPosition = s.getPosition();
                return true;
            }
        }

        return false;
    }

    public double getLatitude() {
        return getPosition().getLatitude();
    }
    public double getLatitudeSD() {
        return getPosition().getLatitudeSignedDecimal();
    }
    public NorthSouth getLatDir() {
        return getPosition().getLatDir();
    }

    public double getLongitude() {
        return getPosition().getLongitude();
    }
    public double getLongitudeSD() {
        return getPosition().getLongitudeSignedDecimal();
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
        return UTMTools.convertLatLonToUTM(getPosition());
    }
    public UTMCoords getUTM(int utmZone) {
        return UTMTools.convertLatLonToUTM(getPosition(), utmZone);
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

    public boolean hasPositionMode() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid()) {
                return s.hasPositionMode();
            }
        }

        for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
            if (s.isValid()) {
                return s.hasPositionMode();
            }
        }

        for (GLLSentence s : (ArrayList<GLLSentence>) getSentencesByID(SentenceID.GLL)) {
            if (s.isValid()) {
                return s.hasPositionMode();
            }
        }

        return false;
    }

    public PositionMode getPositionMode() {
        for (RMCSentence s : (ArrayList<RMCSentence>) getSentencesByID(SentenceID.RMC)) {
            if (s.isValid() && s.hasPositionMode()) {
                return s.getPositionMode();
            }
        }

        for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
            if (s.isValid() && s.hasPositionMode()) {
                return s.getPositionMode();
            }
        }

        for (GLLSentence s : (ArrayList<GLLSentence>) getSentencesByID(SentenceID.GLL)) {
            if (s.isValid() && s.hasPositionMode()) {
                return s.getPositionMode();
            }
        }

        throw new MissingNmeaDataException(SentenceID.RMC, SentenceID.GNS, SentenceID.GLL);
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
                            Satellite satu = sats.get(sat.getNmeaID());
                            if (satu != null) {
                                for (NmeaIDs.GnssSignal sig : sat.getSignals()) {
                                    if (satu.getSignals() != null) {
                                        if (!sig.isUnknown() && !satu.getSignals().contains(sig)) {
                                            satu.addSignal(sig);
                                        }
                                    }
                                }
                            }
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

        if (count == 0) {
            for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
                if (s.isValid()) {
                    count += s.getSatellitesUsedCount();
                }
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
    public GSASentence.Mode getOperationMode() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getOperationMode();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }

    public Float getHDOP() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getHDOP();
            }
        }

        for (GNSSentence s : (ArrayList<GNSSentence>) getSentencesByID(SentenceID.GNS)) {
            if (s.isValid()) {
                return s.getHDOP();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA, SentenceID.GNS);
    }
    public Float getPDOP() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getPDOP();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }
    public Float getVDOP() {
        for (GSASentence s : (ArrayList<GSASentence>) getSentencesByID(SentenceID.GSA)) {
            if (s.isValid()) {
                return s.getVDOP();
            }
        }

        throw new MissingNmeaDataException(SentenceID.GSA);
    }


    public static NmeaSentence parseNmea(String nmea) {
        switch (SentenceID.parse(nmea)) {
            case GGA: return new GGASentence(nmea);
            case RMC: return new RMCSentence(nmea);
            case GSA: return new GSASentence(nmea);
            case GSV: return new GSVSentence(nmea);
            case GLL: return new GLLSentence(nmea);
            case GNS: return new GNSSentence(nmea);
            case ZDA: return new ZDASentence(nmea);
            case GST: return new GSTSentence(nmea);
            default: return null;
        }
    }

    @Override
    public String toString() {
        if (isValid() && hasPosition()) {
            return String.format("[%s] Valid: True | Lat: %f | Lon: %f | Elev: %f", getFixTime(), getLatitudeSD(),
                    getLongitudeSD(), getElevation());
        } else {
            return String.format("[%s] Valid: False |%s rmc: %b | gga: %b | gsa: %b | gsv: %b", DateTime.now(),
                    hasPosition()
                            ? String.format(" (Lat: %f | Lon: %f |%s", getLatitudeSD(), getLongitudeSD(),
                            hasElevation() ? String.format(" Elev: %f) |", getElevation()) : "")
                            : "No Position |",
                    areAnyValid(SentenceID.RMC), areAnyValid(SentenceID.GGA), isValid(SentenceID.GSA),
                    isValid(SentenceID.GSV));
        }
    }

    public String toStringX() {
        if (isValid() && hasPosition()) {
            return String.format("[%s] Valid: True | Lat: %f | Lon: %f | Elev: %f | Fix: %s-%s | Sats (VTU): %d/%d/%d",
                    getFixTime(), getLatitudeSD(), getLongitudeSD(), getElevation(), getFix().toString(),
                    getFixQuality().toString(), getSatellitesInViewCount(), getTrackedSatellitesCount(),
                    getUsedSatellitesCount());
        } else {
            return String.format("[%s] Valid: False |%s rmc: %b | gga: %b | gsa: %b | gsv: %b", DateTime.now(),
                    hasPosition()
                            ? String.format(" (Lat: %f | Lon: %f |%s", getLatitudeSD(), getLongitudeSD(),
                            hasElevation() ? String.format(" Elev: %f) |", getElevation()) : "")
                            : "No Position |",
                    areAnyValid(SentenceID.RMC), areAnyValid(SentenceID.GGA), isValid(SentenceID.GSA),
                    isValid(SentenceID.GSV));
        }
    }
}
