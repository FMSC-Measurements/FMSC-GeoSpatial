package com.usda.fmsc.geospatial.nmea41.sentences;

import android.text.TextUtils;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position2;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
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
    private Status posMode;
    private Status navStatus;


    public RMCSentence() { }

    public RMCSentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

        if (tokens.length > 12 && tokens[1].length() > 0) {
            try {
                String timeString = String.format("%s %s", tokens[1], tokens[9]);

                try {
                    fixTime = DateTime.parse(timeString, SentenceFormats.RMCTimeFormatter);
                } catch (Exception e) {
                    fixTime = DateTime.parse(timeString, SentenceFormats.RMCTimeFormatterAlt);
                }

                status = Status.parse(tokens[2]);

                position = new Position2(
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

                if (tokens.length > 13) {
                    token = tokens[12];
                    if (!TextUtils.isEmpty(token)) {
                        posMode = Status.parse(tokens[12]);
                    }
                }

                if (tokens.length > 14) {
                    token = tokens[13];
                    if (!TextUtils.isEmpty(token)) {
                        navStatus = Status.parse(tokens[13]);
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

    public double getGroundSpeed() {
        return groundSpeed;
    }

    public double getTrackAngle() {
        return trackAngle;
    }


    public double getMagVar() {
        return magVar;
    }

    public EastWest getMagVarDir() {
        return magVarDir;
    }

    public boolean hasMagVar() {
        return magVarDir != null;
    }


    public boolean hasPosMode() {
        return posMode != null;
    }

    public Status getPosMode() {
        return posMode;
    }


    public boolean hasNavStatus() {
        return navStatus != null;
    }

    public Status getNavStatus() {
        return navStatus;
    }


    public enum Status {
        Valid(0), //Active - old
        Warning(1); //Void - old

        private final int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status parse(int id) {
            Status[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid Status id: " + id);
        }

        public static Status parse(String str) {
            switch(str.toLowerCase()) {
                case "a":
                case "valid":
                case "active": return Valid;
                case "v":
                case"warning":
                case "void": return Warning;
                default: throw new IllegalArgumentException("Invalid Status Name: " + str);
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case Valid: return "Valid";
                case Warning: return "Warning";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
