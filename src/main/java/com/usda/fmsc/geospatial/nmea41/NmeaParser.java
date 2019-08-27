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

    private boolean synced, initialized, syncing;

    private long lastSentenceTime, longestPause, startInit;


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
        if (synced) {
            long now = System.currentTimeMillis();

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
                startInit = System.currentTimeMillis();
            } else {
                long pause = now - lastSentenceTime;

                if (pause > longestPause) {
                    longestPause = pause;
                }

                if (startInit - now >= 3000) {
                    initialized = false;
                    syncing = true;
                    longestPause *= .8;
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
        burst = null;
    }

    public void reSync() {
        initialized = syncing = synced = false;
        reset();
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
