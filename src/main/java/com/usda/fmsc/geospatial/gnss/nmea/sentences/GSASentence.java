package com.usda.fmsc.geospatial.gnss.nmea.sentences;

import com.usda.fmsc.geospatial.TextUtils;
import com.usda.fmsc.geospatial.codes.Mode;
import com.usda.fmsc.geospatial.gnss.codes.GnssFix;
import com.usda.fmsc.geospatial.gnss.codes.GnssSystem;
import com.usda.fmsc.geospatial.gnss.nmea.GnssNmeaTools;
import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

import java.util.ArrayList;

public class GSASentence extends NmeaSentence {
    private Mode operationMode;
    private GnssFix fix;
    private ArrayList<Integer> satsUsed;
    private Float pdop, hdop, vdop;
    private GnssSystem gnssSystem;

    public GSASentence(TalkerID talkerID, String nmea) {
        super(talkerID, nmea);
    }

    @Override
    public boolean parse(String nmea) {
        satsUsed = new ArrayList<>();

        boolean valid = false;
        String[] tokens = tokenize(nmea);

        if (tokens.length > 17 && tokens[3].length() > 0) {
            try {
                operationMode = Mode.parse(tokens[1]);

                fix = GnssFix.parse(tokens[2]);

                String token;
                for (int i = 3; i < 15; i++) {
                    token = tokens[i];
                    if (!TextUtils.isEmpty(token))
                        satsUsed.add(Integer.parseInt(token));
                }

                token = tokens[15];
                if (!TextUtils.isEmpty(token))
                    pdop = Float.parseFloat(token);

                token = tokens[16];
                if (!TextUtils.isEmpty(token))
                    hdop = Float.parseFloat(token);

                token = tokens[17];
                if (!TextUtils.isEmpty(token))
                    vdop = Float.parseFloat(token);

                if (tokens.length > 19) {
                    token = tokens[18];
                    if (!TextUtils.isEmpty(token))
                        gnssSystem = GnssNmeaTools.getGnssSystemFromNmeaSystemId(Integer.parseInt(token));
                }
                valid = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return valid;
    }

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GSA;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }

    public Mode getOperationMode() {
        return operationMode;
    }

    public GnssFix getFix() {
        return fix;
    }

    public ArrayList<Integer> getSatellitesUsed() {
        return satsUsed;
    }

    public int getSatellitesUsedCount() {
        return satsUsed.size();
    }

    public Float getPDOP() {
        return pdop;
    }

    public Float getHDOP() {
        return hdop;
    }

    public Float getVDOP() {
        return vdop;
    }

    public GnssSystem getGnssSystem() {
        return gnssSystem;
    }

}
