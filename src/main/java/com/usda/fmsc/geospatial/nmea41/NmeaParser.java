package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;
import com.usda.fmsc.geospatial.nmea41.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea41.sentences.base.NmeaSentence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;


public class NmeaParser {
    private List<Listener> listeners;
    private EnumSet<TalkerID> usedTalkerIDs;

    private boolean synced, initialized, syncing;
    private long lastSentenceTime, longestPause, startInit;
    private ArrayList<Long> pauses = new ArrayList<>();

    private NmeaBurst burst;


    public NmeaParser(TalkerID talkerID) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.of(talkerID);
    }

    public NmeaParser(EnumSet<TalkerID> talkerIDs) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.copyOf(talkerIDs);
    }


    public boolean parse(String nmea) {
        if (synced) {
            long now = System.currentTimeMillis();

            try {
                if (now - lastSentenceTime > longestPause) {
                    if (burst != null) {
                        postBurstReceived(burst);
                    }

                    burst = new NmeaBurst();
                }

                if (usedTalkerIDs.contains(TalkerID.parse(nmea))) {
                    NmeaSentence sentence;
                    if (burst != null) {
                        sentence = burst.addNmeaSentence(nmea);
                    } else {
                        sentence = NmeaBurst.parseNmea(nmea);
                    }

                    if (sentence != null) {
                        postNmeaReceived(sentence);
                    }
                }
            } catch (UnsupportedSentenceException e) {
                //
            }

            lastSentenceTime = now;

            return true;
        }

        return false;
    }

    public boolean sync(String nmea) {
        long now = System.currentTimeMillis();

        if (!synced) {
            if (syncing) {
                if (now - lastSentenceTime >= longestPause) {
                    syncing = false;
                    synced = true;
                }
            } else if (!initialized) {
                initialized = true;
                startInit = now;
            } else {
                long pause = now - lastSentenceTime;

                if (pauses == null) {
                    pauses = new ArrayList<>();
                }

                pauses.add(pause);

                if (pause > longestPause) {
                    longestPause = pause;
                }

                if (now - startInit >= 3000) {
                    initialized = false;
                    syncing = true;

                    long pc = 0;

                    for (Long p : pauses) {
                        pc += p;
                    }

                    pc /= pauses.size();

                    pc *= 2;

                    longestPause *= .8;

                    if (longestPause > 900 && pc < longestPause) {
                        longestPause = pc;
                    }

                    pauses = null;
                }
            }
        }

        lastSentenceTime = now;
        return synced;
    }


    public boolean isSynced() {
        return synced;
    }

    public boolean isSyncing() {
        return syncing;
    }


    public void reset() {
        initialized = syncing = synced = false;
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
