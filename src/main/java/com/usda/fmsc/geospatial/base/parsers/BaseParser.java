package com.usda.fmsc.geospatial.base.parsers;

import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.base.listeners.IMsgListener;
import com.usda.fmsc.geospatial.base.messages.IMessage;

public abstract class BaseParser<
    PDT extends Object,
    Message extends IMessage,
    Listener extends IMsgListener<PDT, Message>> {


    private final List<Listener> listeners;

    private boolean synced, initialized, syncing;
    private long lastMessageTime, longestPause, startInit;
    private ArrayList<Long> pauses = new ArrayList<>();

    private ParseMode parseMode = ParseMode.None;
    private PDT delimiter = null;

    
    public BaseParser() {
        listeners = new ArrayList<>();
    }

    public BaseParser(PDT delimiter) {
        this();
        this.delimiter = delimiter;
        this.parseMode = ParseMode.Delimiter;
    }

    public BaseParser(long longestPause) {
        this();
        this.parseMode = ParseMode.Time;
    }


    
    public final Message parse(PDT data) {
        Message message = null;

        if (parseMode != ParseMode.Time || synced) {
            long now = System.currentTimeMillis();

            if ((parseMode == ParseMode.Time && now - lastMessageTime > longestPause) ||
                (parseMode == ParseMode.Delimiter && checkForDelimiter(data))) {
                onDelimination();
            }

            message = parseMessage(data);

            if (message != null) {
                onMessageReceived(message);
            } else {
                onInvalidMessageReceived(data);
            }

            lastMessageTime = now;
        }

        return message;
    }

    protected abstract Message parseMessage(PDT data);

    protected void onDelimination() {
        //
    }


    protected void onMessageReceived(Message message) {
          for (Listener listener : listeners) {
            listener.onMessageReceived(message);
        }
    }

    protected void onInvalidMessageReceived(PDT invalidMessageData) {
          for (Listener listener : listeners) {
            listener.onInvalidMessageReceived(invalidMessageData);
        }
    }


    public boolean checkForDelimiter(PDT data) {
        return false;
    }

    protected PDT getDelimiter() {
        return delimiter;
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
            } else {
                if (checkForDelimiter(data)) {
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

            if (this.parseMode == ParseMode.Delimiter && delimiter == null) {
                throw new RuntimeException("Burst Delimiter must be set before changing modes.");
            }

            reset();
        }
    }

    public final void setDelimiter(PDT burstDelimiter) {
        this.delimiter = burstDelimiter;

        if (this.parseMode == ParseMode.Delimiter) {
            reset();
        }
    }


    public final void addListener(Listener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public final void removeListener(Listener listener) {
        listeners.remove(listener);
    }

}
