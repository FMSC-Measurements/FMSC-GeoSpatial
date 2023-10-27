package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.NavMode;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic.ChannelData;

/*
 * GPS L1 diagnostic information
 */
public class Bin99Message extends BaseBinMessage {
    protected static final int NAV_MODE_IDX = HEADER_SIZE; // byte
    protected static final int UTC_TIME_DIFF_IDX = NAV_MODE_IDX + 1; // byte
    protected static final int GPS_WEEK_IDX = UTC_TIME_DIFF_IDX + 1; // ushort
    protected static final int GPS_TIME_OF_WEEK_IDX = GPS_WEEK_IDX + 2; // double
    protected static final int CHANNEL_DATA_IDX = GPS_TIME_OF_WEEK_IDX + 8; // ChannelData[12] 288
    protected static final int CLOCK_ERR_AT_L1_IDX = CHANNEL_DATA_IDX + 288; // short
    
    protected static final int NUMBER_OF_CHANNELS = 12;


    public Bin99Message(byte[] message) {
        super(message);
    }

    
    @Override
    public MessageType getMessageType() {
        return MessageType.Bin99;
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
        // if ((nm >>> 3 & 0x1) == 1) {
        //     return NavMode.parse((nm & 0x3) + 1);
        // } else {
        //     return NavMode.parse((nm & 0x3) - 1);
        // }
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

    /**
     * GPS Time of Week (sec) associated with this message
     */
    public double getGPSTimeOfWeek() {
        return _message.getDouble(GPS_TIME_OF_WEEK_IDX);
    }

    /*
     * Detailed data for each signal included.
     */
    public ChannelData[] getChannelData() {
        ChannelData[] cd = new ChannelData[NUMBER_OF_CHANNELS];

        for (int cdIdx = CHANNEL_DATA_IDX, i = 0; i < NUMBER_OF_CHANNELS; cdIdx += ChannelData.STRUCT_SIZE, i++) {
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

    /*
     * Clock error of the GPS clock oscillator at L1 frequency in Hz
     */
    public short getClockErrAtL1() {
        return _message.getShort(CLOCK_ERR_AT_L1_IDX);
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Bin99 [GPS L1 Diag Info]\n  Nav Mode: %s\n", getNavMode()));

        sb.append("  Channel Data:\n");
        for (ChannelData ob : getChannelData()) {
            sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }
}
