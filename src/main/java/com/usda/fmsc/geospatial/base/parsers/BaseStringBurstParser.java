package com.usda.fmsc.geospatial.base.parsers;

import com.usda.fmsc.geospatial.base.bursts.IStringBurst;
import com.usda.fmsc.geospatial.base.listeners.IStringBurstListener;
import com.usda.fmsc.geospatial.base.messages.IStringMessage;

public abstract class BaseStringBurstParser<
    Message extends IStringMessage,
    Burst extends IStringBurst<Message>,
    Listener extends IStringBurstListener<Message, Burst>>
        extends BaseBurstParser<String, Message, Burst, Listener> {

    public BaseStringBurstParser(String burstDelimiter) {
        super(burstDelimiter);
    }

    public BaseStringBurstParser(long longestPause) {
        super(longestPause);
    }
}
