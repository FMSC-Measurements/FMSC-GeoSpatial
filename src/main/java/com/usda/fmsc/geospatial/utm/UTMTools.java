package com.usda.fmsc.geospatial.utm;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.PositionLegacy;
import com.usda.fmsc.geospatial.Latitude;
import com.usda.fmsc.geospatial.Longitude;
import com.usda.fmsc.geospatial.GeoPosition;

@SuppressWarnings({"WeakerAccess", "unused"})
public class UTMTools {
    public static UTMCoords convertLatLonToUTM(GeoPosition position) {
        return convertLatLonToUTM(position.getLatitude(), position.getLongitude());
    }

    public static UTMCoords convertLatLonToUTM(GeoPosition position, Integer utmToUsed) {
        return convertLatLonToUTM(position.getLatitude(), position.getLongitude(), utmToUsed);
    }

    public static UTMCoords convertLatLonToUTM(PositionLegacy position) {
        return convertLatLonToUTM(position.getLatitude(), position.getLongitude());
    }

    public static UTMCoords convertLatLonToUTM(PositionLegacy position, Integer utmToUsed) {
        return convertLatLonToUTM(position.getLatitude(), position.getLongitude(), utmToUsed);
    }

    public static UTMCoords convertLatLonToUTM(Latitude latitude, Longitude longitude) {
        return convertLatLonSignedDecToUTM(latitude.toSignedDecimal(), longitude.toSignedDecimal(), null);
    }

    public static UTMCoords convertLatLonToUTM(Latitude latitude, Longitude longitude, Integer targetUTM) {
        return convertLatLonSignedDecToUTM(latitude.toSignedDecimal(), longitude.toSignedDecimal(), targetUTM);
    }



    public static UTMCoords convertLatLonToUTM(Position position) {
        return convertLatLonSignedDecToUTM(position.getLatitudeSignedDecimal(), position.getLongitudeSignedDecimal(), null);
    }

    public static UTMCoords convertLatLonToUTM(Position position, Integer utmToUsed) {
        return convertLatLonSignedDecToUTM(position.getLatitudeSignedDecimal(), position.getLongitudeSignedDecimal(), utmToUsed);
    }


    public static UTMCoords convertLatLonSignedDecToUTM(double latitude, double longitude, Integer targetUTM) {
        final double deg2rad = Math.PI / 180;

        double a = 6378137; //WGS84
        double eccSquared = 0.00669438; //WGS84
        double k0 = 0.9996;

        double longitudeOrigin;
        double eccPrimeSquared;
        double N, T, C, A, M;

        //Make sure the longitude is between -180.00 .. 179.9
        double longitudeTemp = (longitude + 180) - ((int)((longitude + 180) / 360)) * 360 - 180; // -180.00 .. 179.9;

        double latitudeRad = latitude * deg2rad;
        double longitudeRad = longitudeTemp * deg2rad;
        double longitudeOriginRad;
        int ZoneNumber;

        if (targetUTM != null) {
            ZoneNumber = targetUTM;
        } else {
            ZoneNumber = ((int) ((longitudeTemp + 180) / 6)) + 1;

            if (latitude >= 56.0 && latitude < 64.0 && longitudeTemp >= 3.0 && longitudeTemp < 12.0)
                ZoneNumber = 32;

            // Special zones for Svalbard
            if (latitude >= 72.0 && latitude < 84.0) {
                if (longitudeTemp >= 0.0 && longitudeTemp < 9.0) ZoneNumber = 31;
                else if (longitudeTemp >= 9.0 && longitudeTemp < 21.0) ZoneNumber = 33;
                else if (longitudeTemp >= 21.0 && longitudeTemp < 33.0) ZoneNumber = 35;
                else if (longitudeTemp >= 33.0 && longitudeTemp < 42.0) ZoneNumber = 37;
            }
        }

        longitudeOrigin = (ZoneNumber - 1) * 6 - 180 + 3; //+3 puts origin in middle of zone
        longitudeOriginRad = longitudeOrigin * deg2rad;

        //compute the UTM Zone from the latitude and longitude
        int Zone = ZoneNumber;

        eccPrimeSquared = (eccSquared) / (1 - eccSquared);

        N = a / Math.sqrt(1 - eccSquared * Math.sin(latitudeRad) * Math.sin(latitudeRad));
        T = Math.tan(latitudeRad) * Math.tan(latitudeRad);
        C = eccPrimeSquared * Math.cos(latitudeRad) * Math.cos(latitudeRad);
        A = Math.cos(latitudeRad) * (longitudeRad - longitudeOriginRad);

        M = a * ((1 - eccSquared / 4 - 3 * eccSquared * eccSquared / 64 - 5 * eccSquared * eccSquared * eccSquared / 256) * latitudeRad
                - (3 * eccSquared / 8 + 3 * eccSquared * eccSquared / 32 + 45 * eccSquared * eccSquared * eccSquared / 1024) * Math.sin(2 * latitudeRad)
                + (15 * eccSquared * eccSquared / 256 + 45 * eccSquared * eccSquared * eccSquared / 1024) * Math.sin(4 * latitudeRad)
                - (35 * eccSquared * eccSquared * eccSquared / 3072) * Math.sin(6 * latitudeRad));

        double UTMEasting = (k0 * N * (A + (1 - T + C) * A * A * A / 6
                + (5 - 18 * T + T * T + 72 * C - 58 * eccPrimeSquared) * A * A * A * A * A / 120)
                + 500000.0);

        double UTMNorthing = (k0 * (M + N * Math.tan(latitudeRad) * (A * A / 2 + (5 - T + 9 * C + 4 * C * C) * A * A * A * A / 24
                + (61 - 58 * T + T * T + 600 * C - 330 * eccPrimeSquared) * A * A * A * A * A * A / 720)));
        if (latitude < 0)
            UTMNorthing += 10000000.0; //10000000 meter offset for southern hemisphere
        
        
        return new UTMCoords(UTMEasting, UTMNorthing, Zone);
    }

    public static Position convertUTMtoLatLonSignedDec(UTMCoords coords) {
        return convertUTMtoLatLonSignedDec(coords.getX(), coords.getY(), coords.getZone());
    }

    public static Position convertUTMtoLatLonSignedDec(double utmX, double utmY, int utmZone) {
        //boolean isNorthHemisphere = true; // utmZone[utmZone.Length - 1] >= 'N';

        double diflat = 0;// -0.00066286966871111111111111111111111111;
        double diflon = 0;// -0.0003868060578;

        double c_sa = 6378137.000000;
        double c_sb = 6356752.314245;
        double e2 = Math.pow((Math.pow(c_sa, 2) - Math.pow(c_sb, 2)), 0.5) / c_sb;
        double e2cuadrada = Math.pow(e2, 2);
        double c = Math.pow(c_sa, 2) / c_sb;
        double x = utmX - 500000;
        double y = utmY;// isNorthHemisphere ? utmY : utmY - 10000000;

        double s = ((utmZone * 6.0) - 183.0);
        double lat = y / (c_sa * 0.9996);
        double v = (c / Math.pow(1 + (e2cuadrada * Math.pow(Math.cos(lat), 2)), 0.5)) * 0.9996;
        double a = x / v;
        double a1 = Math.sin(2 * lat);
        double a2 = a1 * Math.pow((Math.cos(lat)), 2);
        double j2 = lat + (a1 / 2.0);
        double j4 = ((3 * j2) + a2) / 4.0;
        double j6 = ((5 * j4) + Math.pow(a2 * (Math.cos(lat)), 2)) / 3.0;
        double alfa = (3.0 / 4.0) * e2cuadrada;
        double beta = (5.0 / 3.0) * Math.pow(alfa, 2);
        double gama = (35.0 / 27.0) * Math.pow(alfa, 3);
        double bm = 0.9996 * c * (lat - alfa * j2 + beta * j4 - gama * j6);
        double b = (y - bm) / v;
        double epsi = ((e2cuadrada * Math.pow(a, 2)) / 2.0) * Math.pow((Math.cos(lat)), 2);
        double eps = a * (1 - (epsi / 3.0));
        double nab = (b * (1 - epsi)) + lat;
        double senoheps = (Math.exp(eps) - Math.exp(-eps)) / 2.0;
        double delt = Math.atan(senoheps / (Math.cos(nab)));
        double tao = Math.atan(Math.cos(delt) * Math.tan(nab));

        double longitude = ((delt * (180.0 / Math.PI)) + s) + diflon;
        double latitude = ((lat + (1 + e2cuadrada * Math.pow(Math.cos(lat), 2) -
                (3.0 / 2.0) * e2cuadrada * Math.sin(lat) * Math.cos(lat) * (tao - lat)) *
                (tao - lat)) * (180.0 / Math.PI)) + diflat;

        return new Position(latitude, longitude);
    }

    public static GeoPosition convertUTMtoLatLonSignedDecLegacy(double utmX, double utmY, int utmZone) {
        //boolean isNorthHemisphere = true; // utmZone[utmZone.Length - 1] >= 'N';

        double diflat = 0;// -0.00066286966871111111111111111111111111;
        double diflon = 0;// -0.0003868060578;

        double c_sa = 6378137.000000;
        double c_sb = 6356752.314245;
        double e2 = Math.pow((Math.pow(c_sa, 2) - Math.pow(c_sb, 2)), 0.5) / c_sb;
        double e2cuadrada = Math.pow(e2, 2);
        double c = Math.pow(c_sa, 2) / c_sb;
        double x = utmX - 500000;
        double y = utmY;// isNorthHemisphere ? utmY : utmY - 10000000;

        double s = ((utmZone * 6.0) - 183.0);
        double lat = y / (c_sa * 0.9996);
        double v = (c / Math.pow(1 + (e2cuadrada * Math.pow(Math.cos(lat), 2)), 0.5)) * 0.9996;
        double a = x / v;
        double a1 = Math.sin(2 * lat);
        double a2 = a1 * Math.pow((Math.cos(lat)), 2);
        double j2 = lat + (a1 / 2.0);
        double j4 = ((3 * j2) + a2) / 4.0;
        double j6 = ((5 * j4) + Math.pow(a2 * (Math.cos(lat)), 2)) / 3.0;
        double alfa = (3.0 / 4.0) * e2cuadrada;
        double beta = (5.0 / 3.0) * Math.pow(alfa, 2);
        double gama = (35.0 / 27.0) * Math.pow(alfa, 3);
        double bm = 0.9996 * c * (lat - alfa * j2 + beta * j4 - gama * j6);
        double b = (y - bm) / v;
        double epsi = ((e2cuadrada * Math.pow(a, 2)) / 2.0) * Math.pow((Math.cos(lat)), 2);
        double eps = a * (1 - (epsi / 3.0));
        double nab = (b * (1 - epsi)) + lat;
        double senoheps = (Math.exp(eps) - Math.exp(-eps)) / 2.0;
        double delt = Math.atan(senoheps / (Math.cos(nab)));
        double tao = Math.atan(Math.cos(delt) * Math.tan(nab));

        double longitude = ((delt * (180.0 / Math.PI)) + s) + diflon;
        double latitude = ((lat + (1 + e2cuadrada * Math.pow(Math.cos(lat), 2) -
                (3.0 / 2.0) * e2cuadrada * Math.sin(lat) * Math.cos(lat) * (tao - lat)) *
                (tao - lat)) * (180.0 / Math.PI)) + diflat;

        return new GeoPosition(latitude, longitude);
    }
}
