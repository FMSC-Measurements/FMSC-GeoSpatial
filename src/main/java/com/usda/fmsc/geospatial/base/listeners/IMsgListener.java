package com.usda.fmsc.geospatial.base.listeners;

import com.usda.fmsc.geospatial.base.messages.IMessage;

public interface IMsgListener<PDT, T extends IMessage> {
    void onMessageReceived(T message);
    void onInvalidMessageReceived(PDT invalidMessageData);
}