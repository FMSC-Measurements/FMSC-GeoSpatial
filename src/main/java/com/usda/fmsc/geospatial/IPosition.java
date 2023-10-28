package com.usda.fmsc.geospatial;

import com.usda.fmsc.geospatial.codes.EastWest;
import com.usda.fmsc.geospatial.codes.NorthSouth;
import com.usda.fmsc.geospatial.codes.UomElevation;

public interface IPosition {
    public double getLatitude();
    public DMS getLatitudeDMS();
    public NorthSouth getLatDir();

    public double getLongitude();
    public DMS getLongitudeDMS();
    public EastWest getLonDir();

    public boolean hasElevation();
    public Double getElevation();
    public UomElevation getUomElevation();
}
