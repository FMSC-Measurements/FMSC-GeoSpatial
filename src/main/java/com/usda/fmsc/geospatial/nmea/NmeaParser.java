package com.usda.fmsc.geospatial.nmea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.usda.fmsc.geospatial.nmea.exceptions.ExcessiveStringException;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;
import com.usda.fmsc.geospatial.nmea.sentences.*;
import com.usda.fmsc.geospatial.nmea.sentences.base.MultiSentence;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;

public class NmeaParser<TNmeaBurst extends INmeaBurst> {
    private List<Listener> listeners;
    private List<TalkerID> usedTalkedIDs;

    private INmeaBurst burst;
    private Class<TNmeaBurst> clazz;


    public NmeaParser(Class<TNmeaBurst> clazz) {
        this(TalkerID.GP, clazz);
    }

    public NmeaParser(TalkerID talkerID, Class<TNmeaBurst> clazz) {
        this.clazz = clazz;

        listeners = new ArrayList<>();
        usedTalkedIDs = new ArrayList<>();
        usedTalkedIDs.add(talkerID);
    }

    public NmeaParser(Collection<TalkerID> talkerIDs) {
        listeners = new ArrayList<>();
        usedTalkedIDs = new ArrayList<>(talkerIDs);
    }


    private TNmeaBurst newBurst() throws InstantiationException, IllegalAccessException {
        return this.clazz.newInstance();
    }

    public boolean parse(String nmea) {
        boolean usedNmea = false;

        if (burst == null) {
            try {
                burst = newBurst();
            } catch (Exception e) {
                //
            }
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
            //burst = new NmeaBurst();
        }

        if (burst != null && burst.isFull()) {
            postBurstReceived(burst);
            burst = null;
        }

        return usedNmea;
    }

    public void reset() {
        burst = null;
    }

    private void postBurstReceived(INmeaBurst burst) {
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
        void onBurstReceived(INmeaBurst burst);

        void onNmeaReceived(NmeaSentence sentence);
    }
}
