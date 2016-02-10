package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class GeoPosition extends Position implements Serializable {
    private Double elevation;
    private Units.UomElevation uomElevation;


    public GeoPosition() {
        super();
    }

    public GeoPosition(Position position) {
        super(position);
    }

    public GeoPosition(GeoPosition position) {
        super(position);
        setElevation(position.getElevation(), position.getUomElevation());
    }

    public GeoPosition(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public GeoPosition(double latitude, double longitude, double elevation, Units.UomElevation uomElevation) {
        super(latitude, longitude);
        this.elevation = elevation;
        this.uomElevation = uomElevation;
    }

    public GeoPosition(double latitude, Units.NorthSouth latDir, double longitude, Units.EastWest lonDir) {
        super(latitude, latDir, longitude, lonDir);
    }

    public GeoPosition(double latitude, Units.NorthSouth latDir, double longitude, Units.EastWest lonDir, double elevation, Units.UomElevation uomElevation) {
        super(latitude, latDir, longitude, lonDir);
        setElevation(elevation, uomElevation);
    }

    public GeoPosition(Latitude latitude, Longitude longitude) {
        super(latitude, longitude);
    }

    public GeoPosition(Latitude latitude, Longitude longitude, double elevation, Units.UomElevation uomElevation) {
        super(latitude, longitude);
        setElevation(elevation, uomElevation);
    }


    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public void setElevation(Double elevation, Units.UomElevation uomElevation) {
        this.elevation = elevation;
        this.uomElevation = uomElevation;
    }

    public Units.UomElevation getUomElevation() {
        return uomElevation;
    }

    public void setUomElevation(Units.UomElevation uomElevation) {
        this.uomElevation = uomElevation;
    }


    public boolean hasElevation() {
        return elevation != null && uomElevation != null;
    }
}