package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Latitude extends DMS implements Serializable {
    private NorthSouth hemisphere;


    public Latitude() {
        super();
    }

    public Latitude(Latitude latitude) {
        super(latitude);
        this.hemisphere = latitude.getHemisphere();
    }

    public Latitude(DMS coord, NorthSouth hemisphere) {
        super(coord);
        this.hemisphere = hemisphere;
    }

    public Latitude(double dms) {
        super(dms);
        this.hemisphere = (dms < 0) ? NorthSouth.South : NorthSouth.North;
    }

    public Latitude(double dms, NorthSouth hemisphere) {
        super(dms);
        this.hemisphere = hemisphere;
    }

    public Latitude(int degrees, int minutes, NorthSouth hemisphere) {
        super(degrees, minutes, 0);
        this.hemisphere = hemisphere;
    }

    public Latitude(int degrees, int minutes, double seconds, NorthSouth hemisphere) {
        super(degrees, minutes, seconds);
        this.hemisphere = hemisphere;
    }


    public NorthSouth getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(NorthSouth hemisphere) {
        this.hemisphere = hemisphere;
    }


    public static Latitude fromDecimalDMS(double dms) {
        return new Latitude(DMS.fromDecimalDMS(dms), dms < 0 ? NorthSouth.South : NorthSouth.North);
    }

    public static Latitude fromDecimalDMS(double dms, NorthSouth hemisphere) {
        return new Latitude(DMS.fromDecimalDMS(dms), hemisphere);
    }


    public double toSignedDecimal() {
        return toDecimal() * ((hemisphere == NorthSouth.South) ? -1 : 1);
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), hemisphere.toStringAbv());
    }
}
