package com.usda.fmsc.geospatial.base.parsers;

import com.usda.fmsc.geospatial.base.listeners.IBinMsgListener;
import com.usda.fmsc.geospatial.base.messages.IBinMessage;

public abstract class BaseBinMsgParser<
    BinMessage extends IBinMessage,
    Listener extends IBinMsgListener<BinMessage>> extends BaseParser<byte[], BinMessage, Listener> {

    public BaseBinMsgParser() {

    }

    public BaseBinMsgParser(byte[] delimiter) {
        super(delimiter);
    }

    public BaseBinMsgParser(long longestPause) {
        super(longestPause);
    }
}
