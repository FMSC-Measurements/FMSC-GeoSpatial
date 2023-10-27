package com.usda.fmsc.geospatial.gnss.nmea.sentences;

import org.joda.time.DateTime;

import com.usda.fmsc.geospatial.gnss.nmea.SentenceFormats;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class ZDASentence extends NmeaSentence {
    private DateTime time;
    private int localZoneHours, localZoneMinutes;


    public ZDASentence(TalkerID talkerID, String nmea) {
        super(talkerID, nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 6) {
            try {
                String timeString = String.format("%s %s%s%s", tokens[1], tokens[2], tokens[3], tokens[4]);

                try {
                    time = DateTime.parse(timeString, SentenceFormats.DateTimeFormatter);
                } catch (Exception e) {
                    time = DateTime.parse(timeString, SentenceFormats.DateTimeFormatterAlt);
                }

                localZoneHours = Integer.parseInt(tokens[5]);
                localZoneMinutes = Integer.parseInt(tokens[6]);

                valid = true;
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        return valid;
    }


    @Override
    public SentenceID getSentenceID() {
        return SentenceID.ZDA;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }


    public DateTime getTime() {
        return time;
    }

    public int getLocalZoneHours() {
        return localZoneHours;
    }

    public int getLocalZoneMinutes() {
        return localZoneMinutes;
    }
}
