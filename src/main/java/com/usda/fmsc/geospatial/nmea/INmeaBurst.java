package com.usda.fmsc.geospatial.nmea;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.GeoPosition;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea.sentences.GGASentence;
import com.usda.fmsc.geospatial.nmea.sentences.GSASentence;
import com.usda.fmsc.geospatial.nmea.sentences.base.NmeaSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;

import org.joda.time.DateTime;

import java.util.ArrayList;

public interface INmeaBurst {
    NmeaSentence addNmeaSentence(String sentence);

    boolean isValid();

    boolean isValid(NmeaIDs.SentenceID id);

    boolean isFull();

    DateTime getFixTime();

    double getMagVar();
    EastWest getMagVarDir();

    double getTrackAngle();

    double getGroundSpeed();


    GeoPosition getPosition();
    boolean hasPosition();

    double getLatitude();
    NorthSouth getLatDir();

    double getLongitude();
    EastWest getLonDir();

    double getElevation();
    UomElevation getUomElevation();

    UTMCoords getTrueUTM();
    UTMCoords getUTM(int utmZone);


    double getHorizDilution();
    double getGeoidHeight();

    UomElevation getGeoUom();

    GGASentence.GpsFixType getFixQuality();

    int getTrackedSatellitesCount();

    ArrayList<Satellite> getSatellitesInView();

    int getSatellitesInViewCount();

    ArrayList<Integer> getUsedSatelliteIDs();

    int getUsedSatellitesCount();

    GSASentence.Fix getFix();

    GSASentence.Mode getMode();

    double getHDOP();

    double getPDOP();

    double getVDOP();
}
