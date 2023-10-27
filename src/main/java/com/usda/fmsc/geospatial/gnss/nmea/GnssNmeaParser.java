package com.usda.fmsc.geospatial.gnss.nmea;

import java.util.EnumSet;

import com.usda.fmsc.geospatial.nmea.INmeaParserListener;
import com.usda.fmsc.geospatial.nmea.NmeaParser;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class GnssNmeaParser extends NmeaParser<TalkerID, SentenceID, GnssNmeaBurst, INmeaParserListener<NmeaSentence, GnssNmeaBurst>, GnssTalkerIDParser> {

    public GnssNmeaParser(TalkerID talkerID) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerID);
    }
    
    public GnssNmeaParser(TalkerID talkerID, String burstDelimiter) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerID, burstDelimiter);
    }

    public GnssNmeaParser(EnumSet<TalkerID> talkerIDs) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerIDs);
    }

    public GnssNmeaParser(EnumSet<TalkerID> talkerIDs, String burstDelimiter) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerIDs, burstDelimiter);
    }

    public GnssNmeaParser(EnumSet<TalkerID> talkerIDs, long longestPause) {
        super(GnssNmeaBurst::new, new GnssTalkerIDParser(), talkerIDs, longestPause);
    }
}

