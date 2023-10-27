package com.usda.fmsc.geospatial.gnss.nmea.sentences;

import com.usda.fmsc.geospatial.TextUtils;
import com.usda.fmsc.geospatial.gnss.codes.GnssSignal;
import com.usda.fmsc.geospatial.gnss.nmea.GnssNmeaTools;
import com.usda.fmsc.geospatial.gnss.nmea.Satellite;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.IMultiSentence;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

import java.util.ArrayList;

public class GSVSentence extends NmeaSentence implements IMultiSentence {
    private ArrayList<Satellite> satellites;
    private int sentenceNumber, totalSentenceCount, numberOfSatellitesInView;
    private GnssSignal signal;

    public GSVSentence(TalkerID talkerID, String nmea) {
        super(talkerID, nmea);
    }

    @Override
    public boolean parse(String nmea) {
        boolean valid = false;

        String[] tokens = tokenize(nmea);

        if (tokens.length > 3 && tokens[1].length() > 0) {
            try {
                satellites = new ArrayList<>();

                totalSentenceCount = Integer.parseInt(tokens[1]);
                sentenceNumber = Integer.parseInt(tokens[2]);
                numberOfSatellitesInView = Integer.parseInt(tokens[3]);

                int index = 4;
                for (; index < 17 && index + 3 < tokens.length; index += 4) {
                    if (tokens[index] != null && !tokens[index].isEmpty()) {
                        satellites.add(new Satellite(Integer.parseInt(tokens[index]), parseFloat(tokens[index + 1]),
                                parseFloat(tokens[index + 2]), parseFloat(tokens[index + 3]), getTalkerID()));
                    }
                }

                if (tokens.length > index && !TextUtils.isEmpty(tokens[index])) {
                    signal = GnssNmeaTools.getSignalFromIdAndTalker(Integer.parseInt(tokens[index], 16), getTalkerID());

                    for (Satellite sat : satellites) {
                        sat.addSignal(signal);
                    }
                }

                valid = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GSV;
    }

    @Override
    public boolean isMultiSentence() {
        return true;
    }

    public ArrayList<Satellite> getSatellites() {
        return satellites;
    }

    public int getSatelliteCountInSentence() {
        return satellites.size();
    }

    public int getSatellitesInViewCount() {
        return numberOfSatellitesInView;
    }

    public GnssSignal getSignal() {
        return signal;
    }

    @Override
    public int getTotalSentenceCount() {
        return totalSentenceCount;
    }

    @Override
    public int getSentenceNumber() {
        return sentenceNumber;
    }

    private static Float parseFloat(String token) {
        if (!TextUtils.isEmpty(token))
            return Float.parseFloat(token);
        return null;
    }
}