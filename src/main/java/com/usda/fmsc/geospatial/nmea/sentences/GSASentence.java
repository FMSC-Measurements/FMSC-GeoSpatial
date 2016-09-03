package com.usda.fmsc.geospatial.nmea.sentences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.usda.fmsc.geospatial.nmea.NmeaIDs;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;

public class GSASentence extends NmeaSentence  implements Serializable {

    private Mode mode;
    private Fix fix;
    private ArrayList<Integer> satsUsed;
    private Float pdop, hdop, vdop;


    public GSASentence() { }

    public GSASentence(String nmea) {
        super(nmea);
    }

    @Override
    public boolean parse(String nmea) {
        satsUsed = new ArrayList<>();

        if (super.parse(nmea)) {
            valid = false;
            String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

            if (tokens.length > 17) {
                try {
                    mode = Mode.parse(tokens[1]);

                    fix = Fix.parse(tokens[2]);

                    String token;
                    for (int i = 3; i < 15; i++) {
                        token = tokens[i];
                        if (token != null && !token.equals(""))
                            satsUsed.add(Integer.parseInt(token));
                    }

                    token = tokens[15];
                    if (token != null && !token.equals(""))
                        pdop = Float.parseFloat(token);

                    token = tokens[16];
                    if (token != null && !token.equals(""))
                        hdop = Float.parseFloat(token);

                    token = tokens[17];
                    if (token != null && !token.equals(""))
                        vdop = Float.parseFloat(token);

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
        return SentenceID.GSA;
    }


    public Mode getMode() {
        return mode;
    }

    public Fix getFix() {
        return fix;
    }

    public ArrayList<Integer> getSatellitesUsed() {
        return satsUsed;
    }

    public int getSatellitesUsedCount() {
        return satsUsed.size();
    }

    public Float getPDOP() {
        return pdop;
    }

    public Float getHDOP() {
        return hdop;
    }

    public Float getVDOP() {
        return vdop;
    }


    public enum Mode {
        Auto(0),
        Manual(1);

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
            throw new IllegalArgumentException("Invalid Mode id: " + id);
        }

        public static Mode parse(String str) {
            switch(str.toLowerCase()) {
                case "0":
                case "a":
                case "auto": return Auto;
                case "1":
                case "m":
                case "manual": return Manual;
                default: throw new IllegalArgumentException("Invalid Mode Name: " + str);
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case Auto: return "Auto";
                case Manual: return "Manual";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringF() {
            switch(this) {
                case Auto: return " 0 (Auto)";
                case Manual: return "1 (Manual)";
                default: throw new IllegalArgumentException();
            }
        }
    }

    public enum Fix {
        NoFix(0),
        _2D(1),
        _3D(2);

        private final int value;

        Fix(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Fix parse(int id) {
            Fix[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid Fix id: " + id);
        }

        public static Fix parse(String str) {
            switch(str.toLowerCase()) {
                case "1":
                case "nofix":
                case "no fix":
                case "no": return NoFix;
                case "2":
                case "2d": return _2D;
                case "3":
                case "3d": return _3D;
                default: throw new IllegalArgumentException("Invalid Fix Name: " + str);
            }
        }

        @Override
        public String toString() {
            switch(this) {
                case NoFix: return "No Fix";
                case _2D: return "2D";
                case _3D: return "3D";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringF() {
            switch(this) {
                case NoFix: return "0 (No Fix)";
                case _2D: return "1 (2D)";
                case _3D: return "2 (3D)";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
