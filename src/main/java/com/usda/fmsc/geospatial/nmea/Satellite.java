package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.GnssType;

import java.io.Serializable;

public class Satellite  implements Serializable {
    private final int nmeaId;
    private final Float elevation;
    private final Float azimuth;
    private final Float srn;
    private GnssType gnssType;

    public Satellite(int nmeaId, Float elevation, Float azimuth, Float srn) {
        this.nmeaId = nmeaId;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.srn = srn;
    }

    public int getNmeaID() {
        return nmeaId;
    }

    public Float getElevation() {
        return elevation;
    }

    public Float getAzimuth() {
        return azimuth;
    }

    public Float getSRN() {
        return srn;
    }


    public GnssType getGnssType() {
        if (gnssType == null)
            gnssType = GnssType.parseNmeaId(nmeaId);
        return gnssType;
    }

    public boolean isSBAS() {
        return getGnssType().isSBAS();
    }
}
