package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
import com.usda.fmsc.geospatial.nmea41.sentences.base.PositionSentence;

import org.joda.time.LocalTime;

public class GLLSentence extends PositionSentence {
    private LocalTime fixTime;
    private Status status;
    private Mode mode;


    public GLLSentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 7 && tokens[1].length() > 0) {
            try {
                try {
                    fixTime = LocalTime.parse(tokens[5], SentenceFormats.TimeFormatter);
                } catch (Exception e) {
                    fixTime = LocalTime.parse(tokens[5], SentenceFormats.TimeFormatterAlt);
                }

                position = Position.fromDecimalDms(
                        Double.parseDouble(tokens[1]), NorthSouth.parse(tokens[2]),
                        Double.parseDouble(tokens[3]), EastWest.parse(tokens[4]));

                status = Status.parse(tokens[6]);

                mode = Mode.parse(tokens[7]);

                valid = true;
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        return valid;
    }


    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GLL;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }

    public LocalTime getFixTime() {
        return fixTime;
    }

    public Status getStatus() {
        return status;
    }

    public Mode getMode() {
        return mode;
    }


    public enum Mode {
        Unknown(0),
        Auto(1),
        Differential(2),
        Estimated(3),
        Manual(4),
        Simulation(5),
        RTK(6),
        FloatRTK(7),
        NotValid(8);

        private final int value;

        Mode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Mode parse(int id) {
            Mode[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid GpsFixType id: " + id);
        }

        public static Mode parse(String value) {
            switch(value.toLowerCase()) {
                case "a":
                case "auto":
                case "autonomous": return Auto;
                case "d":
                case "diff":
                case "differential": return Differential;
                case "e":
                case "estimated":
                case "deadreckoning":
                case "dead reckoning": return Estimated;
                case "m":
                case "man":
                case "manual": return Manual;
                case "s":
                case "simulated":
                case "simulation": return Simulation;
                case "n":
                case "notvalid":
                case "not valid": return NotValid;
                case "f":
                case "float":
                case "floatrtk":
                case "float rtk": return FloatRTK;
                case "r":
                case "rtk": return RTK;
                default: return Unknown;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case Auto: return "Auto";
                case Differential: return "Differential";
                case NotValid: return "Not Valid";
                case Unknown: return "Unknown";
                case RTK: return "RTK";
                case FloatRTK: return "Float RTK";
                case Estimated: return "Estimated";
                case Manual: return "Manual";
                case Simulation: return "Simulation";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringF() {
            switch(this) {
                case Auto: return "A (Auto)";
                case Differential: return "D (Differential)";
                case NotValid: return "N (Not Valid)";
                case Unknown: return "Unknown";
                case RTK: return "R (RTK)";
                case FloatRTK: return "F (Float RTK)";
                case Estimated: return "E (Estimated)";
                case Manual: return "M (Manual)";
                case Simulation: return "S (Simulation)";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
