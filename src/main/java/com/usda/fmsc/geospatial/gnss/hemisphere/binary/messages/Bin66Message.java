package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.L1SatObs;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.L2SatObs;

/*
 * GPS L1/L2 code and carrier phase information
 */
public class Bin66Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // double
    protected static final int GPS_WEEK_IDX = GPS_TIME_OF_WEEK_IDX + 8; // short
    protected static final int SPARE_1_IDX = GPS_WEEK_IDX + 2; // short
    protected static final int SPARE_2_IDX = SPARE_1_IDX + 2; // int
    protected static final int L1_SAT_OBS_IDX = SPARE_2_IDX + 4;// struct[12] 144
    protected static final int L2P_SAT_OBS_IDX = L1_SAT_OBS_IDX + 144; // struct[12] 144
    protected static final int L1_CODE_MSBs_PRN_IDX = L2P_SAT_OBS_IDX + 144; // int[12] 48

    protected static final int NUMBER_OF_L1_OBS = 12;
    protected static final int NUMBER_OF_L2_OBS = 12;


    public Bin66Message(byte[] message) {
        super(message);
    }


    @Override
    public MessageType getMessageType() {
        return MessageType.Bin66;
    }

    
    /**
     * GPS Time of Week (sec) associated with this message
     * 
     * @return double
     */
    public double getGPSTimeOfWeek() {
        return _message.getDouble(GPS_TIME_OF_WEEK_IDX);
    }

    /**
     * GPS week associated with this message
     * 
     * @return short
     */
    public short getGPSWeek() {
        return _message.getShort(GPS_WEEK_IDX);
    }

    /*
     * L1 satellite code observation data
     */
    public L1SatObs[] getL1SatObs() {
        L1SatObs[] obs = new L1SatObs[NUMBER_OF_L1_OBS];

        for (int idx = L1_SAT_OBS_IDX, cidx = L1_CODE_MSBs_PRN_IDX,
                i = 0; i < NUMBER_OF_L1_OBS; idx += L1SatObs.STRUCT_SIZE, cidx += Integer.BYTES, i++) {
            obs[i] = new L1SatObs(
                    _message.getInt(idx),
                    _message.getInt(idx + 4),
                    _message.getInt(idx + 8),
                    _message.getInt(cidx));
        }

        return obs;
    }

    /*
     * L2 satellite observation data
     */
    public L2SatObs[] getL2SatObs() {
        L2SatObs[] obs = new L2SatObs[NUMBER_OF_L2_OBS];

        for (int idx = L2P_SAT_OBS_IDX, i = 0; i < NUMBER_OF_L2_OBS; idx += L2SatObs.STRUCT_SIZE, i++) {
            obs[i] = new L2SatObs(
                    _message.getInt(idx),
                    _message.getInt(idx + 4),
                    _message.getInt(idx + 8));
        }

        return obs;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Bin66 [GLONASS L1/L2 Code Info]\n");

        sb.append("  L1 Obs:\n");
        for (L1SatObs ob : getL1SatObs()) {
            if (ob.isValid())
                sb.append(String.format("    %s\n", ob.toString()));
        }

        sb.append("  L2P Obs:\n");
        for (L2SatObs ob : getL2SatObs()) {
            if (ob.isValid())
                sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }

}
