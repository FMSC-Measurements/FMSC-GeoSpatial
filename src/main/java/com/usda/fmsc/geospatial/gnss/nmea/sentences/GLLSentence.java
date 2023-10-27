package com.usda.fmsc.geospatial.gnss.nmea.sentences;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.codes.EastWest;
import com.usda.fmsc.geospatial.codes.NorthSouth;
import com.usda.fmsc.geospatial.gnss.nmea.SentenceFormats;
import com.usda.fmsc.geospatial.gnss.nmea.codes.PositionMode;
import com.usda.fmsc.geospatial.gnss.nmea.codes.Status;
import com.usda.fmsc.geospatial.gnss.nmea.sentences.base.PositionSentence;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;

import org.joda.time.LocalTime;

public class GLLSentence extends PositionSentence {
    private LocalTime fixTime;
    private Status status;


    public GLLSentence(TalkerID talkerID, String nmea) {
        super(talkerID, nmea);
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
                    positionModes = PositionMode.parseAll(tokens[7]);
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
