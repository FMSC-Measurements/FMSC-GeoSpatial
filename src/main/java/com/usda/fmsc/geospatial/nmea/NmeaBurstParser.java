package com.usda.fmsc.geospatial.nmea;

import java.util.EnumSet;
import java.util.function.Supplier;

import com.usda.fmsc.geospatial.base.IIDParser;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public abstract class NmeaBurstParser<
    TalkerID extends Enum<TalkerID>,
    SentenceID extends Enum<SentenceID>,
    NmeaBurst extends INmeaBurst<NmeaSentence>,
    Listener extends INmeaBurstParserListener<NmeaSentence, NmeaBurst>,
    MsgIDParser extends IIDParser<TalkerID>>
    extends BaseNmeaBurstParser<TalkerID, SentenceID, NmeaSentence, NmeaBurst, Listener, MsgIDParser> {

        
    public NmeaBurstParser(Supplier<NmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<TalkerID> talkerIDs, String burstDelimiter) {
        super(supplier, msgIDParser, talkerIDs, burstDelimiter);
    }

    public NmeaBurstParser(Supplier<NmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<TalkerID> talkerIDs, long longestPause) {
        super(supplier, msgIDParser, talkerIDs, longestPause);
    }
}