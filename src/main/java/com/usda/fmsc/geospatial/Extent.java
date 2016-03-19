package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Extent implements Serializable {
    private Position northEast;
    private Position southWest;


    public Extent(Position northEast, Position southWest) {
        this.northEast = northEast;
        this.southWest = southWest;
    }

    public Extent(double north, double east, double south, double west) {
        this.northEast = new Position(north, east);
        this.southWest = new Position(south, west);
    }

    public Position getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Position northEast) {
        this.northEast = northEast;
    }

    public Position getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Position southWest) {
        this.southWest = southWest;
    }


    public Double getNorth() {
        return northEast.getLatitudeSignedDecimal();
    }

    public Double getEast() {
        return northEast.getLongitudeSignedDecimal();
    }

    public Double getSouth() {
        return southWest.getLatitudeSignedDecimal();
    }

    public Double getWest() {
        return southWest.getLongitudeSignedDecimal();
    }
}
