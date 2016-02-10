package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Latitude extends DMS implements Serializable {
    private Units.NorthSouth hemisphere;


    public Latitude() {
        super();
    }

    public Latitude(Latitude latitude) {
        super(latitude);
        this.hemisphere = latitude.getHemisphere();
    }

    public Latitude(DMS coord, Units.NorthSouth hemisphere) {
        super(coord);
        this.hemisphere = hemisphere;
    }

    public Latitude(double dms) {
        super(dms);
        this.hemisphere = (dms < 0) ? Units.NorthSouth.South : Units.NorthSouth.North;
    }

    public Latitude(double dms, Units.NorthSouth hemisphere) {
        super(dms);
        this.hemisphere = hemisphere;
    }

    public Latitude(int degrees, int minutes, Units.NorthSouth hemisphere) {
        super(degrees, minutes, 0);
        this.hemisphere = hemisphere;
    }

    public Latitude(int degrees, int minutes, double seconds, Units.NorthSouth hemisphere) {
        super(degrees, minutes, seconds);
        this.hemisphere = hemisphere;
    }


    public Units.NorthSouth getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(Units.NorthSouth hemisphere) {
        this.hemisphere = hemisphere;
    }


    public static Latitude fromDecimalDMS(double dms) {
        return new Latitude(DMS.fromDecimalDMS(dms), dms < 0 ? Units.NorthSouth.South : Units.NorthSouth.North);
    }

    public static Latitude fromDecimalDMS(double dms, Units.NorthSouth hemisphere) {
        return new Latitude(DMS.fromDecimalDMS(dms), hemisphere);
    }


    public double toSignedDecimal() {
        return toDecimal() * ((hemisphere == Units.NorthSouth.South) ? -1 : 1);
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), hemisphere.toStringAbv());
    }
}
