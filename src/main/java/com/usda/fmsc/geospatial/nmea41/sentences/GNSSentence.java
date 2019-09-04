package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
import com.usda.fmsc.geospatial.nmea41.sentences.base.PositionSentence;

import org.joda.time.LocalTime;

public class GNSSentence extends PositionSentence {
    private LocalTime fixTime;
    private int numberOfSatsUsed;
    private float hdop;
    private float geoid;
    private Float diffAge;
    private Integer diffStation;
    private Status status;


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
                    status = Status.parse(tokens[13]);
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

    public Status getStatus() {
        return status;
    }

    public enum Status {
        Unknown(0),
        Safe(1),
        Caution(2),
        Unsafe(3),
        NotValid(4);

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

        public static Status parse(String value) {
            switch(value.toLowerCase()) {
                case "unknown":
                case "0": return Unknown;
                case "s":
                case "safe":
                case "1": return Safe;
                case "c":
                case "caution":
                case "2": return Caution;
                case "u":
                case "unsafe":
                case "3": return Unsafe;
                case "v":
                case "not valid":
                case "notvalid":
                case "4": return NotValid;
                default: throw new IllegalArgumentException("Invalid Status Name: " + value);
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case Unknown: return "Unknown";
                case Safe: return "Safe";
                case Caution: return "Caution";
                case Unsafe: return "Unsafe";
                case NotValid: return "Not Valid";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringF() {
            switch(this) {
                case Unknown: return "0 (Unknown)";
                case Safe: return "1 (Safe)";
                case Caution: return "2 (Caution)";
                case Unsafe: return "3 (Unsafe)";
                case NotValid: return "4 (Not Valid)";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
