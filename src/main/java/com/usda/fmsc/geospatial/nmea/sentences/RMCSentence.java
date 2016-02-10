package com.usda.fmsc.geospatial.nmea.sentences;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import com.usda.fmsc.geospatial.Latitude;
import com.usda.fmsc.geospatial.Longitude;
import com.usda.fmsc.geospatial.Units.*;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.PositionSentence;

public class RMCSentence extends PositionSentence  implements Serializable {
    public static final DateTimeFormatter RMCTimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS ddMMYY");

    private DateTime fixTime;
    private Status status;
    private double groundSpeed; //groud speed in knots
    private double trackAngle;  //in degrees, true
    private double magVar;
    private EastWest magVarDir;


    public RMCSentence() {
        super(SentenceID.RMC);
    }

    public RMCSentence(String nmea) {
        super(SentenceID.RMC, nmea);
    }

    @Override
    public boolean parse(String nmea) {
        if (super.parse(nmea)) {
            valid = false;
            String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

            if (tokens.length > 12) {
                try {
                    fixTime = DateTime.parse(String.format("%s %s", tokens[1], tokens[9]), RMCTimeFormatter);

                    status = Status.parse(tokens[2]);

                    latitude = Latitude.fromDecimalDMS(
                            Double.parseDouble(tokens[3]),
                            NorthSouth.parse(tokens[4])
                    );

                    longitude = Longitude.fromDecimalDMS(
                            Double.parseDouble(tokens[5]),
                            EastWest.parse(tokens[6])
                    );

                    groundSpeed = Double.parseDouble(tokens[7]);

                    trackAngle = Double.parseDouble(tokens[8]);

                    String token = tokens[10];
                    if (token != null && !token.equals("")) {
                        magVar = Double.parseDouble(token);
                        magVarDir = EastWest.parse(tokens[11]);
                    }

                    valid = true;
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            }
        }

        return valid;
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

        private Status(int value) {
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
