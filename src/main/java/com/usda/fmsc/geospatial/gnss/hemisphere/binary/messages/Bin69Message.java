package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic.GLONASSChannelData;

/*
 * GLONASS L1/L2diagnostic information
 */
public class Bin69Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // int
    protected static final int L1_USED_NAV_MASK_IDX = GPS_TIME_OF_WEEK_IDX + 4; // unsigned short
    protected static final int L2_USED_NAV_MASK_IDX = L1_USED_NAV_MASK_IDX + 2; // unsigned short
    protected static final int CHANNEL_DATA_IDX = L2_USED_NAV_MASK_IDX + 2; // GLONASSChanData[12] 288
    protected static final int GPS_WEEK_IDX = CHANNEL_DATA_IDX + 288; // unsigned short
    protected static final int SPARE_01_IDX = GPS_WEEK_IDX + 2; // byte
    protected static final int SPARE_02_IDX = SPARE_01_IDX + 2; // byte
    
    protected static final int NUMBER_OF_CHANNELS = 12;


    public Bin69Message(byte[] message) {
        super(message);
    }


    @Override
    public MessageType getMessageType() {
        return MessageType.Bin69;
    }

    /**
     * GPS Time of Week (sec) associated with this message
     */
    public long getGPSTimeOfWeek() {
        return _message.getInt(GPS_TIME_OF_WEEK_IDX) & 0xFFFFFFFF;
    }

    /*
     * Mask of L1 channels used in nav solution
     */
    public int getL1UsedNavMask() {
        return _message.getShort(L1_USED_NAV_MASK_IDX) & 0xFFFF;
    }

    /*
     * Mask of L2 channels used in nav solution
     */
    public int getL2UsedNavMask() {
        return _message.getShort(L2_USED_NAV_MASK_IDX) & 0xFFFF;
    }
    

    /*
     * Detailed data for each schannel
     */
    public GLONASSChannelData[] getChannelData() {
        GLONASSChannelData[] cd = new GLONASSChannelData[NUMBER_OF_CHANNELS];

        for (int cdIdx = CHANNEL_DATA_IDX, i = 0; i < NUMBER_OF_CHANNELS; cdIdx += GLONASSChannelData.STRUCT_SIZE, i++) {
            cd[i] = new GLONASSChannelData(
                    _message.get(cdIdx),
                    _message.get(cdIdx + 1),
                    _message.get(cdIdx + 2),
                    _message.get(cdIdx + 3),
                    _message.get(cdIdx + 4),
                    _message.get(cdIdx + 5),
                    _message.get(cdIdx + 6),
                    _message.get(cdIdx + 7),
                    _message.getShort(cdIdx + 8),
                    _message.getShort(cdIdx + 10),
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

    /**
     * GPS Week Number
     */
    public int getGPSWeek() {
        return _message.getShort(GPS_WEEK_IDX) & 0xFFFF;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int l1Used = 0, l2Used = 0, l1Mask = getL1UsedNavMask(), l2Mask = getL2UsedNavMask();

        for (int i = 0; i < 32; i ++) {
            l1Used += (l1Mask >> i) & 1;
            l2Used += (l2Mask >> i) & 1;
        }

        sb.append(String.format("Bin69 [GLONASS L1/L2 Diag Info]\n  L1 Used: %d\n  L2 Used: %d\n", l1Used, l2Used));

        sb.append("  Channel Data:\n");
        for (GLONASSChannelData ob : getChannelData()) {
            sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }
}
