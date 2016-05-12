package com.usda.fmsc.geospatial.nmea;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.GeoPosition;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea.exceptions.ExcessiveStringException;
import com.usda.fmsc.geospatial.nmea.exceptions.MissingNmeaDataException;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;
import com.usda.fmsc.geospatial.nmea.sentences.GGASentence;
import com.usda.fmsc.geospatial.nmea.sentences.GSASentence;
import com.usda.fmsc.geospatial.nmea.sentences.GSVSentence;
import com.usda.fmsc.geospatial.nmea.sentences.RMCSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;
import com.usda.fmsc.geospatial.utm.UTMTools;

public class NmeaBurst implements INmeaBurst, Serializable {
    //GGA Sentence
    private GGASentence gga;

    //GSA Sentence
    private GSASentence gsa;

    //RMC Sentence
    private RMCSentence rmc;

    //GSV Sentence
    private GSVSentence gsv;


    public NmeaBurst() { }

    public NmeaBurst(NmeaBurst burst) {
        gga = burst.gga;
        gsa = burst.gsa;
        rmc = burst.rmc;
        gsv = burst.gsv;
    }

    public NmeaSentence addNmeaSentence(String sentence) {
        if (sentence == null) {
            throw new NullPointerException();
        }

        if (!isFull() && NmeaSentence.validateChecksum(sentence)) {
            switch (SentenceID.parse(sentence)) {
                case GGA: {
                    if(gga == null || !gga.isValid()) {
                        gga = new GGASentence(sentence);
                        return gga;
                    }
                    else
                        throw new ExcessiveStringException(SentenceID.GGA);
                }
                case GSA: {
                    if(gsa == null) {
                        gsa = new GSASentence(sentence);
                        return gsa;
                    } else {
                        gsa.parse(sentence);
                        return gsa;
                    }
                }
                case RMC: {
                    if(rmc == null) {
                        rmc = new RMCSentence(sentence);
                        return rmc;
                    }
                    else
                        throw new ExcessiveStringException(SentenceID.RMC);
                }
                case GSV: {
                    if (gsv == null || gsv.getTotalMessageCount() == 0) {
                        gsv = new GSVSentence(sentence);
                        return gsv;
                    }
                    else {
                        if(gsv.getMessageCount() < gsv.getTotalMessageCount()) {
                            gsv.parse(sentence);
                            return gsv;
                        } else {
                            throw new ExcessiveStringException(SentenceID.GSV);
                        }
                    }
                }
            }
        }

        return null;
    }


    public GGASentence getGGA() {
        return gga;
    }

    public GSASentence getGSA() {
        return gsa;
    }

    public GSVSentence getGSV() {
        return gsv;
    }

    public RMCSentence getRMC() {
        return rmc;
    }

    public boolean isValid() {
        return (gga != null && gsa != null && rmc != null && gsv != null &&
                gga.isValid() && gsa.isValid() && rmc.isValid() && gsv.isValid());
    }

    @Override
    public boolean isValid(SentenceID id) {
        switch (id) {
            case GGA: return gga != null && gga.isValid();
            case GSA: return gsa != null && gsa.isValid();
            case GSV: return gsv != null && gsv.isValid();
            case RMC: return rmc != null && rmc.isValid();
        }

        return false;
    }

    public boolean isFull() {
        return (gga != null && gsa != null && rmc != null && gsv != null &&
                gsv.hasAllMessages());
    }

    private boolean checkValid(NmeaSentence sentence) {
        return sentence != null && sentence.isValid();
    }


    public DateTime getFixTime() {
        if (checkValid(rmc)) {
            return rmc.getFixTime();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public double getMagVar() {
        if (checkValid(rmc)) {
            return rmc.getMagVar();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public EastWest getMagVarDir() {
        if (checkValid(rmc)) {
            return rmc.getMagVarDir();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public double getTrackAngle() {
        if (checkValid(rmc)) {
            return rmc.getTrackAngle();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }

    public double getGroundSpeed() {
        if (checkValid(rmc)) {
            return rmc.getGroundSpeed();
        } else {
            throw new MissingNmeaDataException(SentenceID.RMC);
        }
    }


    public GeoPosition getPosition() {
        if (checkValid(gga)) {
            return gga.getPosition();
        } else if (checkValid(rmc)) {
            return rmc.getPosition();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public boolean hasPosition() {
        return checkValid(gga) && gga.hasPosition() || checkValid(rmc) && rmc.hasPosition();
    }

    public double getLatitude() {
        if (checkValid(gga)) {
            return gga.getLatitude().toSignedDecimal();
        } else if (checkValid(rmc)) {
            return rmc.getLatitude().toSignedDecimal();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public NorthSouth getLatDir() {
        if (checkValid(gga)) {
            return gga.getLatDir();
        } else if (checkValid(rmc)) {
            return rmc.getLatDir();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public double getLongitude() {
        if (checkValid(gga)) {
            return gga.getLongitude().toSignedDecimal();
        } else if (checkValid(rmc)) {
            return rmc.getLongitude().toSignedDecimal();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public EastWest getLonDir() {
        if (checkValid(gga)) {
            return gga.getLonDir();
        } else if (checkValid(rmc)) {
            return rmc.getLonDir();
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }


    public double getElevation() {
        if (checkValid(gga)) {
            return gga.getElevation();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public UomElevation getUomElevation() {
        if (checkValid(gga)) {
            return gga.getUomElevation();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }


    public UTMCoords getTrueUTM() {
        if (checkValid(gga)) {
            return UTMTools.convertLatLonToUTM(gga.getPosition());
        } else if (checkValid(rmc)) {
            return UTMTools.convertLatLonToUTM(rmc.getPosition());
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }

    public UTMCoords getUTM(int utmZone) {
        if (checkValid(gga)) {
            return UTMTools.convertLatLonToUTM(gga.getPosition(), utmZone);
        } else if (checkValid(rmc)) {
            return UTMTools.convertLatLonToUTM(rmc.getPosition(), utmZone);
        }

        throw new MissingNmeaDataException("Missing RMC and GGA Sentences");
    }


    public double getHorizDilution() {
        if (checkValid(gga)) {
            return gga.getHorizDilution();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public double getGeoidHeight() {
        if (checkValid(gga)) {
            return gga.getGeoidHeight();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public UomElevation getGeoUom() {
        if (checkValid(gga)) {
            return gga.getGeoUom();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public GGASentence.GpsFixType getFixQuality() {
        if (checkValid(gga)) {
            return gga.getFixQuality();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public int getTrackedSatellitesCount() {
        if (checkValid(gga)) {
            return gga.getTrackedSatellitesCount();
        } else {
            throw new MissingNmeaDataException(SentenceID.GGA);
        }
    }

    public List<Satellite> getSatellitesInView() {
        if (checkValid(gsv)) {
            return gsv.getSatellites();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSV);
        }
    }

    public int getSatellitesInViewCount() {
        if (checkValid(gsv)) {
            return gsv.getSatellitesInViewCount();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSV);
        }
    }

    public List<Integer> getUsedSatelliteIDs() {
        if (checkValid(gsa)) {
            return gsa.getSatellitesUsed();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public int getUsedSatellitesCount() {
        if (checkValid(gsa)) {
            return gsa.getSatellitesUsedCount();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }


    public GSASentence.Fix getFix() {
        if (checkValid(gsa)) {
            return gsa.getFix();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public GSASentence.Mode getMode() {
        if (checkValid(gsa)) {
            return gsa.getMode();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public double getHDOP() {
        if (checkValid(gsa)) {
            return gsa.getHDOP();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public double getPDOP() {
        if (checkValid(gsa)) {
            return gsa.getPDOP();
        } else {
            throw new MissingNmeaDataException(SentenceID.GSA);
        }
    }

    public double getVDOP() {
        if (checkValid(gsa)) {
            return gsa.getVDOP();
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
                    hasPosition() ? String.format(" (Lat: %f | Lon: %f | Elev: %f) |", getLatitude(), getLatitude(), getElevation()) :
                            "No Position |",
                    rmc.isValid(), gga.isValid(), gsa.isValid(), gsv.isValid());
        }
    }
}

