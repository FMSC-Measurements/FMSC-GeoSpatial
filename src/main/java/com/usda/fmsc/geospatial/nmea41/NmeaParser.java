package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class NmeaParser {
    private List<Listener> listeners;
    private List<TalkerID> usedTalkerIDs;

    private NmeaBurst burst;

    private long lastSentenceTime;


    public NmeaParser(TalkerID talkerID) {
        listeners = new ArrayList<>();
        usedTalkerIDs = new ArrayList<>();
        usedTalkerIDs.add(talkerID);
    }

    public NmeaParser(Collection<TalkerID> talkerIDs) {
        listeners = new ArrayList<>();
        usedTalkerIDs = new ArrayList<>(talkerIDs);
    }


    public boolean parse(String nmea) {
        boolean usedNmea = false;

        if (burst == null) {
            burst = new NmeaBurst();
        }

        if (usedTalkerIDs.contains(TalkerID.parse(nmea))) {
            NmeaSentence sentence = burst.addNmeaSentence(nmea);

            if (sentence != null) {
                postNmeaReceived(sentence);
            }

            usedNmea = true;
        }

        if (burst != null && burst.isComplete()) {
            postBurstReceived(burst);
            burst = null;
        }

        lastSentenceTime = System.currentTimeMillis();

        return usedNmea;
    }

    public void reset() {
        burst = null;
    }

    private void postBurstReceived(NmeaBurst burst) {
        for (Listener listener : listeners) {
            listener.onBurstReceived(burst);
        }
    }

    private void postNmeaReceived(NmeaSentence sentence) {
        for (Listener listener : listeners) {
            listener.onNmeaReceived(sentence);
        }
    }


    public void addListener(Listener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }


    public interface Listener {
        void onBurstReceived(NmeaBurst burst);

        void onNmeaReceived(NmeaSentence sentence);
    }
}
