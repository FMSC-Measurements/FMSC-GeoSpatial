package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.NavMode;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic.ChannelData;

/*
 * GPS L2 diagnostic information
 */
public class Bin100Message extends BaseBinMessage {
    protected static final int NAV_MODE_IDX = HEADER_SIZE; // byte
    protected static final int UTC_TIME_DIFF_IDX = NAV_MODE_IDX + 1; // byte
    protected static final int GPS_WEEK_IDX = UTC_TIME_DIFF_IDX + 1; // ushort
    protected static final int MASK_SBAS_USED_L2P_IDX = GPS_WEEK_IDX + 2; // long
    protected static final int GPS_TIME_OF_WEEK_IDX = MASK_SBAS_USED_L2P_IDX + 4; // double
    protected static final int MASK_SBAS_USED_L1P_IDX = GPS_WEEK_IDX + 8; // long
    protected static final int CHANNEL_DATA_IDX = MASK_SBAS_USED_L1P_IDX + 4; // ChannelData[12] 288
    
    protected static final int NUMBER_OF_CHANNELS = 10;


    public Bin100Message(byte[] message) {
        super(message);
    }

    
    @Override
    public MessageType getMessageType() {
        return MessageType.Bin100;
    }


    /**
     * Navigation Mode
     * 
     * @return NavMode
     */
    public NavMode getNavMode() {
        // check = Nav Mode FIX_NO, FIX_2D, FIX_3D (high bit =has_diff)

        byte nm = _message.get(NAV_MODE_IDX);

        return NavMode.parse((nm & 0b111) + 1, ((nm >> 3 & 0x1) == 1));
    }

    /*
     * Whole seconds between UTC and GPS time (GPS minus UTC)
     */
    public int geUtcTimeDiff() {
        return _message.get(UTC_TIME_DIFF_IDX) & 0xFF;
    }

    /**
     * GPS Week Number
     */
    public int getGPSWeek() {
        return _message.getShort(GPS_WEEK_IDX) & 0xFFFF;
    }

    /*
     *  L2P satellites used, bit mapped 0..31
     */
    public int getMaskSatsUsedL2P() {
        return _message.getInt(MASK_SBAS_USED_L2P_IDX);
    }

    /**
     * GPS Time of Week (sec) associated with this message
     */
    public double getGPSTimeOfWeek() {
        return _message.getDouble(GPS_TIME_OF_WEEK_IDX);
    }

    /*
     *  L1P satellites used, bit mapped 0..31
     */
    public int getMaskSatsUsedL1P() {
        return _message.getInt(MASK_SBAS_USED_L1P_IDX);
    }

    /*
     * Detailed data for each signal included.
     */
    public ChannelData[] getChannelData() {
        ChannelData[] cd = new ChannelData[NUMBER_OF_CHANNELS];

        for (int cdIdx = CHANNEL_DATA_IDX, i = 0; i < NUMBER_OF_CHANNELS && ((i + 24) < _message.capacity()); cdIdx += ChannelData.STRUCT_SIZE, i++) {
            cd[i] = new ChannelData(
                    _message.get(cdIdx),
                    _message.get(cdIdx + 1),
                    _message.get(cdIdx + 2),
                    _message.get(cdIdx + 3),
                    _message.get(cdIdx + 4),
                    _message.get(cdIdx + 5),
                    _message.get(cdIdx + 6),
                    _message.get(cdIdx + 7),
                    _message.get(cdIdx + 8),
                    _message.get(cdIdx + 9),
                    _message.get(cdIdx + 10),
                    _message.get(cdIdx + 11),
                    _message.getShort(cdIdx + 12),
                    _message.getShort(cdIdx + 14),
                    _message.getShort(cdIdx + 16),
                    _message.getShort(cdIdx + 18),
                    _message.getShort(cdIdx + 20),
                    _message.getShort(cdIdx + 22)
                );
        }

        return cd;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Bin100 [GPS L2 Diag Info]\n  Nav Mode: %s\n", getNavMode()));
        sb.append("  L2P Used: ");

        int tmp = getMaskSatsUsedL2P();
        boolean prev = false;

        for (int i = 0; i < 32; i++) {
            if (((tmp >> i) & 1) == 1) {
                sb.append(prev ? "," + i : i);
                prev = true;
            }
        }
        
        sb.append("\n  L1P Used: ");

        tmp = getMaskSatsUsedL1P();
        prev = false;

        for (int i = 0; i < 32; i++) {
            if (((tmp >> i) & 1) == 1) {
                sb.append(prev ? "," + i : i);
                prev = true;
            }
        }

        sb.append("\n");

        sb.append("  Channel Data:\n");
        for (ChannelData ob : getChannelData()) {
            sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }
}
