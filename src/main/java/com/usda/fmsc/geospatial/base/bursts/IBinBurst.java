package com.usda.fmsc.geospatial.base.bursts;

import com.usda.fmsc.geospatial.base.messages.IBinMessage;

public interface IBinBurst<Message extends IBinMessage> extends IBurst<byte[], Message> {
    
}
