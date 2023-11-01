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

    public BaseParser() {
        listeners = new ArrayList<>();
    }
    
    public final Message parse(PDT data) {
        Message message = null;

        if (shouldParse()) {
            onPreParse(data);

            message = parseMessage(data);

            if (message != null) {
                onMessageReceived(message);
            } else {
                onInvalidMessageReceived(data);
            }

            onPostParse();
        }

        return message;
    }
    
    protected abstract Message parseMessage(PDT data);


    protected boolean shouldParse() {
        return true;
    }

    protected void onPreParse(PDT data) {

    }

    protected void onPostParse() {

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

    
    public final void addListener(Listener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public final void removeListener(Listener listener) {
        listeners.remove(listener);
    }

}
