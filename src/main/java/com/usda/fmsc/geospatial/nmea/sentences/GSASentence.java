package com.usda.fmsc.geospatial.nmea.sentences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;

public class GSASentence extends NmeaSentence  implements Serializable {

    private Mode mode;
    private Fix fix;
    private List<Integer> satsUsed;
    private Float pdop, hdop, vdop;


    public GSASentence() {
        super(SentenceID.GSA);
    }

    public GSASentence(String nmea) {
        super(SentenceID.GSA, nmea);
    }

    @Override
    public boolean parse(String nmea) {
        satsUsed = new ArrayList<>();

        if (super.parse(nmea)) {
            valid = false;
            String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

            if (tokens.length > 17) {
                try {
                    if (mode == null)
                        mode = Mode.parse(tokens[1]);

                    if (fix == null)
                        fix = Fix.parse(tokens[2]);

                    String token;
                    for (int i = 3; i < 15; i++) {
                        token = tokens[i];
                        if (token != null && !token.equals(""))
                            satsUsed.add(Integer.parseInt(token));
                    }

                    if (pdop == null)
                        pdop = Float.parseFloat(tokens[15]);

                    if (hdop == null)
                        hdop = Float.parseFloat(tokens[16]);

                    if (vdop == null)
                        vdop = Float.parseFloat(tokens[17]);

                    valid = true;
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
            }
        }

        return valid;
    }


    public Mode getMode() {
        return mode;
    }

    public Fix getFix() {
        return fix;
    }

    public List<Integer> getSatellitesUsed() {
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
    }
}
