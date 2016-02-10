package com.usda.fmsc.geospatial.nmea;

import java.io.Serializable;

public class Satellite  implements Serializable {
    private int id;
    private Double elevation;
    private Double azimuth;
    private Double srn;

    public Satellite(int id, Double elevation, Double azimuth, Double srn) {
        this.id = id;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.srn = srn;
    }

    public int getID() {
        return id;
    }

    public Double getElevation() {
        return elevation;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public Double getSRN() {
        return srn;
    }
}
