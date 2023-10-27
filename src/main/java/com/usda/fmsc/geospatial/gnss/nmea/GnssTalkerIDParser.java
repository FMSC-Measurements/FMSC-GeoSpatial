package com.usda.fmsc.geospatial.gnss.nmea;

import com.usda.fmsc.geospatial.base.IIDParser;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;

public class GnssTalkerIDParser implements IIDParser<TalkerID> {
    @Override
    public TalkerID parse(int value) {
        return TalkerID.parse(value);
    }
    @Override
    public TalkerID parse(String value) {
        return TalkerID.parse(value);
    }
}