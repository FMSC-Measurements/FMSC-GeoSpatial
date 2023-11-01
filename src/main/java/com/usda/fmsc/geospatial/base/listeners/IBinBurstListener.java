package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.bursts.IBinBurst;
import com.usda.fmsc.geospatial.base.messages.IBinMessage;

public interface IBinBurstListener<Message extends IBinMessage, Burst extends IBinBurst<Message>>
                    extends IBurstListener<byte[], Message, Burst> {
}
