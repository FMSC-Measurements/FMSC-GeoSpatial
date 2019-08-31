package com.usda.fmsc.geospatial.nmea41.sentences;

import android.text.TextUtils;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

import org.joda.time.LocalTime;


public class GSTSentence extends NmeaSentence {
    private LocalTime time;
    private float rangeRms, stdLatDev, stdLongDev, stdAltDev;
    private Float stdMajor, stdMinor, orientation;


    public GSTSentence(String nmea) {
        super(nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 6) {
            try {
                String token = tokens[1];
                try {
                    time = LocalTime.parse(token, SentenceFormats.TimeFormatter);
                } catch (Exception e) {
                    time = LocalTime.parse(token, SentenceFormats.TimeFormatterAlt);
                }
                
                rangeRms = Float.parseFloat(tokens[2]);
                
                token = tokens[3];
                if (!TextUtils.isEmpty(token))
                    stdMajor = Float.parseFloat(token);

                token = tokens[4];
                if (!TextUtils.isEmpty(token))
                    stdMinor = Float.parseFloat(token);

                token = tokens[5];
                if (!TextUtils.isEmpty(token))
                    orientation = Float.parseFloat(token);

                stdLatDev = Float.parseFloat(tokens[6]);
                stdLongDev = Float.parseFloat(tokens[6]);
                stdAltDev = Float.parseFloat(tokens[6]);

                valid = true;
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GST;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }


    public LocalTime getTime() {
        return time;
    }

    public float getRangeRms() {
        return rangeRms;
    }

    public float getStdLatDev() {
        return stdLatDev;
    }

    public float getStdLongDev() {
        return stdLongDev;
    }

    public float getStdAltDev() {
        return stdAltDev;
    }

    public Float getStdMajor() {
        return stdMajor;
    }

    public Float getStdMinor() {
        return stdMinor;
    }

    public Float getOrientation() {
        return orientation;
    }
}
