package com.usda.fmsc.geospatial.gnss.hemisphere.nmea.sentences;

import org.joda.time.LocalTime;

import com.usda.fmsc.geospatial.gnss.codes.GnssSystem;
import com.usda.fmsc.geospatial.gnss.nmea.SentenceFormats;
import com.usda.fmsc.geospatial.gnss.nmea.codes.Status;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class GBSSentence extends NmeaSentence {
    private LocalTime time = null;
    private double latErr, lonErr, altErr;
    private int likelyFailedSatPRN;
    private double probOfHprFault;
    private double estRangeBias;
    private double stdDevRangeBiasEst;
    private Status flag;
    private GnssSystem system;


    public GBSSentence(String nmea) {
        super(TalkerID.PSAT_GBS, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 11) {
            try {
                if (tokens[2] != null && !tokens[2].isEmpty()) {
                    try {
                        time = LocalTime.parse(tokens[2], SentenceFormats.TimeFormatter);
                    } catch (Exception e) {
                        time = LocalTime.parse(tokens[2], SentenceFormats.TimeFormatterAlt);
                    }
                }

                latErr = Double.parseDouble(tokens[3]);

                lonErr = Double.parseDouble(tokens[4]);

                altErr = Double.parseDouble(tokens[5]);

                likelyFailedSatPRN = Integer.parseInt(tokens[6]);

                probOfHprFault = Double.parseDouble(tokens[7]);

                estRangeBias = Double.parseDouble(tokens[8]);

                stdDevRangeBiasEst = Double.parseDouble(tokens[9]);

                flag = Status.parse(Integer.parseInt(tokens[10]));

                system = GnssSystem.parse(Integer.parseInt(tokens[11]));

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }


    @Override
    public SentenceID getSentenceID() {
        return SentenceID.Unknown;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }


    public LocalTime getTime() {
        return time;
    }


    public double getLatErr() {
        return latErr;
    }


    public double getLonErr() {
        return lonErr;
    }


    public double getAltErr() {
        return altErr;
    }


    public int getLikelyFailedSatPRN() {
        return likelyFailedSatPRN;
    }


    public double getProbOfHprFault() {
        return probOfHprFault;
    }


    public double getEstRangeBias() {
        return estRangeBias;
    }


    public double getStdDevRangeBiasEst() {
        return stdDevRangeBiasEst;
    }


    public Status getStatus() {
        return flag;
    }


    public GnssSystem getGnssSystem() {
        return system;
    }
}
