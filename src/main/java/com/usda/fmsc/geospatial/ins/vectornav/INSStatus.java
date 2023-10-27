package com.usda.fmsc.geospatial.ins.vectornav;

import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.GpsError;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.GpsMode;

//todo change for vpe to ins
public class INSStatus {
    private static final int MODE_MASK = 0b11;
    private static final int GPS_FIX_MASK = 1 << 2;
    private static final int ERROR_OFFSET = 3;
    private static final int GPS_HEADERING_INS_MASK = 1 << 8;
    private static final int GPS_COMPASS_MASK = 1 << 9;


    private short statusData;

    public INSStatus(short data) {
        this.statusData = data;
    }


    public short getValue() {
        return statusData;
    }


    public GpsMode getGpsMode() {
        return GpsMode.parse(statusData & MODE_MASK);
    }

    public boolean hasProperFix() {
        return (statusData & GPS_FIX_MASK) == GPS_FIX_MASK;
    }

    public GpsError getGpsError() {
        return GpsError.parse((statusData >> ERROR_OFFSET) & 0b11);
    }
    
    public boolean hasGpsHeadingINS() {
        return (statusData & GPS_HEADERING_INS_MASK) == GPS_HEADERING_INS_MASK;
    }

    public boolean hasGpsCompass() {
        return (statusData & GPS_COMPASS_MASK) == GPS_COMPASS_MASK;
    }
}
