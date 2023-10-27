package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.messages.IStringMessage;

public interface IStringMsgListener<Message extends IStringMessage> extends IMsgListener<String, Message>{
    
}