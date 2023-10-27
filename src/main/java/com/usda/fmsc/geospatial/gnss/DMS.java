package com.usda.fmsc.geospatial.gnss;

import java.io.Serializable;

public class DMS implements Serializable {
    protected final String DEGREE_SYMBOL = "\u00b0";

    protected int degrees;
    protected int minutes;
    protected double seconds;

    public DMS() {
        degrees = minutes = -1;
        seconds = -1.0;
    }

    public DMS(DMS dms) {
        this.degrees = dms.getDegrees();
        this.minutes = dms.getMinutes();
        this.seconds = dms.getSeconds();
    }

    public DMS(double dms) {
        dms = Math.abs(dms);
        this.degrees = (int) dms;

        double decMin = (dms - this.degrees) * 60d;

        this.minutes = (int) decMin;
        this.seconds = (decMin - this.minutes) * 60d;
    }

    public DMS(int degrees, int minutes) {
        setCoord(degrees, minutes, 0);
    }

    public DMS(int degrees, int minutes, double seconds) {
        setCoord(degrees, minutes, seconds);
    }

    protected void setCoord(int degrees, int minutes, double seconds) {
        this.degrees = degrees;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public double toDecimal() {
        return this.degrees + this.minutes / 60d + this.seconds / 3600d;
    }

    public static DMS fromDecimal(double dms) {
        DMS cood = new DMS();

        dms = Math.abs(dms);
        cood.setDegrees((int) dms);

        double decMin = (dms - cood.getDegrees()) * 60d;

        cood.setMinutes((int) decMin);
        cood.setSeconds((decMin - cood.getMinutes()) * 60d);
        return cood;
    }

    public static DMS fromDecimalDMS(double dms) {
        DMS cood = new DMS();

        dms = Math.abs(dms / 100);
        cood.setDegrees((int) dms);

        double decMin = (dms - cood.getDegrees()) * 100d;

        cood.setMinutes((int) decMin);
        cood.setSeconds((decMin - cood.getMinutes()) * 60d);
        return cood;
    }

    @Override
    public String toString() {
        return String.format("%d%s %d%s %.2f%s", degrees, DEGREE_SYMBOL, minutes, DEGREE_SYMBOL, seconds,
                DEGREE_SYMBOL);
    }
}
