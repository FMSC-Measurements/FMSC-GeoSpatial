package com.usda.fmsc.geospatial.nmea;

import android.annotation.SuppressLint;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.GeoPosition;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.TalkerID;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea.exceptions.ExcessiveStringException;
import com.usda.fmsc.geospatial.nmea.exceptions.MissingNmeaDataException;
import com.usda.fmsc.geospatial.nmea.sentences.GGASentence;
import com.usda.fmsc.geospatial.nmea.sentences.GSASentence;
import com.usda.fmsc.geospatial.nmea.sentences.GSVSentence;
import com.usda.fmsc.geospatial.nmea.sentences.RMCSentence;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;
import com.usda.fmsc.geospatial.nmea.sentences.base.PositionSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;
import com.usda.fmsc.geospatial.utm.UTMTools;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("DefaultLocale")
@SuppressWarnings({"WeakerAccess", "unused"})
public class NmeaBurstEx implements INmeaBurst, Serializable {
    private static TalkerID[] priorityIds = new TalkerID[] {
            TalkerID.GP,
            TalkerID.GN,
            TalkerID.GL,
            TalkerID.GA,
            TalkerID.GB,
            TalkerID.BD,
            TalkerID.QZ,
            TalkerID.LC,
            TalkerID.II,
            TalkerID.IN,
            TalkerID.EC,
            TalkerID.CD
    };
    
    //GGA Sentence
    private HashMap<TalkerID, GGASentence> gga = new HashMap<>();

    //GSA Sentence
    private HashMap<TalkerID, GSASentence> gsa = new HashMap<>();

    //RMC Sentence
    private HashMap<TalkerID, RMCSentence> rmc = new HashMap<>();

    //GSV Sentence
    private HashMap<TalkerID, GSVSentence> gsv = new HashMap<>();

    private ArrayList<NmeaSentence> allSenteneces = new ArrayList<>();


    public NmeaBurstEx() { }

    public NmeaBurstEx(NmeaBurstEx burst) {
        gga = burst.gga;
        gsa = burst.gsa;
        rmc = burst.rmc;
        gsv = burst.gsv;
        allSenteneces = burst.allSenteneces;
    }

    public NmeaSentence addNmeaSentence(String sentence) {
        if (sentence == null) {
            throw new NullPointerException();
        }

        if (!isFull() && NmeaSentence.validateChecksum(sentence)) {
            TalkerID talkerID = TalkerID.parse(sentence);

            switch (SentenceID.parse(sentence)) {
                case GGA: {
                    if (!gga.containsKey(talkerID)) {
                        GGASentence ggaSentence = new GGASentence(sentence);
                        gga.put(talkerID, ggaSentence);
                        allSenteneces.add(ggaSentence);
                        return ggaSentence;
                    } else {
                        throw new ExcessiveStringException(SentenceID.GGA);
                    }
                }
                case GSA: {
                    GSASentence gsaSentence;
                    if (!gsa.containsKey(talkerID)) {
                        gsaSentence = new GSASentence(sentence);
                        gsa.put(talkerID, gsaSentence);
                        allSenteneces.add(gsaSentence);
                        return gsaSentence;
                    } else {
                        gsaSentence = gsa.get(talkerID);
                        gsaSentence.parse(sentence);
                        return gsaSentence;
                    }
                }
                case RMC: {
                    if (!rmc.containsKey(talkerID)) {
                        RMCSentence rmcSentence = new RMCSentence(sentence);
                        rmc.put(talkerID, rmcSentence);
                        allSenteneces.add(rmcSentence);
                        return rmcSentence;
                    } else {
                        throw new ExcessiveStringException(SentenceID.RMC);
                    }
                }
                case GSV: {
                    GSVSentence gsvSentence;
                    if (!gsv.containsKey(talkerID) || gsv.get(talkerID).getTotalMessageCount() == 0) {
                        gsvSentence = new GSVSentence(sentence);
                        gsv.put(talkerID, gsvSentence);
                        allSenteneces.add(gsvSentence);
                        return gsvSentence;
                    } else {
                        gsvSentence = gsv.get(talkerID);
                        if (gsvSentence.getMessageCount() < gsvSentence.getTotalMessageCount()) {
                            gsvSentence.parse(sentence);
                            return gsvSentence;
                        } else {
                            throw new ExcessiveStringException(SentenceID.GSV);
                        }
                    }
                }
            }
        }

        return null;
    }


    public HashMap<TalkerID, GGASentence> getGGA() {
        return gga;
    }

    public HashMap<TalkerID, GSASentence> getGSA() {
        return gsa;
    }

    public HashMap<TalkerID, GSVSentence> getGSV() {
        return gsv;
    }

    public HashMap<TalkerID, RMCSentence> getRMC() {
        return rmc;
    }


    public NmeaSentence getSentenceByPriority(SentenceID id) {
        switch (id) {
            case GGA: {
                for (TalkerID tid : priorityIds) {
                    if (gga.containsKey(tid)) {
                        return gga.get(tid);
                    }
                }
                break;
            }
            case GSA: {
                for (TalkerID tid : priorityIds) {
                    if (gsa.containsKey(tid)) {
                        return gsa.get(tid);
                    }
                }
                break;
            }
            case RMC: {
                for (TalkerID tid : priorityIds) {
                    if (rmc.containsKey(tid)) {
                        return rmc.get(tid);
                    }
                }
                break;
            }
            case GSV: {
                for (TalkerID tid : priorityIds) {
                    if (gsv.containsKey(tid)) {
                        return gsv.get(tid);
                    }
                }
                break;
            }
        }

        throw new RuntimeException("No Sentence found");
    }


    public boolean isValid() {
        for (NmeaSentence sentence : allSenteneces) {
            if (!sentence.isValid())
                return false;
        }

        return true;
    }

    public boolean isValid(SentenceID id) {
        switch (id) {
            case GGA:
                if (gga.size() < 1)
                    return false;

                for (GGASentence s : gga.values()) {
                    if (!s.isValid()) {
                        return false;
                    }
                }
                break;
            case GSA:
                if (gsa.size() < 1)
                    return false;

                for (GSASentence s : gsa.values()) {
                    if (!s.isValid()) {
                        return false;
                    }
                }
                break;
            case GSV:
                if (gsv.size() < 1)
                    return false;

                for (GSVSentence s : gsv.values()) {
                    if (!s.isValid()) {
                        return false;
                    }
                }
                break;
            case RMC:
                if (rmc.size() < 1)
                    return false;

                for (RMCSentence s : rmc.values()) {
                    if (!s.isValid()) {
                        return false;
                    }
                }
                break;
        }

        return true;
    }

    public boolean isFull() {
        return (rmc.size() > 0 && gsa.size() > 0 && gga.size() > 0 && gsvIsFull());
    }

    private boolean gsvIsFull() {
        boolean isFull = true;
        int msgCount = 0, firstTotalMsgCount = 0;

        for (GSVSentence s : gsv.values()) {
            if (!s.hasAllMessages())
                isFull = false;

            if (firstTotalMsgCount < 1)
                firstTotalMsgCount = s.getTotalMessageCount();

            msgCount += s.getMessageCount();
        }

        return isFull || (msgCount > 0 && msgCount == firstTotalMsgCount);
    }


    public DateTime getFixTime() {
        if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getFixTime();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public double getMagVar() {
        if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getMagVar();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public EastWest getMagVarDir() {
        if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getMagVarDir();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public double getTrackAngle() {
        if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getTrackAngle();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public double getGroundSpeed() {
        if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getGroundSpeed();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }


    public GeoPosition getPosition() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getPosition();
        } else if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getPosition();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public boolean hasPosition() {
        return isValid(SentenceID.GGA) && ((GGASentence)getSentenceByPriority(SentenceID.GGA)).hasPosition() ||
                isValid(SentenceID.RMC) && ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).hasPosition();
    }

    public double getLatitude() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getLatitude().toSignedDecimal();
        } else if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getLatitude().toSignedDecimal();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public NorthSouth getLatDir() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getLatDir();
        } else if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getLatDir();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public double getLongitude() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getLongitude().toSignedDecimal();
        } else if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getLongitude().toSignedDecimal();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public EastWest getLonDir() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getLonDir();
        } else if (isValid(SentenceID.RMC)) {
            return ((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getLonDir();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }


    public double getElevation() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getElevation();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public boolean hasElevation() {
        return isValid(SentenceID.GGA) && ((GGASentence)getSentenceByPriority(SentenceID.GGA)).hasElevation();
    }

    public UomElevation getUomElevation() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getUomElevation();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }


    public UTMCoords getTrueUTM() {
        if (isValid(SentenceID.GGA)) {
            return UTMTools.convertLatLonToUTM(((PositionSentence)getSentenceByPriority(SentenceID.GGA)).getPosition());
        } else if (isValid(SentenceID.RMC)) {
            return UTMTools.convertLatLonToUTM(((PositionSentence)getSentenceByPriority(SentenceID.RMC)).getPosition());
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public UTMCoords getUTM(int utmZone) {
        if (isValid(SentenceID.GGA)) {
            return UTMTools.convertLatLonToUTM(((GGASentence)getSentenceByPriority(SentenceID.GGA)).getPosition(), utmZone);
        } else if (isValid(SentenceID.RMC)) {
            return UTMTools.convertLatLonToUTM(((RMCSentence)getSentenceByPriority(SentenceID.RMC)).getPosition(), utmZone);
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }


    public double getHorizDilution() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getHorizDilution();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public double getGeoidHeight() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getGeoidHeight();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public UomElevation getGeoUom() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getGeoUom();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public GGASentence.GpsFixType getFixQuality() {
        if (isValid(SentenceID.GGA)) {
            return ((GGASentence)getSentenceByPriority(SentenceID.GGA)).getFixQuality();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public int getTrackedSatellitesCount() {
        if (isValid(SentenceID.GGA)) {
            int count = 0;

            for (GGASentence sentence : gga.values()) {
                count += sentence.getTrackedSatellitesCount();
            }

            return count;
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public ArrayList<Satellite> getSatellitesInView() {
        if (isValid(SentenceID.GSV)) {
            ArrayList<Satellite> sats = new ArrayList<>();

            for (GSVSentence sentence : gsv.values()) {
                sats.addAll(sentence.getSatellites());
            }

            return sats;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSV);
        }
    }

    public int getSatellitesInViewCount() {
        if (isValid(SentenceID.GSV)) {
            int count = 0;

            for (GSVSentence sentence : gsv.values()) {
                count += sentence.getSatellitesInViewCount();
            }

            return count;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSV);
        }
    }

    public ArrayList<Integer> getUsedSatelliteIDs() {
        if (isValid(SentenceID.GSA)) {
            ArrayList<Integer> ids = new ArrayList<>();

            for (GSASentence sentence : gsa.values()) {
                ids.addAll(sentence.getSatellitesUsed());
            }

            return ids;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public int getUsedSatellitesCount() {
        if (isValid(SentenceID.GSA)) {
            int count = 0;

            for (GSASentence sentence : gsa.values()) {
                count += sentence.getSatellitesUsedCount();
            }

            return count;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }


    public GSASentence.Fix getFix() {
        if (isValid(SentenceID.GSA)) {
            return ((GSASentence)getSentenceByPriority(SentenceID.GSA)).getFix();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public GSASentence.Mode getMode() {
        if (isValid(SentenceID.GSA)) {
            return ((GSASentence)getSentenceByPriority(SentenceID.GSA)).getMode();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public float getHDOP() {
        if (isValid(SentenceID.GSA)) {
            GSASentence gsa = ((GSASentence)getSentenceByPriority(SentenceID.GSA));
            return gsa.getHDOP() != null ? gsa.getHDOP() : 0;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public float getPDOP() {
        if (isValid(SentenceID.GSA)) {
            GSASentence gsa = ((GSASentence)getSentenceByPriority(SentenceID.GSA));
            return gsa.getPDOP() != null ? gsa.getPDOP() : 0;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public float getVDOP() {
        if (isValid(SentenceID.GSA)) {
            GSASentence gsa = ((GSASentence)getSentenceByPriority(SentenceID.GSA));
            return gsa.getVDOP() != null ? gsa.getVDOP() : 0;
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    @Override
    public String toString() {
        if (isValid()) {
            return String.format("[%s] Valid: True | Lat: %f | Lon: %f | Elev: %f",
                    getFixTime(), getLatitude(), getLatitude(), getElevation());
        } else {
            return String.format("[%s] Valid: False |%s rmc: %b | gga: %b | gsa: %b | gsv: %b",
                    DateTime.now(),
                    hasPosition() ? String.format(" (Lat: %f | Lon: %f |%s", getLatitude(), getLatitude(),
                            hasElevation() ? String.format(" Elev: %f) |", getElevation()) : "") : "No Position |",
                    isValid(SentenceID.RMC), isValid(SentenceID.GGA), isValid(SentenceID.GSA), isValid(SentenceID.GSV));
        }
    }
}
