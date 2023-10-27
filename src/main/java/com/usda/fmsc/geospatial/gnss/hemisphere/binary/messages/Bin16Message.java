package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import java.util.EnumSet;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.Signal;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations.SatObsCMPwSig;

/*
 * Generic GNSS observations
 */
public class Bin16Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // double
    protected static final int GPS_WEEK_IDX = GPS_TIME_OF_WEEK_IDX + 8; // short
    protected static final int SPARE_1_IDX = GPS_WEEK_IDX + 2; // short
    protected static final int PAGE_COUNT_IDX = SPARE_1_IDX + 2; // int
    protected static final int ALL_SIGNALS_INDLUED_01_IDX = PAGE_COUNT_IDX + 4; // int
    protected static final int ALL_SIGNALS_INDLUED_02_IDX = ALL_SIGNALS_INDLUED_01_IDX + 4; // int
    protected static final int OBS_IDX = ALL_SIGNALS_INDLUED_02_IDX + 4; // obs[16] 192
    protected static final int CODE_MSBs_PRN = OBS_IDX + 192; // int[16] 64
    protected static final int CHAN_SIGNAL_SYS = CODE_MSBs_PRN + 64; // short[16] 32

    protected static final int NUMBER_OF_OBS = 16;

    public Bin16Message(byte[] message) {
        super(message);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.Bin16;
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
     * Satellite Observations
     */
    public SatObsCMPwSig[] getObservations() {
        SatObsCMPwSig[] obs = new SatObsCMPwSig[NUMBER_OF_OBS];

        for (int idx = OBS_IDX, cidx = CODE_MSBs_PRN, sidx = CHAN_SIGNAL_SYS,
                i = 0; i < NUMBER_OF_OBS; idx += SatObsCMPwSig.STRUCT_SIZE, cidx += Integer.BYTES, sidx += Short.BYTES, i++) {
            obs[i] = new SatObsCMPwSig(
                    _message.getInt(idx),
                    _message.getInt(idx + 4),
                    _message.getInt(idx + 8),
                    _message.getInt(cidx),
                    _message.getShort(sidx));
        }

        return obs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Bin16 [GNSS Satellites] (%d/%d)\n",
                getPageNumber(), getNumberOfPages()));

        StringBuilder sbs = new StringBuilder();
        for (Signal sig : getSignals()) {
            sbs.append(String.format("%s | ", sig.toString()));
        }

        sb.append(String.format("  GNSS Signals: %s\n", sbs.toString().subSequence(0, sbs.length() - 2)));

        sb.append("  Satellites:\n");
        for (SatObsCMPwSig ob : getObservations()) {
            if (ob.isValid())
                sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }

}
