package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.messages.IBinMessage;

public interface IBinMsgListener<Message extends IBinMessage> extends IMsgListener<byte[], Message>{
    
}