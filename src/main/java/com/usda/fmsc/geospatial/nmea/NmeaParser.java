package com.usda.fmsc.geospatial.nmea;

import java.util.EnumSet;
import java.util.function.Supplier;

import com.usda.fmsc.geospatial.base.IIDParser;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class NmeaParser<
    TalkerID extends Enum<TalkerID>,
    SentenceID extends Enum<SentenceID>,
    GnssNmeaBurst extends INmeaBurst<NmeaSentence>,
    Listener extends INmeaParserListener<NmeaSentence, GnssNmeaBurst>,
    MsgIDParser extends IIDParser<TalkerID>>
    extends BaseNmeaParser<TalkerID, SentenceID, NmeaSentence, GnssNmeaBurst, Listener, MsgIDParser> {


    public NmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, Class<TalkerID> talkerIDClass) {
        super(supplier, msgIDParser, talkerIDClass);
    }

    public NmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, TalkerID talkerID) {
        super(supplier, msgIDParser, talkerID);
    }

    public NmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, TalkerID talkerID, String burstDelimiter) {
        super(supplier, msgIDParser, talkerID, burstDelimiter);
    }

    public NmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<TalkerID> talkerIDs) {
        super(supplier, msgIDParser, talkerIDs);
    }

    public NmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<TalkerID> talkerIDs, String burstDelimiter) {
        super(supplier, msgIDParser, talkerIDs, burstDelimiter);
    }

    public NmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<TalkerID> talkerIDs, long longestPause) {
        super(supplier, msgIDParser, talkerIDs, longestPause);
    }
}