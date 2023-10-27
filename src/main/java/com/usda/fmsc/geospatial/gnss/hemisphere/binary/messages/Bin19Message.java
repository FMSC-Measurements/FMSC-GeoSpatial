package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import java.util.EnumSet;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.NavMode;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Signal;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic.GenericChannelData;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic.GenericChannelDataWSig;

/*
 * GNSS diagnostic information
 */
public class Bin19Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // int
    protected static final int GPS_WEEK_IDX = GPS_TIME_OF_WEEK_IDX + 4; // ushort
    protected static final int NAV_MODE_IDX = GPS_WEEK_IDX + 2; // byte
    protected static final int UTC_TIME_DIFF_IDX = NAV_MODE_IDX + 1; // byte
    protected static final int PAGE_COUNT_IDX = UTC_TIME_DIFF_IDX + 1; // int
    protected static final int ALL_SIGNALS_INDLUED_01_IDX = PAGE_COUNT_IDX + 4; // int
    protected static final int ALL_SIGNALS_INDLUED_02_IDX = ALL_SIGNALS_INDLUED_01_IDX + 4; // int
    protected static final int CLOCK_ERR_AT_L1_IDX = ALL_SIGNALS_INDLUED_02_IDX + 4; // short
    protected static final int SPARE_1_IDX = CLOCK_ERR_AT_L1_IDX + 2; // short
    protected static final int CHANNEL_DATA_IDX = SPARE_1_IDX + 2; // SGENERICchanData[16] 320
    protected static final int CHAN_SIGNAL_SYS = CHANNEL_DATA_IDX + 320; // short[16] 32

    protected static final int NUMBER_OF_CHANNELS = 16;

    public Bin19Message(byte[] message) {
        super(message);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.Bin19;
    }

    /**
     * GPS Time of Week (sec) associated with this message
     */
    public int getGPSTimeOfWeek() {
        return _message.getInt(GPS_TIME_OF_WEEK_IDX);
    }

    /**
     * GPS Week Number
     */
    public int getGPSWeek() {
        return _message.getShort(GPS_WEEK_IDX) & 0xFFFF;
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
        
        // if (nm == 0) {
        //     return NavMode.NoFix;
        // } else if ((nm >>> 3 & 0x1) == 1) {
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

    /*
     * Number of Pages
     */
    public int getNumberOfPages() {
        return (_message.getInt(PAGE_COUNT_IDX) >>> 16) & 0x3F;
    }

    /*
     * Page Number (starts at 1);
     */
    public int getPageNumber() {
        return ((_message.getInt(PAGE_COUNT_IDX) >>> 22) & 0x3F) + 1;
    }

    /*
     * All signals included in the set of pages
     */
    public EnumSet<Signal> getSignals() {
        return Signal.parseFromMasks(
                _message.getInt(ALL_SIGNALS_INDLUED_01_IDX),
                _message.getInt(ALL_SIGNALS_INDLUED_02_IDX));
    }

    /*
     * Detailed data for each signal included.
     */
    public GenericChannelDataWSig[] getChannelData() {
        GenericChannelDataWSig[] cd = new GenericChannelDataWSig[NUMBER_OF_CHANNELS];

        for (int cdIdx = CHANNEL_DATA_IDX, csIdx = CHAN_SIGNAL_SYS,
                i = 0; i < NUMBER_OF_CHANNELS; cdIdx += GenericChannelDataWSig.STRUCT_SIZE, csIdx += Short.BYTES, i++) {
            cd[i] = new GenericChannelDataWSig(
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
                    _message.getShort(csIdx));
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

        sb.append(String.format("Bin19 [GNSS Diag Info] (%d/%d)\n  Nav Mode: %s\n",
                getPageNumber(), getNumberOfPages(), getNavMode()));

        StringBuilder sbs = new StringBuilder();
        for (Signal sig : getSignals()) {
            sbs.append(String.format("%s | ", sig.toString()));
        }

        sb.append(String.format("  GNSS Signals: %s\n", sbs.toString().subSequence(0, sbs.length() - 2)));

        sb.append("  Channel Data:\n");
        for (GenericChannelData ob : getChannelData()) {
            sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }

}
