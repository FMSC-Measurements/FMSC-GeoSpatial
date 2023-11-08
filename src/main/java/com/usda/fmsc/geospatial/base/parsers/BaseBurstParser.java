package com.usda.fmsc.geospatial.base.parsers;

import java.util.ArrayList;

import com.usda.fmsc.geospatial.base.bursts.IBurst;
import com.usda.fmsc.geospatial.base.listeners.IBurstListener;
import com.usda.fmsc.geospatial.base.messages.IMessage;

public abstract class BaseBurstParser<
    PDT extends Object,
    Message extends IMessage,
    Burst extends IBurst<PDT, Message>,
    Listener extends IBurstListener<PDT, Message, Burst>> extends BaseParser<PDT, Message, Listener> {

    private boolean synced, initialized, syncing;
    private long lastMessageTime, longestPause, startInit;
    private ArrayList<Long> pauses = new ArrayList<>();

    private ParseMode parseMode;
    private PDT burstDelimiter = null;


    public BaseBurstParser(PDT burstDelimiter) {
        super();
        this.burstDelimiter = burstDelimiter;
        this.parseMode = ParseMode.Delimiter;
    }

    public BaseBurstParser(long longestPause) {
        super();
        this.parseMode = ParseMode.Time;
    }


    @Override
    protected boolean shouldParse(PDT data, Object args) {
        return parseMode != ParseMode.Time || synced;
    }

    @Override
    protected void onPreParse(PDT data, Object args) {
        long now = System.currentTimeMillis();

        if ((parseMode == ParseMode.Time && now - lastMessageTime > longestPause) ||
            (parseMode == ParseMode.Delimiter && data != null && checkForDelimiter(data))) {
            onBurstDelimination();
        }

        lastMessageTime = now;
    }

    protected void onBurstDelimination() {
        //
    }

    public abstract boolean checkForDelimiter(PDT data);

    protected PDT getBurstDelimiter() {
        return burstDelimiter;
    }


    public final boolean sync(PDT data) {
        long now = System.currentTimeMillis();

        if (!synced) {
            if (parseMode == ParseMode.Time) {
                if (syncing) {
                    if (now - lastMessageTime >= longestPause) {
                        syncing = false;
                        synced = true;
                    }
                } else if (!initialized) {
                    initialized = true;
                    startInit = now;
                } else {
                    long pause = now - lastMessageTime;

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
            } else if (parseMode == ParseMode.Delimiter) {
                if (data != null && checkForDelimiter(data)) {
                    syncing = false;
                    synced = true;
                } else {
                    syncing = true;
                }
            }
        }

        lastMessageTime = now;
        return synced;
    }

    public final boolean isSynced() {
        return synced;
    }

    public final boolean isSyncing() {
        return syncing;
    }


    public void reset() {
        initialized = syncing = synced = false;
    }


    public final void setParseMode(ParseMode parseMode) {
        if (this.parseMode != parseMode) {
            this.parseMode = parseMode;

            if (this.parseMode == ParseMode.Delimiter && burstDelimiter == null) {
                throw new RuntimeException("Burst Delimiter must be set before changing modes.");
            }

            reset();
        }
    }

    public final void setBurstDelimiter(PDT burstDelimiter) {
        this.burstDelimiter = burstDelimiter;

        if (this.parseMode == ParseMode.Delimiter) {
            reset();
        }
    }
}
