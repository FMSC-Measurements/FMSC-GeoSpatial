package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.bursts.IBurst;
import com.usda.fmsc.geospatial.base.messages.IMessage;

public interface IBurstListener<PDT, Message extends IMessage, Burst extends IBurst<PDT, Message>>
                    extends IMsgListener<PDT, Message> {
    public void onBurstReceived(Burst burst);
}