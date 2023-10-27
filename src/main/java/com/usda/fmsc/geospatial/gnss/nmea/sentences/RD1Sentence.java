package com.usda.fmsc.geospatial.gnss.nmea.sentences;

import com.usda.fmsc.geospatial.nmea.codes.SentenceID;
import com.usda.fmsc.geospatial.nmea.codes.TalkerID;
import com.usda.fmsc.geospatial.nmea.sentences.NmeaSentence;

public class RD1Sentence extends NmeaSentence {
    private int gpsSecOfWeek;
    private int gpsWeekNumber;  
    private double freq;        //L-band Frequency
    private boolean dspLock;   //display lock
    private int ber1, ber2;     //Bit Error Rate of SBAS being used
    private int agc;            //L-band signal strength
    private double dds;
    private int doppler;
    private int dspStatMask;    //Status bit mask for the DSP tracking of SBAS
    private int armStatMask;    //Status bit mask for the ARM GPS solution
    private int diffStat;       //PRN of the satellite in use
    private int[] navcon;       //Series of hex character fields with each field representing the number of GPS satellites satisfying a certain condition


    public RD1Sentence(String nmea) {
        super(TalkerID.RD1, nmea);
    }


    @Override
    protected boolean parse(String nmea) {
        boolean valid = false;
        String[] tokens = nmea.split(",");

        if (tokens.length > 11 && tokens[2].length() > 0) {
            try {
                gpsSecOfWeek = Integer.parseInt(tokens[1]);

                gpsWeekNumber = Integer.parseInt(tokens[2]);

                freq = Double.parseDouble(tokens[3]);

                dspLock = Boolean.parseBoolean(tokens[4]);

                String[] bers = tokens[5].split("-");
                if (bers.length > 1) {
                    ber1 = Integer.parseInt(bers[0]);
                    ber2 = Integer.parseInt(bers[1]);
                }

                agc = Integer.parseInt(tokens[6]);

                dds = Double.parseDouble(tokens[7]);

                doppler = Integer.parseInt(tokens[8]);

                dspStatMask = Integer.parseInt(tokens[9], 16);

                armStatMask = Integer.parseInt(tokens[10], 16);

                diffStat = Integer.parseInt(tokens[11]);

                String navConStr = tokens[12].trim();
                navcon = new int[navConStr.length()];

                int nci = 0;
                for (int i = navConStr.length() - 1; i >= 0; i--) {
                     navcon[nci++] = Character.digit(navConStr.charAt(i), 16);
                }

                valid = true;
            } catch (Exception ex) {
                // ex.printStackTrace();
            }
        }

        return valid;
    }
    

    @Override
    public SentenceID getSentenceID() {
        return SentenceID.Unknown;
    }

    @Override
    public boolean isMultiSentence() {
        return false;
    }


    public int getGpsSecOfWeek() {
        return gpsSecOfWeek;
    }

    public int getGpsWeekNumber() {
        return gpsWeekNumber;
    }

    public double getLBandFreq() {
        return freq;
    }

    public boolean isDspLock() {
        return dspLock;
    }

    public int getBer1() {
        return ber1;
    }

    public int getBer2() {
        return ber2;
    }

    public int getAgc() {
        return agc;
    }

    public double getDds() {
        return dds;
    }

    public int getDoppler() {
        return doppler;
    }


    public int getDspStatMask() {
        return dspStatMask;
    }

    public boolean hasCarrierLock() {
        return (dspStatMask & 1) == 1;
    }

    public boolean isBerOK() {
        return (dspStatMask >> 1 & 1) == 1;
    }

    public boolean atlasOrWaasHasDspLock() {
        return (dspStatMask >> 2 & 1) == 1;
    }

    public boolean isFrameSync1() {
        return (dspStatMask >> 3 & 1) == 1;
    }

    public boolean isTrackMode() {
        return (dspStatMask >> 4 & 1) == 1;
    }


    public int getArmStatMask() {
        return armStatMask;
    }

    public boolean isGpsLock() {
        return (armStatMask & 1) == 1;
    }

    public boolean hasValidDGpsData() {
        return (armStatMask >> 1 & 1) == 1;
    }

    public boolean hasArmLock() {
        return (armStatMask >> 2 & 1) == 1;
    }

    public boolean hasDiffGps() {
        return (armStatMask >> 3 & 1) == 1;
    }

    public boolean hasGoodDiffGps() {
        return (armStatMask >> 4 & 1) == 1;
    }
    

    public int getSbasPrn() {
        return diffStat;
    }


    public int[] getNavcon() {
        return navcon;
    }

    public int getNumberOfValidTrackSats() {
        return navcon.length > 0 ? navcon[0] : -1;
    }

    public int getNumberOfSatsWithEphemeris() {
        return navcon.length > 1 ? navcon[1] : -1;
    }

    public int getNumberOfHealthySats() {
        return navcon.length > 2 ? navcon[1] : -1;
    }

    public int getNumberOfTrackedHealthySats() {
        return navcon.length > 3 ? navcon[3] : -1;
    }

    public int getNumberOfSatsAboveElevMask() {
        return navcon.length > 4 ? navcon[4] : -1;
    }

    public int getNumberOfSatsWithDiffCorrAvail() {
        return navcon.length > 5 ? navcon[5] : -1;
    }

    public int getNumberOfSatsWithDiffCorrNotAvail() {
        return navcon.length > 6 ? navcon[6] : -1;
    }


    @Override
    protected boolean validateChecksum(String nmea) {
        return true;
    }
}
