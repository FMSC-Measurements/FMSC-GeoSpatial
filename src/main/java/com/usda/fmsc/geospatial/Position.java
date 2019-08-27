package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Position implements Serializable {
    private double latitude;
    private NorthSouth latDir;
    private double longitude;
    private EastWest lonDir;
    private Double elevation;
    private UomElevation uomElevation;

    public Position(Position position) {
        this.latitude = position.latitude;
        this.latDir = position.latDir;
        this.longitude = position.longitude;
        this.lonDir = position.lonDir;
        this.elevation = position.elevation;
        this.uomElevation = position.uomElevation;
    }

    public Position(double latitude, double longitude) {
        setPosition(latitude, null, longitude, null, 0, null);
    }

    public Position(double latitude, double longitude, double elevation, UomElevation uomElevation) {
        setPosition(latitude, null, longitude, null, elevation, uomElevation);
    }

    public Position(double latitude, NorthSouth latDir, double longitude, EastWest lonDir) {
        setPosition(latitude, latDir, longitude, lonDir, 0, null);
    }

    public Position(double latitude, NorthSouth latDir, double longitude, EastWest lonDir, double elevation, UomElevation uomElevation) {
        setPosition(latitude, latDir, longitude, lonDir, elevation, uomElevation);
    }

    private void setPosition(double latitude, NorthSouth latDir, double longitude, EastWest lonDir, double elevation, UomElevation uomElevation) {
        if (latDir != null) {
            this.latitude = latitude;
            this.latDir = latDir;
        } else {
            this.latitude = Math.abs(latitude);
            this.latDir = latitude < 0 ? NorthSouth.South : NorthSouth.North;
        }

        if (lonDir != null) {
            this.longitude = longitude;
            this.lonDir = lonDir;
        } else {
            this.longitude = Math.abs(longitude);
            this.lonDir = longitude < 0 ? EastWest.West : EastWest.East;
        }

        this.elevation = elevation;
        this.uomElevation = uomElevation;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLatitudeSignedDecimal() {
        return latDir == NorthSouth.North ? latitude : latitude * -1;
    }

    public DMS getLatitudeDMS() {
        return new DMS(getLatitudeSignedDecimal());
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }
    public double getLongitudeSignedDecimal() {
        return lonDir == EastWest.East ? longitude : longitude * -1;
    }

    public DMS getLongitudeDMS() {
        return new DMS(getLongitudeSignedDecimal());
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public NorthSouth getLatDir() {
        return latDir;
    }

    public void setLatDir(NorthSouth latDir) {
        this.latDir = latDir;
    }


    public EastWest getLonDir() {
        return lonDir;
    }

    public void setLonDir(EastWest lonDir) {
        this.lonDir = lonDir;
    }


    public boolean hasElevation() {
        return uomElevation != null;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public UomElevation getUomElevation() {
        return uomElevation;
    }

    public void setUomElevation(UomElevation uomElevation) {
        this.uomElevation = uomElevation;
    }

    public boolean isNorthernHemisphere() {
        return latDir == NorthSouth.North;
    }

    public boolean isSouthernHemisphere() {
        return latDir == NorthSouth.South;
    }

    public boolean isWesternHemisphere() {
        return lonDir == EastWest.West;
    }

    public boolean isEasternHemisphere() {
        return lonDir == EastWest.East;
    }


    public static double fromDecimalDMS(double dms) {
        dms = Math.abs(dms / 100);
        double deg = (int)dms;

        double decMinAndSec = (dms - deg) * 100d;
        int decMin = (int)decMinAndSec;
        double decSec = (decMinAndSec - decMin) * 60d;

        return deg + decMin / 60d + decSec / 3600d;
    }


    public static Position fromDecimalDms(double lat, double lon) {
        return new Position(fromDecimalDMS(lat) * lat < 0 ? -1 : 1, fromDecimalDMS(lon) * lon < 0 ? -1 : 1);
    }

    public static Position fromDecimalDms(double lat, NorthSouth northSouth, double lon, EastWest eastWest) {
        return new Position(fromDecimalDMS(lat), northSouth, fromDecimalDMS(lon), eastWest);
    }

    public static Position fromDecimalDms(double lat, NorthSouth northSouth, double lon, EastWest eastWest, double elevation, UomElevation uomElevation) {
        return new Position(fromDecimalDMS(lat), northSouth, fromDecimalDMS(lon), eastWest, elevation, uomElevation);
    }
}
