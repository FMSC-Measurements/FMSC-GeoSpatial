package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.base.IIDParser;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;

public class VNMsgIDParser implements IIDParser<MessageID> {
    @Override
    public MessageID parse(int value) {
        return MessageID.parse(value);
    }
    @Override
    public MessageID parse(String value) {
        return MessageID.parse(value);
    }
}