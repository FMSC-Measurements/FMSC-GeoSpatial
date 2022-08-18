package com.usda.fmsc.geospatial.nmea41.sentences.base;


import com.usda.fmsc.geospatial.EastWest;
import com.usda.fmsc.geospatial.NorthSouth;
import com.usda.fmsc.geospatial.Position;
import com.usda.fmsc.geospatial.UomElevation;
import com.usda.fmsc.geospatial.nmea41.codes.PositionMode;

import java.io.Serializable;

public abstract class PositionSentence extends NmeaSentence implements Serializable {
    protected Position position;
    protected PositionMode positionMode;


    public PositionSentence() {
        super();
    }

    public PositionSentence(String nmea) {
        super(nmea);
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


    public PositionMode getPositionMode() {
        return positionMode;
    }

    public boolean hasPositionMode() {
        return positionMode != null;
    }
}
