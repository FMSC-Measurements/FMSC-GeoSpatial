package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.base.parsers.BaseStringMsgParser;
import com.usda.fmsc.geospatial.nmea.sentences.INmeaSentence;

public abstract class BaseNmeaParser<
        NmeaSentence extends INmeaSentence, Listener extends INmeaParserListener<NmeaSentence>>
            extends BaseStringMsgParser<NmeaSentence, Listener> {

    public BaseNmeaParser() {
        super();
    }
}
