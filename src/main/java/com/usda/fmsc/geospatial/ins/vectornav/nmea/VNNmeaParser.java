package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import java.util.EnumSet;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;
import com.usda.fmsc.geospatial.nmea.BaseNmeaParser;

public class VNNmeaParser extends BaseNmeaParser<MessageID, RegisterID, VNNmeaSentence, VNNmeaBurst, VNNmeaParserListener, VNMsgIDParser> {
  
    public VNNmeaParser() {
        super(VNNmeaBurst::new, new VNMsgIDParser(), MessageID.class);
    }  
    
    public VNNmeaParser(MessageID msgId) {
        super(VNNmeaBurst::new, new VNMsgIDParser(), msgId);
    }

    public VNNmeaParser(EnumSet<MessageID> talkerIDs) {
        super(VNNmeaBurst::new, new VNMsgIDParser(), talkerIDs);
    }
}
