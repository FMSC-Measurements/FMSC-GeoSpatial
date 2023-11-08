package com.usda.fmsc.geospatial.nmea;

import java.util.EnumSet;
import java.util.function.Supplier;

import com.usda.fmsc.geospatial.base.IIDParser;
import com.usda.fmsc.geospatial.base.parsers.BaseStringBurstParser;
import com.usda.fmsc.geospatial.nmea.exceptions.InvalidChecksumException;
import com.usda.fmsc.geospatial.nmea.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea.sentences.INmeaSentence;

public abstract class BaseNmeaBurstParser<
    MessageID extends Enum<MessageID>,
    SentenceID extends Enum<SentenceID>,
    NmeaSentence extends INmeaSentence,
    NmeaBurst extends INmeaBurst<NmeaSentence>,
    Listener extends INmeaBurstParserListener<NmeaSentence, NmeaBurst>,
    MsgIDParser extends IIDParser<MessageID>>
        extends BaseStringBurstParser<NmeaSentence, NmeaBurst, Listener> {

    private final Supplier<NmeaBurst> supplier;
    private final MsgIDParser msgIDParser;

    private final EnumSet<MessageID> usedMsgIDs;

    private NmeaBurst burst;

    
    public BaseNmeaBurstParser(Supplier<NmeaBurst> supplier, MsgIDParser msgIDParser, MessageID msgID, String burstDelimiter) {
        this(supplier, msgIDParser, EnumSet.of(msgID), burstDelimiter);
    }

    public BaseNmeaBurstParser(Supplier<NmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<MessageID> msgIDs, String burstDelimiter) {
        super(burstDelimiter);

        this.supplier = supplier;
        this.msgIDParser = msgIDParser;
        this.usedMsgIDs = EnumSet.copyOf(msgIDs);
    }

    public BaseNmeaBurstParser(Supplier<NmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<MessageID> msgIDs, long longestPause) {
        super(longestPause);

        this.supplier = supplier;
        this.msgIDParser = msgIDParser;
        this.usedMsgIDs = EnumSet.copyOf(msgIDs);
    }

    @Override
    protected NmeaSentence parseMessage(String data, Object args) {
        NmeaSentence sentence = null;
        String rawNmeaSentence = NmeaTools.sanitizeNmea(data);

        try {
            MessageID msgID = msgIDParser.parse(rawNmeaSentence);

            if (usedMsgIDs.contains(msgID)) {
                if (burst == null) {
                    burst = supplier.get();
                }
                
                sentence = burst.addNmeaSentence(rawNmeaSentence);
            }
        } catch (UnsupportedSentenceException | InvalidChecksumException e) {
            //
        }

        return sentence;
    }

    @Override
    protected void onBurstDelimination() {
        if (burst != null) {
            postBurstReceived(burst);
        }

        burst = supplier.get();
    }


    private void postBurstReceived(NmeaBurst burst) {
        for (Listener listener : getListeners()) {
            listener.onBurstReceived(burst);
        }
    }
}
