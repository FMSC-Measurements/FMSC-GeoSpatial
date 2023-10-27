package com.usda.fmsc.geospatial.gnss.juniper.nmea.sentences;

import org.joda.time.LocalTime;

import com.usda.fmsc.geospatial.gnss.juniper.nmea.codes.BattChargeStatus;
import com.usda.fmsc.geospatial.gnss.nmea.SentenceFormats;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class BATTSentence extends NmeaSentence {
    private LocalTime time = null;
    private int battId;
    private int battLevel;
    private BattChargeStatus battChargeStatus;

    public BATTSentence(String nmea) {
        super(TalkerID.PJSI_BATT, nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 5) {
            try {
                if (tokens[2] != null && !tokens[2].isEmpty()) {
                    try {
                        time = LocalTime.parse(tokens[2], SentenceFormats.TimeFormatter);
                    } catch (Exception e) {
                        time = LocalTime.parse(tokens[2], SentenceFormats.TimeFormatterAlt);
                    }
                }

                battId = Integer.parseInt(tokens[3]);

                battLevel = Integer.parseInt(tokens[4]);

                battChargeStatus = BattChargeStatus.parse(Integer.parseInt(tokens[5]));

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.Unknown;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getBattId() {
        return battId;
    }

    public int getBattLevel() {
        return battLevel;
    }

    public BattChargeStatus getBattChargeStatus() {
        return battChargeStatus;
    }
}
