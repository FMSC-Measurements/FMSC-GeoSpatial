package com.usda.fmsc.geospatial.gnss.nmea;

import com.usda.fmsc.geospatial.gnss.codes.GnssSignal;
import com.usda.fmsc.geospatial.gnss.codes.GnssSystem;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;

import java.io.Serializable;
import java.util.EnumSet;

public class Satellite implements Serializable {
    private final int nmeaId, prn;
    private final Float elevation;
    private final Float azimuth;
    private final Float snr;
    private final GnssSystem gnssSystem;
    private final TalkerID talkerID;
    private EnumSet<GnssSignal> signals;

    public Satellite(int nmeaId, Float elevation, Float azimuth, Float snr, TalkerID talkerID) {
        this.nmeaId = nmeaId;
        this.prn = GnssNmeaTools.getPRNfromNmeaId((gnssSystem = GnssNmeaTools.getGnssSystemFromTalkerId(talkerID)), nmeaId);
        this.elevation = elevation;
        this.azimuth = azimuth;
        this.snr = snr;
        this.talkerID = talkerID;
        this.signals = EnumSet.noneOf(GnssSignal.class);
    }

    public int getNmeaID() {
        return nmeaId;
    }

    public int getPRN() {
        return prn;
    }

    public Float getElevation() {
        return elevation;
    }

    public Float getAzimuth() {
        return azimuth;
    }

    public Float getSNR() {
        return snr;
    }

    public GnssSystem getGnssSystem() {
        return gnssSystem;
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
        return GnssNmeaTools.isSBASfromNmeaId(gnssSystem, nmeaId);
    }
}
