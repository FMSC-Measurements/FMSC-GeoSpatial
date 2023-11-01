package com.usda.fmsc.geospatial.base.parsers;

import com.usda.fmsc.geospatial.base.bursts.IBinBurst;
import com.usda.fmsc.geospatial.base.listeners.IBinBurstListener;
import com.usda.fmsc.geospatial.base.messages.IBinMessage;

public abstract class BaseBinBurstParser<
    Message extends IBinMessage,
    Burst extends IBinBurst<Message>,
    Listener extends IBinBurstListener<Message, Burst>>
        extends BaseBurstParser<byte[], Message, Burst, Listener> {

    public BaseBinBurstParser(byte[] burstDelimiter) {
        super(burstDelimiter);
    }

    public BaseBinBurstParser(long longestPause) {
        super(longestPause);
    }
}
