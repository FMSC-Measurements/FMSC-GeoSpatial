package com.usda.fmsc.geospatial.base.parsers;

import com.usda.fmsc.geospatial.base.listeners.IStringMsgListener;
import com.usda.fmsc.geospatial.base.messages.IStringMessage;

public abstract class BaseStringMsgParser<
    StringMessage extends IStringMessage,
    Listener extends IStringMsgListener<StringMessage>> extends BaseParser<String, StringMessage, Listener> {

    public BaseStringMsgParser() {
        super();
    }

    public BaseStringMsgParser(String delimiter) {
        super(delimiter);
    }

    public BaseStringMsgParser(long longestPause) {
        super(longestPause);
    }
}
