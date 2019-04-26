package com.usda.fmsc.geospatial.nmea.sentences;

import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.Latitude;
import com.usda.fmsc.geospatial.Longitude;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.PositionSentence;

public class RMCSentence extends PositionSentence  implements Serializable {
    public static final DateTimeFormatter RMCTimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS ddMMYY");
    public static final DateTimeFormatter RMCTimeFormatterAlt = DateTimeFormat.forPattern("HHmmss ddMMYY");

    private DateTime fixTime;
    private Status status;
    private double groundSpeed; //groud speed in knots
    private double trackAngle;  //in degrees, true
    private double magVar;
    private EastWest magVarDir;


    public RMCSentence() { }

    public RMCSentence(String nmea) {
        super(nmea);
    }

    @Override
    public boolean parse(String nmea) {
        if (super.parse(nmea)) {
            valid = false;
            String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

            if (tokens.length > 12 && tokens[1].length() > 0) {
                try {
                    String timeString = String.format("%s %s", tokens[1], tokens[9]);

                    try {
                        fixTime = DateTime.parse(timeString, RMCTimeFormatter);
                    } catch (Exception e) {
                        fixTime = DateTime.parse(timeString, RMCTimeFormatterAlt);
                    }

                    status = Status.parse(tokens[2]);

                    latitude = Latitude.fromDecimalDMS(
                            Double.parseDouble(tokens[3]),
                            NorthSouth.parse(tokens[4])
                    );

                    longitude = Longitude.fromDecimalDMS(
                            Double.parseDouble(tokens[5]),
                            EastWest.parse(tokens[6])
                    );

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

                    valid = true;
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            }
        }

        return valid;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.RMC;
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


    public enum Status {
        Active(0),
        Void(1);

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
                case "active": return Active;
                case "v":
                case "void": return Void;
                default: throw new IllegalArgumentException("Invalid Status Name: " + str);
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case Active: return "Active";
                case Void: return "Void";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
