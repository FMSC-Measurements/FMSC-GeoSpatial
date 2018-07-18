package com.usda.fmsc.geospatial;

import java.io.Serializable;

@SuppressWarnings("WeakerAccess")
public class Position implements Serializable {
    private Latitude latitude;
    private Longitude longitude;


    public Position() {
        latitude = new Latitude(0);
        longitude = new Longitude(0);
    }

    public Position(Position position) {
        this.latitude = position.getLatitude();
        this.longitude = position.getLongitude();
    }

    public Position(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Position(double latitude, double longitude) {
        setPosition(latitude, null, longitude, null);
    }

    public Position(double latitude, NorthSouth latDir, double longitude, EastWest lonDir) {
        setPosition(latitude, latDir, longitude, lonDir);
    }

    protected void setPosition(Double latitude, NorthSouth latDir, Double longitude, EastWest lonDir) {
        if (latDir != null) {
            this.latitude = new Latitude(latitude, latDir);
        } else {
            this.latitude = new Latitude(latitude);
        }

        if (lonDir != null) {
            this.longitude = new Longitude(longitude, lonDir);
        } else {
            this.longitude = new Longitude(longitude);
        }
    }


    public Latitude getLatitude() {
        return latitude;
    }

    public double getLatitudeSignedDecimal() {
        return latitude.toSignedDecimal();
    }

    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }


    public Longitude getLongitude() {
        return longitude;
    }

    public double getLongitudeSignedDecimal() {
        return longitude.toSignedDecimal();
    }

    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }



    public NorthSouth getLatDir() {
        return latitude.getHemisphere();
    }

    public void setLatDir(NorthSouth latDir) {
        this.latitude.setHemisphere(latDir);
    }


    public EastWest getLonDir() {
        return longitude.getHemisphere();
    }

    public void setLonDir(EastWest lonDir) {
        this.longitude.setHemisphere(lonDir);
    }


    public boolean isNorthernHemisphere() {
        return latitude.getHemisphere() == NorthSouth.North;
    }

    public boolean isWesternHemisphere() {
        return longitude.getHemisphere() == EastWest.West;
    }
}
