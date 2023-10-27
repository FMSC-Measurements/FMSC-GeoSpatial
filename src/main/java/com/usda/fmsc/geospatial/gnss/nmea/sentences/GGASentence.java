package com.usda.fmsc.geospatial.gnss.nmea.sentences;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.codes.EastWest;
import com.usda.fmsc.geospatial.codes.NorthSouth;
import com.usda.fmsc.geospatial.codes.UomElevation;
import com.usda.fmsc.geospatial.gnss.codes.GnssFixQuality;
import com.usda.fmsc.geospatial.gnss.nmea.SentenceFormats;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.base.PositionSentence;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;

import org.joda.time.LocalTime;

public class GGASentence extends PositionSentence {
    private LocalTime fixTime;
    private GnssFixQuality fixQuality;
    private int trackedSatellites;
    private double horizDilution;
    private double geoidHeight;
    private UomElevation geoUom;
    private Double diffAge;
    private Integer diffID;

    public GGASentence(TalkerID talkerID, String nmea) {
        super(talkerID, nmea);
    }

    @Override
    public boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 13 && tokens[2].length() > 0) {
            try {
                try {
                    fixTime = LocalTime.parse(tokens[1], SentenceFormats.TimeFormatter);
                } catch (Exception e) {
                    fixTime = LocalTime.parse(tokens[1], SentenceFormats.TimeFormatterAlt);
                }

                position = Position.fromDecimalDms(
                        Double.parseDouble(tokens[2]), NorthSouth.parse(tokens[3]),
                        Double.parseDouble(tokens[4]), EastWest.parse(tokens[5]),
                        Double.parseDouble(tokens[9]), UomElevation.parse(tokens[10]));

                fixQuality = GnssFixQuality.parse(tokens[6]);

                trackedSatellites = Integer.parseInt(tokens[7]);

                horizDilution = Double.parseDouble(tokens[8]);

                geoidHeight = Double.parseDouble(tokens[11]);
                geoUom = UomElevation.parse(tokens[12]);

                if (tokens.length > 14 && !tokens[13].isEmpty()) {
                    diffAge = Double.parseDouble(tokens[13]);
                }

                if (tokens.length > 15 && !tokens[14].isEmpty()) {
                    diffID = Integer.parseInt(tokens[14]);
                }

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GGA;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }

    public LocalTime getFixTime() {
        return fixTime;
    }

    public GnssFixQuality getFixQuality() {
        return fixQuality;
    }

    public int getTrackedSatellitesCount() {
        return trackedSatellites;
    }

    public double getHorizDilution() {
        return horizDilution;
    }

    public double getGeoidHeight() {
        return geoidHeight;
    }

    public UomElevation getGeoUom() {
        return geoUom;
    }

    public Double getSatDiffAge() {
        return diffAge;
    }

    public Integer getSatDiffID() {
        return diffID;
    }

}