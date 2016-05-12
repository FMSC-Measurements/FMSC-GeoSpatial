package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Longitude extends DMS implements Serializable {
    private EastWest hemisphere;


    public Longitude() {
        super();
    }

    public Longitude(Longitude longitude) {
        super(longitude);
        this.hemisphere = longitude.getHemisphere();
    }

    public Longitude(DMS coord, EastWest hemisphere) {
        super(coord);
        this.hemisphere = hemisphere;
    }

    public Longitude(double dms) {
        super(dms);
        this.hemisphere = (dms > 0) ? EastWest.East : EastWest.West;
    }

    public Longitude(double dms, EastWest hemisphere) {
        super(dms);
        this.hemisphere = hemisphere;
    }

    public Longitude(int degrees, int minutes, EastWest hemisphere) {
        super(degrees, minutes, 0);
        this.hemisphere = hemisphere;
    }

    public Longitude(int degrees, int minutes, double seconds, EastWest hemisphere) {
        super(degrees, minutes, seconds);
        this.hemisphere = hemisphere;
    }


    public EastWest getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(EastWest hemisphere) {
        this.hemisphere = hemisphere;
    }


    public static Longitude fromDecimalDMS(double dms) {
        return new Longitude(DMS.fromDecimalDMS(dms), dms > 0 ? EastWest.East : EastWest.West);
    }

    public static Longitude fromDecimalDMS(double dms, EastWest hemisphere) {
        return new Longitude(DMS.fromDecimalDMS(dms), hemisphere);
    }


    public double toSignedDecimal() {
        return toDecimal() * ((hemisphere == EastWest.West) ? -1 : 1);
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), hemisphere.toStringAbv());
    }
}