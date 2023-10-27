package com.usda.fmsc.geospatial.gnss.nmea;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.codes.EastWest;
import com.usda.fmsc.geospatial.codes.NorthSouth;
import com.usda.fmsc.geospatial.codes.UomElevation;
import com.usda.fmsc.geospatial.nmea.INmeaBurst;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;
import com.usda.fmsc.geospatial.utm.UTMCoords;

import org.joda.time.DateTime;

import java.util.ArrayList;

public interface IGnssNmeaBurst extends INmeaBurst<NmeaSentence> {
    boolean isValid();

    boolean isValid(SentenceID sentenceID);

    boolean isComplete();

    DateTime getFixTime();

    Double getMagVar();

    EastWest getMagVarDir();

    Double getTrackAngle();

    Double getGroundSpeed();

    Position getPosition();

    boolean hasPosition();

    double getLatitude();

    NorthSouth getLatDir();

    double getLongitude();

    EastWest getLonDir();

    double getElevation();

    boolean hasElevation();

    UomElevation getUomElevation();

    UTMCoords getTrueUTM();

    UTMCoords getUTM(int utmZone);

    double getHorizDilution();

    double getGeoidHeight();

    UomElevation getGeoUom();

    int getTrackedSatellitesCount();

    ArrayList<Satellite> getSatellitesInView();

    int getSatellitesInViewCount();

    ArrayList<Integer> getUsedSatelliteIDs();

    int getUsedSatellitesCount();

    Float getHDOP();

    Float getPDOP();

    Float getVDOP();
}
