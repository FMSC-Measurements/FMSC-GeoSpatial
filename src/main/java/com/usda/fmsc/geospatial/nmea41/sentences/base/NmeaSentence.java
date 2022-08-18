package com.usda.fmsc.geospatial.nmea41.sentences.base;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;

import java.io.Serializable;

public abstract class NmeaSentence implements Serializable {
    private TalkerID talkerID;
    private boolean valid;


    public NmeaSentence() { }

    public NmeaSentence(String nmea) {
        this.talkerID = TalkerID.parse(nmea);
        this.valid = isValidNmea(getSentenceID(), null, nmea) & parse(nmea);
    }


    protected abstract boolean parse(String nmea);


    public boolean isValid() {
        return valid;
    }

    public TalkerID getTalkerID() {
        return talkerID;
    }

    public abstract SentenceID getSentenceID();

    public abstract boolean isMultiSentence();


    protected static String[] tokenize(String nmea) {
        return nmea.substring(0, nmea.indexOf("*")).split(",", -1);
    }


    public static boolean validateChecksum(String nmea) {
        if (nmea.length() > 10 && nmea.startsWith("$") && nmea.contains("*")) {
            String calcString = nmea.substring(1);
            int ast = calcString.indexOf("*");
            String checkSumStr = calcString.substring(ast + 1, ast + 3);
            calcString = calcString.substring(0, ast);

            int checksum = 0;

            for(int i = 0; i < calcString.length(); i++) {
                checksum ^= (byte)calcString.charAt(i);
            }

            String hex = Integer.toHexString(checksum);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            return hex.equalsIgnoreCase(checkSumStr);
        }

        return false;
    }

    public static boolean isValidNmea(SentenceID sentenceID, TalkerID talkerID, String nmea) {
        boolean valid = false;

        if (validateChecksum(nmea)) {
            valid = sentenceID == SentenceID.parse(nmea);

            if (talkerID != null) {
                valid &= talkerID == TalkerID.parse(nmea);
            }
        }

        return valid;
    }
}
