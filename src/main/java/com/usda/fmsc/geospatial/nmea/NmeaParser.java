package com.usda.fmsc.geospatial.nmea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.usda.fmsc.geospatial.nmea.exceptions.ExcessiveStringException;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.MultiSentence;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;

public class NmeaParser {
    private List<Listener> listeners;
    private List<TalkerID> usedTalkedIDs;

    private NmeaBurst burst;


    public NmeaParser() {
        this(TalkerID.GP);
    }

    public NmeaParser(TalkerID talkerID) {
        listeners = new ArrayList<>();
        usedTalkedIDs = new ArrayList<>();
        usedTalkedIDs.add(talkerID);
    }

    public NmeaParser(Collection<TalkerID> talkerIDs) {
        listeners = new ArrayList<>();
        usedTalkedIDs = new ArrayList<>(talkerIDs);
    }


    public boolean parse(String nmea) {
        boolean usedNmea = false;

        if (burst == null) {
            burst = new NmeaBurst();
        }

        try {
            if (usedTalkedIDs.contains(TalkerID.parse(nmea))) {
                NmeaSentence sentence = burst.addNmeaSentence(nmea);

                if (sentence != null) {
                    if (sentence instanceof MultiSentence) {
                        if (((MultiSentence)sentence).hasAllMessages()) {
                            postNmeaReceived(sentence);
                        }
                    } else {
                        postNmeaReceived(sentence);
                    }
                }

                usedNmea = true;
            }
        } catch (ExcessiveStringException e) {
            burst = new NmeaBurst();
        }

        if (burst.isFull()) {
            postBurstReceived(burst);
            burst = null;
        }

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
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }


    public void addTalkerID(TalkerID talkerID) {
        if (!usedTalkedIDs.contains(talkerID))
            usedTalkedIDs.add(talkerID);
    }

    public void removeTalkerID(TalkerID talkerID) {
        usedTalkedIDs.remove(talkerID);
    }


    public static NmeaSentence getNmeaSentence(String sentence) {
        if (sentence == null) {
            throw new NullPointerException();
        }

        if (NmeaSentence.validateChecksum(sentence)) {
            switch (SentenceID.parse(sentence)) {
                case GGA: return new GGASentence(sentence);
                case GSA: return new GSASentence(sentence);
                case RMC: return new RMCSentence(sentence);
                case GSV: return new GSVSentence(sentence);
            }
        }

        return null;
    }

    public interface Listener {
        void onBurstReceived(NmeaBurst burst);

        void onNmeaReceived(NmeaSentence sentence);
    }
}
