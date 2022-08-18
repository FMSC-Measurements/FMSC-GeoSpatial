package com.usda.fmsc.geospatial.nmea41;

import com.usda.fmsc.geospatial.GnssType;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.GnssSignal;
import com.usda.fmsc.geospatial.nmea41.NmeaIDs.TalkerID;

import java.io.Serializable;
import java.util.EnumSet;

public class Satellite implements Serializable {
    private int nmeaId;
    private final int origNmeaId;
    private final Float elevation;
    private final Float azimuth;
    private final Float srn;
    private GnssType gnssType;
    private final TalkerID talkerID;
    private final EnumSet<GnssSignal> signals;

    public Satellite(int nmeaId, Float elevation, Float azimuth, Float srn, TalkerID talkerID) {
        this.origNmeaId = this.nmeaId = nmeaId;
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.srn = srn;
        this.talkerID = talkerID;
        this.signals = EnumSet.noneOf(GnssSignal.class);

        if (talkerID == TalkerID.GL && this.nmeaId < 33) {
            this.nmeaId += 64;
        }
    }

    public int getNmeaID() {
        return nmeaId;
    }

    public int getOrigNmeaId() {
        return origNmeaId;
    }

    public Float getElevation() {
        return elevation;
    }

    public Float getAzimuth() {
        return azimuth;
    }

    public Float getSRN() {
        return srn;
    }


    public GnssType getGnssType() {
        if (gnssType == null) {
            gnssType = GnssType.parseNmeaId(nmeaId);
        }

        return gnssType;
    }

    public TalkerID getTalkerID() {
        return talkerID;
    }

    public EnumSet<GnssSignal> getSignals() {
        return signals;
    }

    public void addSignal(GnssSignal signal) {
        signals.add(signal);
    }

    public void addSignals(EnumSet<GnssSignal> signals) {
        this.signals.addAll(signals);
    }

    public boolean isSBAS() {
        return getGnssType().isSBAS();
    }
}
