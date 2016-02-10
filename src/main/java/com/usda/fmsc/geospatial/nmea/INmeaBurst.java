package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.GeoPosition;
import com.usda.fmsc.geospatial.nmea.sentences.GGASentence;
import com.usda.fmsc.geospatial.nmea.sentences.GSASentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;
import com.usda.fmsc.geospatial.Units;

import org.joda.time.DateTime;

import java.util.List;

public interface INmeaBurst {

    DateTime getFixTime();

    double getMagVar();
    Units.EastWest getMagVarDir();

    double getTrackAngle();

    double getGroundSpeed();


    GeoPosition getPosition();
    boolean hasPosition();

    double getLatitude();
    Units.NorthSouth getLatDir();

    double getLongitude();
    Units.EastWest getLonDir();

    double getElevation();
    Units.UomElevation getUomElevation();

    UTMCoords getTrueUTM();
    UTMCoords getUTM(int utmZone);


    double getHorizDilution();
    double getGeoidHeight();

    Units.UomElevation getGeoUom();

    GGASentence.GpsFixType getFixQuality();

    int getTrackedSatellitesCount();

    List<Satellite> getSatellitesInView();

    int getSatellitesInViewCount();

    List<Integer> getUsedSatelliteIDs();

    int getUsedSatellitesCount();

    GSASentence.Fix getFix();

    GSASentence.Mode getMode();

    double getHDOP();

    double getPDOP();

    double getVDOP();
}
