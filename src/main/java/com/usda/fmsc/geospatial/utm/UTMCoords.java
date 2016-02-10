package com.usda.fmsc.geospatial.utm;

import java.io.Serializable;

public class UTMCoords implements Serializable {

    private double x, y;
    private int zone;

    public UTMCoords(double x, double y, int zone) {
        this.x = x;
        this.y = y;
        this.zone = zone;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }


    /*
    public void forceZone(int forcedZone) {

    }
    */
}
