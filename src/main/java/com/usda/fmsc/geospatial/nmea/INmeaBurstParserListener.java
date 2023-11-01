package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.base.listeners.IStringBurstListener;
import com.usda.fmsc.geospatial.nmea.sentences.INmeaSentence;

public interface INmeaBurstParserListener<
    NmeaSentence extends INmeaSentence,
    NmeaBurst extends INmeaBurst<NmeaSentence>> extends IStringBurstListener<NmeaSentence, NmeaBurst> {
}
