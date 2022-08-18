package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.utm.UTMCoords;

import org.joda.time.DateTime;

import java.util.ArrayList;

@SuppressWarnings("unused")
public interface INmeaBurst {
    //NmeaSentence addNmeaSentence(String sentence);

    boolean isValid();
    boolean isValid(NmeaIDs.SentenceID sentenceID);

    boolean isComplete();

    DateTime getFixTime();

    Double getMagVar();
    EastWest getMagVarDir();

    Double getTrackAngle();

    Double getGroundSpeed();


    Position getPosition();
    boolean hasPosition();

    double getLatitude();
    double getLatitudeSD();
    NorthSouth getLatDir();

    double getLongitude();
    double getLongitudeSD();
    EastWest getLonDir();

    double getElevation();
    boolean hasElevation();
    UomElevation getUomElevation();

    UTMCoords getTrueUTM();
    UTMCoords getUTM(int utmZone);


    double getHorizDilution();
    double getGeoidHeight();

    UomElevation getGeoUom();

    //GGASentence.GpsFixType getFixQuality();

    int getTrackedSatellitesCount();

    ArrayList<Satellite> getSatellitesInView();

    int getSatellitesInViewCount();

    ArrayList<Integer> getUsedSatelliteIDs();

    int getUsedSatellitesCount();

    //GSASentence.Fix getFix();

    //GSASentence.Mode getOperationMode();

    Float getHDOP();

    Float getPDOP();

    Float getVDOP();
}
