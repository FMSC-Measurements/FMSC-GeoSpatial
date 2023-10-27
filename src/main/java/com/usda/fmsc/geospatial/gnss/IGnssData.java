package com.usda.fmsc.geospatial.gnss;

import com.usda.fmsc.geospatial.codes.Mode;
import com.usda.fmsc.geospatial.gnss.codes.GnssFix;
import com.usda.fmsc.geospatial.gnss.codes.GnssFixQuality;

public interface IGnssData {

    Mode getAcquisitionMode();
    GnssFix getFix();
    GnssFixQuality getFixQuality();


}
