package com.usda.fmsc.geospatial.nmea.sentences;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.Latitude;
import com.usda.fmsc.geospatial.Longitude;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.PositionSentence;

public class GGASentence extends PositionSentence  implements Serializable {
    public static final DateTimeFormatter GGATimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS");
    public static final DateTimeFormatter GGATimeFormatterAlt = DateTimeFormat.forPattern("HHmmss");

    private LocalTime fixTime;
    private GpsFixType fixQuality;
    private int trackedSatellites;
    private double horizDilution;
    private double geoidHeight;
    private UomElevation geoUom;


    public GGASentence() { }

    public GGASentence(String nmea) {
        super(nmea);
    }

    @Override
    public boolean parse(String nmea) {
        if (super.parse(nmea)) {
            valid = false;
            String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

            if (tokens.length > 14 && tokens[1].length() > 0) {
                try {
                    try {
                        fixTime = LocalTime.parse(tokens[1], GGATimeFormatter);
                    } catch (Exception e) {
                        fixTime = LocalTime.parse(tokens[1], GGATimeFormatterAlt);
                    }

                    latitude = Latitude.fromDecimalDMS(
                            Double.parseDouble(tokens[2]),
                            NorthSouth.parse(tokens[3])
                    );

                    longitude = Longitude.fromDecimalDMS(
                            Double.parseDouble(tokens[4]),
                            EastWest.parse(tokens[5])
                    );

                    fixQuality = GpsFixType.parse(tokens[6]);

                    trackedSatellites = Integer.parseInt(tokens[7]);

                    horizDilution = Double.parseDouble(tokens[8]);

                    elevation = Double.parseDouble(tokens[9]);
                    uomElevation = UomElevation.parse(tokens[10]);

                    geoidHeight = Double.parseDouble(tokens[11]);
                    geoUom = UomElevation.parse(tokens[12]);

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
        return SentenceID.GGA;
    }


    public LocalTime getFixTime() {
        return fixTime;
    }

    public GpsFixType getFixQuality() {
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


    public enum GpsFixType {
        NoFix(0),
        GPS(1),
        DGPS(2),
        PPS(3),
        FloatRTK(5),
        RTK(4),
        Estimated(6),
        Manual(7),
        Simulation(8);

        private final int value;

        GpsFixType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static GpsFixType parse(int id) {
            GpsFixType[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid GpsFixType id: " + id);
        }

        public static GpsFixType parse(String value) {
            switch(value.toLowerCase()) {
                case "nofix":
                case "no fix":
                case "0": return NoFix;
                case "gps":
                case "1": return GPS;
                case "dgps":
                case "2": return DGPS;
                case "pps":
                case "3": return PPS;
                case "realtime":
                case "realtimekinematic":
                case "4": return RTK;
                case "float":
                case "floatrtk":
                case "5": return FloatRTK;
                case "estimate":
                case "estimated":
                case "6": return Estimated;
                case "manual":
                case "7": return Manual;
                case "simulate":
                case "simulation":
                case "8": return Simulation;
                default: throw new IllegalArgumentException("Invalid GpsFixType Name: " + value);
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case NoFix: return "No Fix";
                case GPS: return "GPS";
                case DGPS: return "DGPS";
                case PPS: return "PPS";
                case RTK: return "RTK";
                case FloatRTK: return "Float RTK";
                case Estimated: return "Estimated";
                case Manual: return "Manual";
                case Simulation: return "Simulation";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringX() {
            switch(this) {
                case NoFix: return "No Fix";
                case GPS: return "GPS";
                case DGPS: return "GPS (DIFF)";
                case PPS: return "PPS";
                case RTK: return "Real Time Kinematic";
                case FloatRTK: return "Float RTK";
                case Estimated: return "Estimated";
                case Manual: return "Manual";
                case Simulation: return "Simulation";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringF() {
            switch(this) {
                case NoFix: return "0 (No Fix)";
                case GPS: return "1 (GPS)";
                case DGPS: return "2 (DGPS)";
                case PPS: return "3 (PPS)";
                case RTK: return "5 (RTK)";
                case FloatRTK: return "4 (Float RTK)";
                case Estimated: return "6 (Estimated)";
                case Manual: return "7 (Manual)";
                case Simulation: return "8 (Simulation)";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
