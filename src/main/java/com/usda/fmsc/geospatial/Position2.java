package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Position2 implements Serializable {
    private double latitude;
    private NorthSouth latDir;
    private double longitude;
    private EastWest lonDir;
    private Double elevation;
    private UomElevation uomElevation;

    public Position2(Position2 position) {
        this.latitude = position.latitude;
        this.latDir = position.latDir;
        this.longitude = position.longitude;
        this.lonDir = position.lonDir;
        this.elevation = position.elevation;
        this.uomElevation = position.uomElevation;
    }

    public Position2(double latitude, double longitude) {
        setPosition(latitude, null, longitude, null, 0, null);
    }

    public Position2(double latitude, double longitude, double elevation, UomElevation uomElevation) {
        setPosition(latitude, null, longitude, null, elevation, uomElevation);
    }

    public Position2(double latitude, NorthSouth latDir, double longitude, EastWest lonDir) {
        setPosition(latitude, latDir, longitude, lonDir, 0, null);
    }

    public Position2(double latitude, NorthSouth latDir, double longitude, EastWest lonDir, double elevation, UomElevation uomElevation) {
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

    public double getLatitudeSigned() {
        return latDir == NorthSouth.North ? latitude : latitude * -1;
    }

    public DMS getLatitudeDMS() {
        return new DMS(getLatitudeSigned());
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }
    public double getLongitudeSigned() {
        return lonDir == EastWest.East ? longitude : longitude * -1;
    }

    public DMS getLongitudeDMS() {
        return new DMS(getLongitudeSigned());
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
}
