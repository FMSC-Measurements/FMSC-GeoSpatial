package com.usda.fmsc.geospatial.nmea.sentences;

import java.io.Serializable;
import java.util.ArrayList;

import com.usda.fmsc.geospatial.nmea.NmeaIDs.SentenceID;
import com.usda.fmsc.geospatial.nmea.sentences.base.MultiSentence;
import com.usda.fmsc.geospatial.nmea.Satellite;

public class GSVSentence extends MultiSentence implements Serializable {
    private int numberOfSatellitesInView;
    private ArrayList<Satellite> satellites;


    public GSVSentence() { }

    public GSVSentence(String nmea) {
        super(nmea);
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
                        try {
                            if (tokens[current] != null) {
                                add = true;

                                int satID = Integer.parseInt(tokens[current]);

                                satellite = new Satellite(
                                        satID,
                                        parseFloat(tokens[current + 1]),
                                        parseFloat(tokens[current + 2]),
                                        parseFloat(tokens[current + 3])
                                );

                                for (Satellite sat : satellites) {
                                    if(satID == sat.getNmeaID()) {
                                        //throw new DuplicateSatelliteException(satID);
                                        add = false;
                                        break;
                                    }
                                }

                                if (add) {
                                    satellites.add(satellite);
                                }
                            }
                        } catch (NumberFormatException e) {
                            //
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

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.GSV;
    }

    public ArrayList<Satellite> getSatellites() {
        return satellites;
    }

    public int getSatellitesInViewCount() {
        return numberOfSatellitesInView;
    }


    private Float parseFloat(String input) {
        Float value = null;

        try {
            value = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            //
        }

        return value;
    }
}
