package com.usda.fmsc.geospatial.nmea41.sentences;

import android.text.TextUtils;

import static com.usda.fmsc.geospatial.nmea41.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea41.Satellite;
import com.usda.fmsc.geospatial.nmea41.sentences.base.MultiSentence;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

import java.io.Serializable;
import java.util.ArrayList;

public class GSVSentence extends NmeaSentence implements MultiSentence, Serializable {
    private ArrayList<Satellite> satellites = new ArrayList<>();
    private int sentenceNumber, totalSentenceCount, numberOfSatellitesInView;
    private Integer signalID;

    public GSVSentence() { }

    public GSVSentence(String nmea) {
        super(nmea);
    }

    @Override
    public boolean parse(String nmea) {
        boolean valid = false;

        String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

        if (tokens.length > 3) {
            try {
                totalSentenceCount = Integer.parseInt(tokens[1]);
                sentenceNumber = Integer.parseInt(tokens[2]);
                numberOfSatellitesInView = Integer.parseInt(tokens[3]);

                int current = 4;
                for (; current < 16 && current + 3 < tokens.length; current += 4) {
                    try {
                        if (tokens[current] != null) {
                            satellites.add(new Satellite(
                                    Integer.parseInt(tokens[current]),
                                    parseFloat(tokens[current + 1]),
                                    parseFloat(tokens[current + 2]),
                                    parseFloat(tokens[current + 3])
                            ));
                        }
                    } catch (NumberFormatException e) {
                        //
                    }
                }

                int sigIdIndex = current - 2;
                if (tokens.length > sigIdIndex & !TextUtils.isEmpty(tokens[sigIdIndex])) {
                    signalID = Integer.parseInt(tokens[sigIdIndex]);
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

    public Integer getSignalID() {
        return signalID;
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