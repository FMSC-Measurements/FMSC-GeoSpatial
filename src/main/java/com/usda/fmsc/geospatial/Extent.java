package com.usda.fmsc.geospatial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
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


    public Position getCenter() {
        return new Position(
            ((northEast.getLatitude() - southWest.getLatitude()) / 2d),
            ((northEast.getLongitude() - southWest.getLongitude()) / 2d)
        );
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


    public static class Builder {
        private final List<Position> positions = new ArrayList<>();

        public void include(Position position) {
            positions.add(position);
        }

        public void include(Extent extent) {
            positions.add(extent.getNorthEast());
            positions.add(extent.getSouthWest());
        }

        public Extent build() {
            double north = -90, south = 90, east = -180, west = 180;
            double lat, lon;

            if (positions.size() < 1)
                throw new RuntimeException("No positions");

            for (Position pos : positions) {
                lat = pos.getLatitudeSignedDecimal();
                lon = pos.getLongitudeSignedDecimal();

                if (lat > north)
                    north = lat;

                if (lat < south)
                    south = lat;

                if (lon > east)
                    east = lon;

                if (lon < west)
                    west = lon;
            }

            return new Extent(north, east, south, west);
        }

        public int numberOfPositions() {
            return positions.size();
        }
    }
}
