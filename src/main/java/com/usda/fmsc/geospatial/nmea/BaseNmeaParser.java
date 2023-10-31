package com.usda.fmsc.geospatial.nmea;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;

import com.usda.fmsc.geospatial.base.IIDParser;
import com.usda.fmsc.geospatial.base.parsers.BaseStringMsgParser;
import com.usda.fmsc.geospatial.nmea.exceptions.InvalidChecksumException;
import com.usda.fmsc.geospatial.nmea.exceptions.UnsupportedSentenceException;
import com.usda.fmsc.geospatial.nmea.sentences.INmeaSentence;

public abstract class BaseNmeaParser<
    MessageID extends Enum<MessageID>,
    SentenceID extends Enum<SentenceID>,
    NmeaSentence extends INmeaSentence,
    GnssNmeaBurst extends INmeaBurst<NmeaSentence>,
    Listener extends INmeaParserListener<NmeaSentence, GnssNmeaBurst>,
    MsgIDParser extends IIDParser<MessageID>> extends BaseStringMsgParser<NmeaSentence, Listener> {
    private static final String DEFAULT_DELIMITER = "\r\n";

    private final Supplier<GnssNmeaBurst> supplier;
    private final MsgIDParser msgIDParser;

    private final List<Listener> listeners;
    private final EnumSet<MessageID> usedMsgIDs;

    private GnssNmeaBurst burst;


    public BaseNmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, Class<MessageID> msgIDClass) {
        this(supplier, msgIDParser, EnumSet.allOf(msgIDClass));
    }

    public BaseNmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, MessageID msgID) {
        this(supplier, msgIDParser, EnumSet.of(msgID));
    }

    public BaseNmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, MessageID msgID, String burstDelimiter) {
        this(supplier, msgIDParser, EnumSet.of(msgID), burstDelimiter);
    }

    public BaseNmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<MessageID> msgIDs) {
        super(DEFAULT_DELIMITER);
        this.supplier = supplier;
        this.msgIDParser = msgIDParser;
        this.listeners = new ArrayList<>();
        this.usedMsgIDs = EnumSet.copyOf(msgIDs);
    }

    public BaseNmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<MessageID> msgIDs, String burstDelimiter) {
        super(burstDelimiter);

        this.supplier = supplier;
        this.msgIDParser = msgIDParser;
        this.listeners = new ArrayList<>();
        this.usedMsgIDs = EnumSet.copyOf(msgIDs);
    }

    public BaseNmeaParser(Supplier<GnssNmeaBurst> supplier, MsgIDParser msgIDParser, EnumSet<MessageID> msgIDs, long longestPause) {
        super(longestPause);

        this.supplier = supplier;
        this.msgIDParser = msgIDParser;
        this.listeners = new ArrayList<>();
        this.usedMsgIDs = EnumSet.copyOf(msgIDs);
    }


    @Override
    public boolean checkForDelimiter(String data) {
        return data.contains(getDelimiter());
    }

    @Override
    protected NmeaSentence parseMessage(String data) {
        NmeaSentence sentence = null;
        String rawNmeaSentence = sanitizeNmea(data);

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
    protected void onDelimination() {
        if (burst != null) {
            postBurstReceived(burst);
        }

        burst = supplier.get();
    }


    private void postBurstReceived(GnssNmeaBurst burst) {
        for (Listener listener : listeners) {
            listener.onBurstReceived(burst);
        }
    }

    @Override
    protected void onMessageReceived(NmeaSentence message) {
        super.onMessageReceived(message);

        for (Listener listener : listeners) {
            listener.onNmeaReceived(message);
        }
    }

    
    public static String sanitizeNmea(String nmea) {
        return nmea.replaceAll("[$]>", "");
    }

}
