package com.usda.fmsc.geospatial;

import java.io.Serializable;

public class Longitude extends DMS implements Serializable {
    private Units.EastWest hemisphere;


    public Longitude() {
        super();
    }

    public Longitude(Longitude longitude) {
        super(longitude);
        this.hemisphere = longitude.getHemisphere();
    }

    public Longitude(DMS coord, Units.EastWest hemisphere) {
        super(coord);
        this.hemisphere = hemisphere;
    }

    public Longitude(double dms) {
        super(dms);
        this.hemisphere = (dms > 0) ? Units.EastWest.East : Units.EastWest.West;
    }

    public Longitude(double dms, Units.EastWest hemisphere) {
        super(dms);
        this.hemisphere = hemisphere;
    }

    public Longitude(int degrees, int minutes, Units.EastWest hemisphere) {
        super(degrees, minutes, 0);
        this.hemisphere = hemisphere;
    }

    public Longitude(int degrees, int minutes, double seconds, Units.EastWest hemisphere) {
        super(degrees, minutes, seconds);
        this.hemisphere = hemisphere;
    }


    public Units.EastWest getHemisphere() {
        return hemisphere;
    }

    public void setHemisphere(Units.EastWest hemisphere) {
        this.hemisphere = hemisphere;
    }


    public static Longitude fromDecimalDMS(double dms) {
        return new Longitude(DMS.fromDecimalDMS(dms), dms > 0 ? Units.EastWest.East : Units.EastWest.West);
    }

    public static Longitude fromDecimalDMS(double dms, Units.EastWest hemisphere) {
        return new Longitude(DMS.fromDecimalDMS(dms), hemisphere);
    }


    public double toSignedDecimal() {
        return toDecimal() * ((hemisphere == Units.EastWest.West) ? -1 : 1);
    }

    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), hemisphere.toStringAbv());
    }
}