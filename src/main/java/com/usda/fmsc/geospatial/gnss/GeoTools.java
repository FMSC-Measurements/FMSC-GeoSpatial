package com.usda.fmsc.geospatial.gnss;

import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.Extent;
import com.usda.fmsc.geospatial.Position;

public class GeoTools {
    public static Position getGeoMidPoint(Position position1, Position position2) {
        ArrayList<Position> arr = new ArrayList<>();

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
            lat = p.getLatitude() * Math.PI / 180.0;
            lon = p.getLongitude() * Math.PI / 180.0;

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

        return new Position(lat * 180.0 / Math.PI, lon * 180.0 / Math.PI);
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

        return new Position(lat * 180.0 / Math.PI, lon * 180.0 / Math.PI);
    }

    public static Position getMidPoint(List<Position> positions) {
        if (positions == null || positions.size() < 1) {
            throw new RuntimeException("No positions to average.");
        }

        Double x, y, z, q;
        x = y = z = q = 0.0;

        int size = positions.size(), elevSize = 0;

        Double lat, lon;
        for (Position p : positions) {
            lat = p.getLatitude() * Math.PI / 180.0;
            lon = p.getLongitude() * Math.PI / 180.0;

            x += Math.cos(lat) * Math.cos(lon);
            y += Math.cos(lat) * Math.sin(lon);
            q += Math.sin(lat);

            if (p.hasElevation()) {
                z += p.getElevation();
                elevSize++;
            }
        }

        x /= size;
        y /= size;
        z /= elevSize;
        q /= size;

        lon = Math.atan2(y, x);
        Double hyp = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        lat = Math.atan2(q, hyp);

        return new Position(lat * 180.0 / Math.PI, lon * 180.0 / Math.PI, z, positions.get(0).getUomElevation());
    }

    public static Position getMidPoint(Extent extent) {
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(extent.getNorthEast());
        positions.add(extent.getSouthWest());
        return getGeoMidPoint(positions);
    }
}
