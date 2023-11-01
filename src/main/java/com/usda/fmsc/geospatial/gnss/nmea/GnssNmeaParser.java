package com.usda.fmsc.geospatial.gnss.nmea;

import java.util.EnumSet;
import com.usda.fmsc.geospatial.nmea.INmeaBurstParserListener;
import com.usda.fmsc.geospatial.nmea.NmeaBurstParser;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class GnssNmeaParser extends NmeaBurstParser<
    TalkerID,
    SentenceID,
    GnssNmeaBurst,
    INmeaBurstParserListener<NmeaSentence, GnssNmeaBurst>,
    GnssTalkerIDParser> {

    private static final String DEFAULT_DELIMITER = "$RD1";


    public GnssNmeaParser(EnumSet<TalkerID> talkerIDs) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerIDs, DEFAULT_DELIMITER);
    }

    public GnssNmeaParser(EnumSet<TalkerID> talkerIDs, String burstDelimiter) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerIDs, burstDelimiter);
    }

    public GnssNmeaParser(EnumSet<TalkerID> talkerIDs, long longestPause) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerIDs, longestPause);
    }
}

