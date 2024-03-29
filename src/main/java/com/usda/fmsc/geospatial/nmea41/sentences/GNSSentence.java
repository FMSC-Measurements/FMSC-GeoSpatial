package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
import com.usda.fmsc.geospatial.nmea41.codes.NavigationStatus;
import com.usda.fmsc.geospatial.nmea41.codes.PositionMode;
import com.usda.fmsc.geospatial.nmea41.sentences.base.PositionSentence;

import org.joda.time.LocalTime;

public class GNSSentence extends PositionSentence {
    private LocalTime fixTime;
    private int numberOfSatsUsed;
    private float hdop;
    private float geoid;
    private Float diffAge;
    private Integer diffStation;
    private NavigationStatus status;


    public GNSSentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 12 && tokens[2].length() > 0) {
            try {
                try {
                    fixTime = LocalTime.parse(tokens[1], SentenceFormats.TimeFormatter);
                } catch (Exception e) {
                    fixTime = LocalTime.parse(tokens[1], SentenceFormats.TimeFormatterAlt);
                }

                position = Position.fromDecimalDms(
                        Double.parseDouble(tokens[2]), NorthSouth.parse(tokens[3]),
                        Double.parseDouble(tokens[4]), EastWest.parse(tokens[5]),
                        Double.parseDouble(tokens[9]), UomElevation.Meters);

                if (tokens[6] != null) {
                    positionMode = PositionMode.parse(tokens[6]);
                }

                numberOfSatsUsed = Integer.parseInt(tokens[7]);
                hdop = Float.parseFloat(tokens[8]);
                geoid = Float.parseFloat(tokens[10]);

                if (!tokens[11].isEmpty()) {
                    diffAge = Float.parseFloat(tokens[11]);
                }

                if (!tokens[12].isEmpty()) {
                    diffStation = Integer.parseInt(tokens[12]);
                }

                if (tokens.length > 13 && !tokens[13].isEmpty()) {
                    status = NavigationStatus.parse(tokens[13]);
                }

                valid = true;
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        return valid;
    }


    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GNS;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }

    public LocalTime getFixTime() {
        return fixTime;
    }

    public int getSatellitesUsedCount() {
        return numberOfSatsUsed;
    }

    public float getHDOP() {
        return hdop;
    }

    public float getGeoid() {
        return geoid;
    }

    public Float getDiffAge() {
        return diffAge;
    }

    public Integer getDiffStation() {
        return diffStation;
    }

    public NavigationStatus getStatus() {
        return status;
    }

}
