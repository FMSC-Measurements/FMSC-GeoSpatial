package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.bursts.IStringBurst;
import com.usda.fmsc.geospatial.base.messages.IStringMessage;

public interface IStringBurstListener<Message extends IStringMessage, Burst extends IStringBurst<Message>>
                    extends IBurstListener<String, Message, Burst> {
}
