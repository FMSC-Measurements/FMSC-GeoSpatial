package com.usda.fmsc.geospatial;

import java.io.Serializable;

@SuppressWarnings("unused")
public class GeoPosition extends PositionLegacy implements Serializable {
    private Double elevation;
    private UomElevation uomElevation;


    public GeoPosition() {
        super();
    }

    public GeoPosition(PositionLegacy position) {
        super(position);
    }

    public GeoPosition(GeoPosition position) {
        super(position);
        setElevation(position.getElevation(), position.getUomElevation());
    }

    public GeoPosition(double latitude, double longitude) {
        super(latitude, longitude);
    }

    public GeoPosition(double latitude, double longitude, Double elevation, UomElevation uomElevation) {
        super(latitude, longitude);
        this.elevation = elevation;
        this.uomElevation = uomElevation;
    }

    public GeoPosition(double latitude, NorthSouth latDir, double longitude, EastWest lonDir) {
        super(latitude, latDir, longitude, lonDir);
    }

    public GeoPosition(double latitude, NorthSouth latDir, double longitude, EastWest lonDir, Double elevation, UomElevation uomElevation) {
        super(latitude, latDir, longitude, lonDir);
        setElevation(elevation, uomElevation);
    }

    public GeoPosition(Latitude latitude, Longitude longitude) {
        super(latitude, longitude);
    }

    public GeoPosition(Latitude latitude, Longitude longitude, Double elevation, UomElevation uomElevation) {
        super(latitude, longitude);
        setElevation(elevation, uomElevation);
    }


    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public void setElevation(Double elevation, UomElevation uomElevation) {
        this.elevation = elevation;
        this.uomElevation = uomElevation;
    }

    public UomElevation getUomElevation() {
        return uomElevation;
    }

    public void setUomElevation(UomElevation uomElevation) {
        this.uomElevation = uomElevation;
    }


    public boolean hasElevation() {
        return elevation != null && uomElevation != null;
    }
}