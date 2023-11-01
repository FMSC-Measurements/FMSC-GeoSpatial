package com.usda.fmsc.geospatial.gnss.nmea;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.exceptions.InvalidChecksumException;
import com.usda.fmsc.geospatial.nmea.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class GnssNmeaParserOld {
    private final List<Listener> listeners;
    private final EnumSet<TalkerID> usedTalkerIDs;

    private boolean synced, initialized, syncing;
    private long lastSentenceTime, longestPause, startInit;
    private ArrayList<Long> pauses = new ArrayList<>();

    private ParseMode parseMode = ParseMode.Time;
    private String burstDelimiter = "$RD1";

    private GnssNmeaBurst burst;


    public GnssNmeaParserOld(TalkerID talkerID) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.of(talkerID);
        parseMode = ParseMode.Time;
    }

    public GnssNmeaParserOld(TalkerID talkerID, String burstDelimiter) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.of(talkerID);
        parseMode = ParseMode.Delimiter;
        this.burstDelimiter = burstDelimiter;
    }

    public GnssNmeaParserOld(EnumSet<TalkerID> talkerIDs) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.copyOf(talkerIDs);
        parseMode = ParseMode.Time;
    }

    public GnssNmeaParserOld(EnumSet<TalkerID> talkerIDs, String burstDelimiter) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.copyOf(talkerIDs);
        parseMode = ParseMode.Delimiter;
        this.burstDelimiter = burstDelimiter;
    }

    public GnssNmeaParserOld(EnumSet<TalkerID> talkerIDs, long longestPause) {
        listeners = new ArrayList<>();
        usedTalkerIDs = EnumSet.copyOf(talkerIDs);
        parseMode = ParseMode.Time;
        this.longestPause = longestPause;
    }


    public boolean parse(String nmea) {
        if (synced) {
            long now = System.currentTimeMillis();

            nmea = sanitizeNmea(nmea);

            try {
                if (parseMode == ParseMode.Time ?
                        now - lastSentenceTime > longestPause :
                        nmea.contains(burstDelimiter)) {
                    if (burst != null) {
                        postBurstReceived(burst);
                    }

                    burst = new GnssNmeaBurst();
                }

                if (usedTalkerIDs.contains(TalkerID.parse(nmea))) {
                    NmeaSentence sentence;
                    if (burst != null) {
                        sentence = burst.addNmeaSentence(nmea);
                    } else {
                        sentence = burst.parseNmea(nmea);
                    }

                    if (sentence != null) {
                        postNmeaReceived(sentence);
                    }
                }
            } catch (UnsupportedSentenceException | InvalidChecksumException e) {
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
            if (parseMode == ParseMode.Time) {
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
            } else {
                if (nmea.contains(burstDelimiter)) {
                    syncing = false;
                    synced = true;
                } else {
                    syncing = true;
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


    public void setParseMode(ParseMode parseMode) {
        if (this.parseMode != parseMode) {
            this.parseMode = parseMode;
            reset();
        }
    }

    public void setBurstDelimiter(String burstDelimiter) {
        this.burstDelimiter = burstDelimiter;

        if (this.parseMode == ParseMode.Delimiter) {
            reset();
        }
    }


    private void postBurstReceived(GnssNmeaBurst burst) {
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
        void onBurstReceived(GnssNmeaBurst burst);

        void onNmeaReceived(NmeaSentence sentence);
    }


    public static String sanitizeNmea(String nmea) {
        return nmea.replaceAll("[$]>", "");
    }


    public enum ParseMode {
        Time(0),
        Delimiter(1);

        private final int value;

        ParseMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static ParseMode parse(int id) {
            ParseMode[] types = values();
            if(types.length > id && id > -1)
                return types[id];
            throw new IllegalArgumentException("Invalid Parse Mode: " + id);
        }

    }
}
