package com.usda.fmsc.geospatial.ins.vectornav.nmea;

import com.usda.fmsc.geospatial.ins.vectornav.codes.MessageID;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;
import com.usda.fmsc.geospatial.ins.vectornav.commands.AsyncOutputPauseCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.BinaryOutputPollCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.FirmwareUpdateCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.ReadRegisterCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.ResetCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.RestoreFactorySettingsCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.SerialCommandPromptCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNBaseCommand;
import com.usda.fmsc.geospatial.ins.vectornav.commands.WriteRegisterCommand;
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
import com.usda.fmsc.geospatial.nmea.NmeaTools;

public class VNNmeaTools {
    public static boolean isAsyncMsg(MessageID messageID) {
        switch (messageID) {
            case ACC:
            case DTV: 
            case ERR:
            case GYR:
            case HVE:
            case IMU:
            case MAG:
            case MAR:
            case QMR:
            case QTN:
            case YBA:
            case YIA:
            case YMR:
            case YPR: return true;
            default: return false;
        }
    }

    public static boolean isAsyncMsg(RegisterID regID) {
        switch (regID) {
            case ACC:
            case DTV: 
            case GYR:
            case HVE:
            case IMU:
            case MAG:
            case MAR:
            case QMR:
            case QTN:
            case YBA:
            case YIA:
            case YMR:
            case YPR: return true;
            default: return false;
        }
    }

    public static VNNmeaSentence parseSentence(String data) {
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

    public static VNNmeaSentence parseMessageFromRegister(MessageID id, String data) {
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

    
    public static boolean isCommandMessage(String data) {
        MessageID messageID = MessageID.parse(data);
        switch (messageID) {
            case RFS:
            case RST:
            case FWU:
            case CMD:
            case ASY:
            case BOM:
                return true;
            case RRG:
            case WRG: return isAsyncMsg(RegisterID.parse(data));
            default:
                break;
        }
        
        return false;
    }

    public static VNBaseCommand parseCommand(String data) {
        switch (MessageID.parse(data)) {
            case RFS: return new RestoreFactorySettingsCommand();
            case RST: return new ResetCommand();
            case FWU: return new FirmwareUpdateCommand();
            case CMD: return new SerialCommandPromptCommand();
            case ASY: return AsyncOutputPauseCommand.parse(data);
            case BOM: return BinaryOutputPollCommand.parse(data);
            case RRG: return ReadRegisterCommand.parse(data);
            case WRG: return WriteRegisterCommand.parse(data);
            default:
                return null;
        }
    }

    public static boolean validateChecksum(String nmea) {
        return (nmea.length() > 10 && nmea.endsWith("XX")) || NmeaTools.validateChecksum(nmea);
    }
}
