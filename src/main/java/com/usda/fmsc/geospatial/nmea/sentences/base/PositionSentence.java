package com.usda.fmsc.geospatial.nmea.sentences.base;


import java.io.Serializable;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.Latitude;
import com.usda.fmsc.geospatial.Longitude;
import com.usda.fmsc.geospatial.GeoPosition;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea.NmeaIDs.*;

public abstract class PositionSentence extends NmeaSentence implements Serializable {
    protected Latitude latitude;
    protected Longitude longitude;
    protected Double elevation;
    protected UomElevation uomElevation;


    public PositionSentence() {
        super();
    }

    public PositionSentence(String nmea) {
        super(nmea);
    }


    public Latitude getLatitude() {
        return latitude;
    }

    public NorthSouth getLatDir() {
        return latitude.getHemisphere();
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public EastWest getLonDir() {
        return longitude.getHemisphere();
    }

    public Double getElevation() {
        return elevation;
    }

    public UomElevation getUomElevation() {
        return uomElevation;
    }


    public GeoPosition getPosition() {
        if (hasPosition()) {
            return new GeoPosition(latitude, longitude, elevation, uomElevation);
        }

        return null;
    }

    public boolean isNortherHemisphere() {
        return latitude.getHemisphere() == NorthSouth.North;
    }

    public boolean isWesternHemisphere() {
        return longitude.getHemisphere() == EastWest.West;
    }

    public boolean hasPosition() {
        return latitude != null && longitude != null;
    }

    public boolean hasElevation() {
        return uomElevation != null;
    }

}
