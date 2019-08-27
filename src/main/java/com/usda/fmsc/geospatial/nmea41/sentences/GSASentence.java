package com.usda.fmsc.geospatial.nmea41.sentences;

import android.text.TextUtils;

import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

import java.io.Serializable;
import java.util.ArrayList;

public class GSASentence extends NmeaSentence implements Serializable {
    private Mode mode;
    private Fix fix;
    private ArrayList<Integer> satsUsed;
    private Float pdop, hdop, vdop;
    private Integer sysID;


    public GSASentence(String nmea) {
        super(nmea);
    }

    @Override
    public boolean parse(String nmea) {
        satsUsed = new ArrayList<>();

        boolean valid = false;
        String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

        if (tokens.length > 17) {
            try {
                mode = Mode.parse(tokens[1]);

                fix = Fix.parse(tokens[2]);

                String token;
                for (int i = 3; i < 15; i++) {
                    token = tokens[i];
                    if (!TextUtils.isEmpty(token))
                        satsUsed.add(Integer.parseInt(token));
                }

                token = tokens[15];
                if (!TextUtils.isEmpty(token))
                    pdop = Float.parseFloat(token);

                token = tokens[16];
                if (!TextUtils.isEmpty(token))
                    hdop = Float.parseFloat(token);

                token = tokens[17];
                if (!TextUtils.isEmpty(token))
                    vdop = Float.parseFloat(token);

                if (tokens.length > 19) {
                    token = tokens[18];
                    if (!TextUtils.isEmpty(token))
                        sysID = Integer.parseInt(token);
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
        return SentenceID.GSA;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
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

    public Integer getSystemID() {
        return sysID;
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
                case Auto: return "0 (Auto)";
                case Manual: return "1 (Manual)";
                default: throw new IllegalArgumentException();
            }
        }
    }

    public enum Fix {
        Unknown(0),
        NoFix(1),
        _2D(2),
        _3D(3);

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
                case "0":
                case "unknown": return Unknown;
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
                case Unknown: return "Unknown";
                case NoFix: return "No Fix";
                case _2D: return "2D";
                case _3D: return "3D";
                default: throw new IllegalArgumentException();
            }
        }

        public String toStringF() {
            switch(this) {
                case Unknown: return "0 (Unknown)";
                case NoFix: return "1 (No Fix)";
                case _2D: return "2 (2D)";
                case _3D: return "3 (3D)";
                default: throw new IllegalArgumentException();
            }
        }
    }
}
