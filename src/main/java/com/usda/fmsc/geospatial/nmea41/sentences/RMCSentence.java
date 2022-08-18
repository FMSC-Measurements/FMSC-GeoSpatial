package com.usda.fmsc.geospatial.nmea41.sentences;

import android.text.TextUtils;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
import com.usda.fmsc.geospatial.nmea41.codes.NavigationStatus;
import com.usda.fmsc.geospatial.nmea41.codes.PositionMode;
import com.usda.fmsc.geospatial.nmea41.codes.Status;
import com.usda.fmsc.geospatial.nmea41.sentences.base.PositionSentence;

import org.joda.time.DateTime;

import java.io.Serializable;

public class RMCSentence extends PositionSentence implements Serializable {
    private DateTime fixTime;
    private Status status;
    private Double groundSpeed; //ground speed in knots
    private Double trackAngle;  //in degrees, true
    private Double magVar;
    private EastWest magVarDir;
    private NavigationStatus navStatus;


    public RMCSentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 11 && tokens[3].length() > 0) {
            try {
                String timeString = String.format("%s %s", tokens[1], tokens[9]);

                try {
                    fixTime = DateTime.parse(timeString, SentenceFormats.DateTimeFormatter);
                } catch (Exception e) {
                    fixTime = DateTime.parse(timeString, SentenceFormats.DateTimeFormatterAlt);
                }

                status = Status.parse(tokens[2]);

                position = Position.fromDecimalDms(
                        Double.parseDouble(tokens[3]), NorthSouth.parse(tokens[4]),
                        Double.parseDouble(tokens[5]), EastWest.parse(tokens[6]));

                String token = tokens[7];
                if (!TextUtils.isEmpty(token))
                    groundSpeed = Double.parseDouble(token);

                token = tokens[8];
                if (!TextUtils.isEmpty(token))
                    trackAngle = Double.parseDouble(token);

                token = tokens[10];
                if (!TextUtils.isEmpty(token)) {
                    magVar = Double.parseDouble(token);
                }

                token = tokens[11];
                if (!TextUtils.isEmpty(token)) {
                    magVarDir = EastWest.parse(token);
                }

                if (tokens.length > 12) {
                    token = tokens[12];
                    if (!TextUtils.isEmpty(token)) {
                        positionMode = PositionMode.parse(tokens[12]);
                    }
                }

                // NMEA 4.1 Only
                if (tokens.length > 13) {
                    token = tokens[13];
                    if (!TextUtils.isEmpty(token)) {
                        navStatus = NavigationStatus.parse(tokens[13]);
                    }
                }

                valid = true;
            } catch (Exception ex) {
                //
            }
        }

        return valid;
    }


    @Override
    public SentenceID getSentenceID() {
        return SentenceID.RMC;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }


    public DateTime getFixTime() {
        return fixTime;
    }

    public Status getStatus() {
        return status;
    }

    public Double getGroundSpeed() {
        return groundSpeed;
    }

    public Double getTrackAngle() {
        return trackAngle;
    }


    public Double getMagVar() {
        return magVar;
    }

    public EastWest getMagVarDir() {
        return magVarDir;
    }

    public boolean hasMagVar() {
        return magVarDir != null;
    }

    public boolean hasNavStatus() {
        return navStatus != null;
    }

    public NavigationStatus getNavStatus() {
        return navStatus;
    }
}
