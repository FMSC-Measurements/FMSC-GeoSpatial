package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.messages.IMessage;

public interface IMsgListener<PDT, Message extends IMessage> {
    void onMessageReceived(Message message);
    void onInvalidMessageReceived(PDT invalidMessageData);
}