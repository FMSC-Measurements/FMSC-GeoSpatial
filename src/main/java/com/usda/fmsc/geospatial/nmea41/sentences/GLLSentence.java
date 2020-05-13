package com.usda.fmsc.geospatial.nmea41.sentences;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.SentenceFormats;
import com.usda.fmsc.geospatial.nmea41.codes.PositionMode;
import com.usda.fmsc.geospatial.nmea41.codes.Status;
import com.usda.fmsc.geospatial.nmea41.sentences.base.PositionSentence;

import org.joda.time.LocalTime;

public class GLLSentence extends PositionSentence {
    private LocalTime fixTime;
    private Status status;


    public GLLSentence(String nmea) {
        super(nmea);
    }

    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 6 && tokens[1].length() > 0) {
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

                if (tokens.length > 7) {
                    positionMode = PositionMode.parse(tokens[7]);
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
}
