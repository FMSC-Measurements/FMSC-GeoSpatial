package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.ACCSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.DTVSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.ERRSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.GYRSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.HVESentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.IMUSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.MAGSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.MARSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.QMRSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.QTNSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.UnknownSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.YBASentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.YIASentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.YMRSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.YPRSentence;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;
import com.usda.fmsc.geospatial.nmea.BaseNmeaParser;

public class VNNmeaParser extends BaseNmeaParser<VNNmeaSentence, VNNmeaParserListener> {
  
    public VNNmeaParser() {
        super();
    }

    @Override
    protected VNNmeaSentence parseMessage(String data) {
        MessageID id = MessageID.parse(data);
        
        switch (id) {
            case ACC: return new ACCSentence(id, data);
            case DTV: return new DTVSentence(id, data);
            case ERR: return new ERRSentence(id, data);
            case GYR: return new GYRSentence(id, data);
            case HVE: return new HVESentence(id, data);
            case IMU: return new IMUSentence(id, data);
            case MAG: return new MAGSentence(id, data);
            case MAR: return new MARSentence(id, data);
            case QMR: return new QMRSentence(id, data);
            case QTN: return new QTNSentence(id, data);
            case YBA: return new YBASentence(id, data);
            case YIA: return new YIASentence(id, data);
            case YMR: return new YMRSentence(id, data);
            case YPR: return new YPRSentence(id, data);
            case RRG:
            case WRG: return parseMessageFromRegister(id, data);
            default: return new UnknownSentence(id, data);
        }
    }

    protected VNNmeaSentence parseMessageFromRegister(MessageID id, String data) {
        RegisterID registerID = RegisterID.parse(data);

        switch (registerID) {
            case ACC: return new ACCSentence(id, data);
            case DTV: return new DTVSentence(id, data);
            case GYR: return new GYRSentence(id, data);
            case HVE: return new HVESentence(id, data);
            case IMU: return new IMUSentence(id, data);
            case MAG: return new MAGSentence(id, data);
            case MAR: return new MARSentence(id, data);
            case QMR: return new QMRSentence(id, data);
            case QTN: return new QTNSentence(id, data);
            case YBA: return new YBASentence(id, data);
            case YIA: return new YIASentence(id, data);
            case YMR: return new YMRSentence(id, data);
            case YPR: return new YPRSentence(id, data);
            default: return new UnknownSentence(id, data);
        }
    }
}
