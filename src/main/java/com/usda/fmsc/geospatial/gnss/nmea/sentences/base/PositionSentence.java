package com.usda.fmsc.geospatial.gnss.nmea.sentences.base;

import java.util.ArrayList;

import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.codes.EastWest;
import com.usda.fmsc.geospatial.codes.NorthSouth;
import com.usda.fmsc.geospatial.codes.UomElevation;
import com.usda.fmsc.geospatial.gnss.nmea.codes.PositionMode;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public abstract class PositionSentence extends NmeaSentence {
    protected Position position;
    protected ArrayList<PositionMode> positionModes;

    
    public PositionSentence(TalkerID talkerID, String nmea) {
        super(talkerID, nmea);
    }


    public double getLatitude() {
        return position.getLatitude();
    }

    public NorthSouth getLatDir() {
        return position.getLatDir();
    }

    public double getLongitude() {
        return position.getLongitude();
    }

    public EastWest getLonDir() {
        return position.getLonDir();
    }

    public double getElevation() {
        return position.getElevation();
    }

    public UomElevation getUomElevation() {
        return position.getUomElevation();
    }

    public Position getPosition() {
        if (hasPosition()) {
            return new Position(position);
        }

        return null;
    }

    public boolean hasPosition() {
        return position != null;
    }

    public boolean hasElevation() {
        return position.hasElevation();
    }

    public ArrayList<PositionMode> getPositionModes() {
        return positionModes;
    }

    public boolean hasPositionModes() {
        return positionModes != null;
    }
}
