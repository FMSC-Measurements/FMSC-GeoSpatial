package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.L1CASatObs;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.L1PObs;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.L2PSatObs;

/*
 * GPS L1/L2 code and carrier phase information
 */
public class Bin76Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // double
    protected static final int GPS_WEEK_IDX = GPS_TIME_OF_WEEK_IDX + 8; // short
    protected static final int SPARE_1_IDX = GPS_WEEK_IDX + 2; // short
    protected static final int SPARE_2_IDX = SPARE_1_IDX + 2; // int
    protected static final int L2P_SAT_OBS_IDX = SPARE_2_IDX + 4; // struct[12] 144
    protected static final int L1CA_SAT_OBS_IDX = L2P_SAT_OBS_IDX + 144;// struct[15] 180
    protected static final int L1CA_CODE_MSBs_PRN_IDX = L1CA_SAT_OBS_IDX + 180; // int[15] 60
    protected static final int L1P_CODE_IDX = L1CA_CODE_MSBs_PRN_IDX + 60; // int[12] 48

    protected static final int NUMBER_OF_L1CA_OBS = 15;
    protected static final int NUMBER_OF_L1P_L2P_OBS = 12;


    public Bin76Message(byte[] message) {
        super(message);
    }


    @Override
    public MessageType getMessageType() {
        return MessageType.Bin76;
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
     * L2 satellite observation data
     */
    public L2PSatObs[] getL2PSatObs() {
        L2PSatObs[] obs = new L2PSatObs[NUMBER_OF_L1P_L2P_OBS];

        for (int idx = L2P_SAT_OBS_IDX, i = 0; i < NUMBER_OF_L1P_L2P_OBS; idx += L2PSatObs.STRUCT_SIZE, i++) {
            obs[i] = new L2PSatObs(
                    _message.getInt(idx),
                    _message.getInt(idx + 4),
                    _message.getInt(idx + 8));
        }

        return obs;
    }

    /*
     * L1(CA) satellite code observation data
     */
    public L1CASatObs[] getL1CASatObs() {
        L1CASatObs[] obs = new L1CASatObs[NUMBER_OF_L1CA_OBS];

        for (int idx = L1CA_SAT_OBS_IDX, cidx = L1CA_CODE_MSBs_PRN_IDX,
                i = 0; i < NUMBER_OF_L1CA_OBS; idx += L1CASatObs.STRUCT_SIZE, cidx += Integer.BYTES, i++) {
            obs[i] = new L1CASatObs(
                    _message.getInt(idx),
                    _message.getInt(idx + 4),
                    _message.getInt(idx + 8),
                    _message.getInt(cidx));
        }

        return obs;
    }

    /*
     * L1(P) code observation data
     */
    public L1PObs[] getL1PObs() {
        L1PObs[] obs = new L1PObs[NUMBER_OF_L1P_L2P_OBS];

        for (int idx = L1P_CODE_IDX, i = 0; i < NUMBER_OF_L1P_L2P_OBS; idx += Integer.BYTES, i++) {
            obs[i] = new L1PObs(_message.getInt(idx));
        }

        return obs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Bin76 [GPS L1/L2 Code Info]\n");

        sb.append("  L2P Obs:\n");
        for (L2PSatObs ob : getL2PSatObs()) {
            if (ob.isValid())
                sb.append(String.format("    %s\n", ob.toString()));
        }

        sb.append("  L1CA Obs:\n");
        for (L1CASatObs ob : getL1CASatObs()) {
            if (ob.isValid())
                sb.append(String.format("    %s\n", ob.toString()));
        }

        sb.append("  L1P Obs:\n");
        for (L1PObs ob : getL1PObs()) {
            if (ob.isValid())
                sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }

}
