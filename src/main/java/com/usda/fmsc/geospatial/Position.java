package com.usda.fmsc.geospatial;

import java.io.Serializable;

import com.usda.fmsc.geospatial.codes.EastWest;
import com.usda.fmsc.geospatial.codes.NorthSouth;
import com.usda.fmsc.geospatial.codes.UomElevation;
import com.usda.fmsc.geospatial.gnss.DMS;

public class Position implements IPosition, Serializable {
    private double latitude;
    private double longitude;
    private Double elevation;
    private UomElevation uomElevation;


    public Position() {
        setPosition(0, null, 0, null, 0, null);
    }

    public Position(Position position) {
        this.latitude = position.latitude;
        this.longitude = position.longitude;
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

    public Position(double latitude, NorthSouth latDir, double longitude, EastWest lonDir, double elevation,
            UomElevation uomElevation) {
        setPosition(latitude, latDir, longitude, lonDir, elevation, uomElevation);
    }


    private void setPosition(double latitude, NorthSouth latDir, double longitude, EastWest lonDir, double elevation,
            UomElevation uomElevation) {
            
        this.latitude = latDir != null ? (Math.abs(latitude) * (latDir == NorthSouth.North ? 1 : -1)) : latitude;
        this.longitude = lonDir != null ? (Math.abs(longitude) * (lonDir == EastWest.East ? 1 : -1)) : longitude;

        this.elevation = elevation;
        this.uomElevation = uomElevation;
    }


    public double getLatitude() {
        return latitude;
    }
    
    public DMS getLatitudeDMS() {
        return new DMS(getLatitude());
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLatitude(double latitude, NorthSouth latDir) {
        this.latitude = latDir != null ? (Math.abs(latitude) * (latDir == NorthSouth.North ? 1 : -1)) : latitude;
    }

    public NorthSouth getLatDir() {
        return latitude >= 0 ? NorthSouth.North : NorthSouth.South;
    }


    public double getLongitude() {
        return longitude;
    }

    public DMS getLongitudeDMS() {
        return new DMS(getLongitude());
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLongitude(double longitude, EastWest lonDir) {
        this.longitude = lonDir != null ? (Math.abs(longitude) * (lonDir == EastWest.East ? 1 : -1)) : longitude;
    }

    public EastWest getLonDir() {
        return longitude >= 0 ? EastWest.East : EastWest.West;
    }


    public boolean hasElevation() {
        return uomElevation != null;
    }

    public Double getElevation() {
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

    
    public static double fromDecimalDMS(double dms) {
        dms = Math.abs(dms / 100);
        double deg = (int) dms;

        double decMinAndSec = (dms - deg) * 100d;
        int decMin = (int) decMinAndSec;
        double decSec = (decMinAndSec - decMin) * 60d;

        return deg + decMin / 60d + decSec / 3600d;
    }

    public static Position fromDecimalDms(double lat, double lon) {
        return new Position(fromDecimalDMS(lat) * lat < 0 ? -1 : 1, fromDecimalDMS(lon) * lon < 0 ? -1 : 1);
    }

    public static Position fromDecimalDms(double lat, NorthSouth northSouth, double lon, EastWest eastWest) {
        return new Position(fromDecimalDMS(lat), northSouth, fromDecimalDMS(lon), eastWest);
    }

    public static Position fromDecimalDms(double lat, NorthSouth northSouth, double lon, EastWest eastWest,
            double elevation, UomElevation uomElevation) {
        return new Position(fromDecimalDMS(lat), northSouth, fromDecimalDMS(lon), eastWest, elevation, uomElevation);
    }
}
