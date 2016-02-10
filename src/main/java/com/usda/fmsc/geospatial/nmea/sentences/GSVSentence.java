package com.usda.fmsc.geospatial.nmea.sentences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea.sentences.base.MultiSentence;
import com.usda.fmsc.geospatial.nmea.Satellite;

public class GSVSentence extends MultiSentence implements Serializable {
    private int numberOfSatellitesInView;
    private List<Satellite> satellites;


    public GSVSentence() {
        super(SentenceID.GSV);
    }

    public GSVSentence(String nmea) {
        super(SentenceID.GSV, nmea);
    }

    @Override
    public boolean parse(String nmea) {
        valid = false;

        if (isValidNmea(SentenceID.GSV, null, nmea) && super.parse(nmea)) {
            String[] tokens = nmea.substring(0, nmea.indexOf("*")).split(",", -1);

            if (tokens.length > 3) {
                try {
                    int tMessageCount = Integer.parseInt(tokens[1]);

                    if (this.totalMessageCount == 0) {
                        setTotalMessageCount(tMessageCount);
                    } else if (this.totalMessageCount != tMessageCount) {
                        throw new MismatchMessageCountException();
                    }

                    incrementMessageCount();

                    //ignore message id, assuming there are no duplicate messages

                    if (numberOfSatellitesInView == 0) {
                        numberOfSatellitesInView = Integer.parseInt(tokens[3]);
                    }

                    if (satellites == null) {
                        satellites = new ArrayList<>();
                    }

                    Satellite satellite;
                    boolean add;
                    for (int current = 4; current < 16 && current + 3 < tokens.length; current += 4) {
                        add = true;

                        if (tokens[current] != null) {

                            int satID = Integer.parseInt(tokens[current]);

                            satellite = new Satellite(
                                    satID,
                                    parseDouble(tokens[current + 1]),
                                    parseDouble(tokens[current + 2]),
                                    parseDouble(tokens[current + 3])
                            );

                            for (Satellite sat : satellites) {
                                if(satID == sat.getID()) {
                                    //throw new DuplicateSatelliteException(satID);
                                    add = false;
                                    break;
                                }
                            }

                            if (add) {
                                satellites.add(satellite);
                            }
                        }
                    }

                    valid = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return valid;
    }

    public List<Satellite> getSatellites() {
        return satellites;
    }

    public int getSatellitesInViewCount() {
        return numberOfSatellitesInView;
    }


    private Double parseDouble(String input) {
        Double value = null;

        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            //
        }

        return value;
    }
}
