package com.usda.fmsc.geospatial;

import java.util.ArrayList;
import java.util.List;

public class GeoTools {
    public static Position getGeoMidPoint(Position position1, Position position2) {
        ArrayList<Position> arr = new ArrayList<Position>();

        arr.add(position1);
        arr.add(position2);

        return getGeoMidPoint(arr);
    }

    public static Position getGeoMidPoint(List<Position> positions) {
        Double x, y, q;
        x = y = q = 0.0;

        int size = positions.size();

        Double lat, lon;
        for (Position p : positions) {
            lat = p.getLatitude().toSignedDecimal() * Math.PI / 180.0;
            lon = p.getLongitude().toSignedDecimal() * Math.PI / 180.0;

            x += Math.cos(lat) * Math.cos(lon);
            y += Math.cos(lat) * Math.sin(lon);
            q += Math.sin(lat);
        }

        x /= size;
        y /= size;
        q /= size;

        lon = Math.atan2(y, x);
        Double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        lat = Math.atan2(q, hyp);

        return new Position(new Latitude(lat * 180.0 / Math.PI), new Longitude(lon * 180.0 / Math.PI));
    }

    public static Position getGeoMidPoint(List<Double> lats, List<Double> lons) {
        if (lats.size() != lons.size()) {
            throw new RuntimeException("Array size Mismatch");
        }

        Double x, y, q;
        x = y = q = 0.0;

        int size = lats.size();

        Double lat, lon;
        for (int i = 0; i < size; i++) {
            lat = lats.get(i) * Math.PI / 180.0;
            lon = lons.get(i) * Math.PI / 180.0;

            x += Math.cos(lat) * Math.cos(lon);
            y += Math.cos(lat) * Math.sin(lon);
            q += Math.sin(lat);
        }

        x /= size;
        y /= size;
        q /= size;

        lon = Math.atan2(y, x);
        Double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        lat = Math.atan2(q, hyp);

        return new Position(new Latitude(lat * 180.0 / Math.PI), new Longitude(lon * 180.0 / Math.PI));
    }


    public static GeoPosition getMidPioint(List<GeoPosition> positions) {
        if (positions == null || positions.size() < 1) {
            throw new RuntimeException("No positions to average.");
        }

        Double x, y, z, q;
        x = y = z = q = 0.0;

        int size = positions.size();

        Double lat, lon;
        for (GeoPosition p : positions) {
            lat = p.getLatitudeSignedDecimal() * Math.PI / 180.0;
            lon = p.getLongitudeSignedDecimal() * Math.PI / 180.0;

            x += Math.cos(lat) * Math.cos(lon);
            y += Math.cos(lat) * Math.sin(lon);
            q += Math.sin(lat);
            z += p.hasElevation() ? p.getElevation() : 0;
        }

        x /= size;
        y /= size;
        z /= size;
        q /= size;

        lon = Math.atan2(y, x);
        Double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        lat = Math.atan2(q, hyp);

        return new GeoPosition(lat * 180.0 / Math.PI, lon * 180.0 / Math.PI, z, positions.get(0).getUomElevation());
    }

    public static GeoPosition getMidPioint(List<Double> lats, List<Double> lons, List<Double> elevations, UomElevation uomElevation) {
        if (lats == null || lats.size() < 1) {
            throw new RuntimeException("No positions to average.");
        }

        if (lats.size() != lons.size() && lats.size() != elevations.size()) {
            throw new RuntimeException("Array size Mismatch");
        }

        Double x, y, z, q;
        x = y = z = q = 0.0;

        int size = lats.size();

        Double lat, lon;
        for (int i = 0; i < size; i++) {
            lat = lats.get(i) * Math.PI / 180.0;
            lon = lons.get(i) * Math.PI / 180.0;

            x += Math.cos(lat) * Math.cos(lon);
            y += Math.cos(lat) * Math.sin(lon);
            q += Math.sin(lat);
            z += elevations.get(i);
        }

        x /= size;
        y /= size;
        z /= size;
        q /= size;

        lon = Math.atan2(y, x);
        Double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        lat = Math.atan2(q, hyp);

        return new GeoPosition(lat * 180.0 / Math.PI, lon * 180.0 / Math.PI, z, uomElevation);

    }
}
