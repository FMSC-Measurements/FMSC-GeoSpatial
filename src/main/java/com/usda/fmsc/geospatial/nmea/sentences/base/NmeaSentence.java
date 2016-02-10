package com.usda.fmsc.geospatial.nmea.sentences.base;

import java.io.Serializable;

import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.TalkerID;

public abstract class NmeaSentence implements Serializable {
    protected TalkerID talkerID;
    protected SentenceID sentenceID;
    protected String rawNmea;
    protected boolean valid;


    public NmeaSentence(SentenceID sentenceID) {
        this.sentenceID = sentenceID;
    }

    public NmeaSentence(SentenceID sentenceID, String nmea) {
        this.sentenceID = sentenceID;

        if (isMultiString() && nmea.contains("\n")) {
            String[] strs = nmea.split("\\r?\\n");

            for (String str : strs) {
                parse(str);
            }
        } else {
            this.rawNmea = nmea;
            parse(nmea);
        }
    }


    protected boolean parse(String nmea) {
        valid = isValidNmea(sentenceID, null, nmea);
        return valid;
    }


    public boolean isValid() {
        return valid;
    }

    public TalkerID getTalkerID() {
        return talkerID;
    }

    public void setTalkerID(TalkerID talkerID) {
        this.talkerID = talkerID;
    }

    public SentenceID getSentenceID() {
        return sentenceID;
    }

    public String getNmea() {
        return rawNmea;
    }

    public boolean isMultiString() {
        return false;
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
