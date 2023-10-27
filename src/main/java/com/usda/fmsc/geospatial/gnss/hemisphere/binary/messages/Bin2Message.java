package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;

/*
 * GPS DOPs (Dilution of Precision) This message contains various quantities that are related to the GNSS solution, 
 * such as satellites tracked, satellites used, and DOPs.
 */
public class Bin2Message extends BaseBinMessage {
    protected static final int MASK_SATS_TRACKED_IDX = HEADER_SIZE; // uint
    protected static final int MASK_SATS_USED_IDX = MASK_SATS_TRACKED_IDX + 4; // uint
    protected static final int GPS_UTC_DIFF_IDX = MASK_SATS_USED_IDX + 4; // ushort
    protected static final int HDOP_IDX = GPS_UTC_DIFF_IDX + 2; // ushort
    protected static final int VDOP_IDX = HDOP_IDX + 2; // ushort
    protected static final int WAAS_MASK_IDX = VDOP_IDX + 2;// ushort

    private short _waasMask;

    public Bin2Message(byte[] message) {
        super(message);
        _waasMask = _message.getShort(WAAS_MASK_IDX);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.Bin2;
    }

    /*
     * Mask of satellites tracked by the GPS. Bit 0 corresponds to the GPS satellite
     * with PRN 1.
     */
    public int getMaskSatsTracked() {
        return _message.getInt(MASK_SATS_TRACKED_IDX);
    }

    /*
     * Mask of satellites used in the GPS solution. Bit 0 corresponds to the GPS
     * satellite with PRN 1.
     */
    public int getMaskSatsUsed() {
        return _message.getInt(MASK_SATS_USED_IDX);
    }

    /*
     * Whole seconds between UTC and GPS time (GPS minus UTC)
     */
    public int getGpsUtcDiff() {
        return _message.getShort(GPS_UTC_DIFF_IDX) & 0xFFFF;
    }

    /*
     * Horizontal dilution of precision
     */
    public float getHDOP() {
        return (_message.getShort(HDOP_IDX) & 0xFFFF) / 10f;
    }

    /*
     * Vertical dilution of precision
     */
    public float getVDOP() {
        return (_message.getShort(VDOP_IDX) & 0xFFFF) / 10f;
    }

    /*
     * PRN and tracked or used status masks
     */
    public int WAASMask() {
        return _waasMask;
    }

    /*
     * PRN of the first WAAS Satellite used
     */
    public int getWaasSat1PRN() {
        return (((_waasMask >> 5) & 0x1F) - 120) & 0xFF;
    }

    /*
     * PRN of the second WAAS Satellite used
     */
    public int getWaasSat2PRN() {
        return (((_waasMask >> 10) & 0x1F) - 120) & 0xFF;
    }

    /*
     * Satellites tracked by first WAAS satellite
     */
    public boolean areSatsTrackedByWaas1() {
        return (_waasMask & 0x01) == 1;
    }

    /*
     * Satellites tracked by second WAAS satellite
     */
    public boolean areSatsTrackedByWaas2() {
        return (_waasMask & 0x02) == 2;
    }

    /*
     * Satellites used by first WAAS satellite
     */
    public boolean areSatsUsedByWaas1() {
        return (_waasMask & 0x04) == 4;
    }

    /*
     * Satellites used by second WAAS satellite
     */
    public boolean areSatsUsedByWaas2() {
        return (_waasMask & 0x08) == 8;
    }

    private List<Integer> getPRNsFromMask(int mask) {
        List<Integer> prns = new ArrayList<Integer>();

        for (int i = 0; i < Integer.SIZE; i++) {
            if (((mask & (1 << i)) == (1 << i))) {
                prns.add(i + 1);
            }
        }

        return prns;
    }

    /*
     * Get list of tracked satellite PRNs
     */
    public List<Integer> getTrackedSatPRNs() {
        return getPRNsFromMask(getMaskSatsTracked());
    }

    /*
     * Get list of used satellite PRNs
     */
    public List<Integer> getUsedSatPRNs() {
        return getPRNsFromMask(getMaskSatsUsed());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Bin2 [GPS DOP Info]\n");
        sb.append(String.format("  GpsDiffTime: %ds\n", getGpsUtcDiff()));
        sb.append(String.format("  HDOP: %.2f\n", getHDOP()));
        sb.append(String.format("  VDOP: %.2f\n", getVDOP()));

        sb.append(String.format("  Waas1- PRN: %d | Trk: %s | Used: %s\n",
                getWaasSat1PRN(),
                areSatsTrackedByWaas1(),
                areSatsUsedByWaas1()));

        sb.append(String.format("  Waas2- PRN: %d | Trk: %s | Used: %s\n",
                getWaasSat2PRN(),
                areSatsTrackedByWaas2(),
                areSatsUsedByWaas2()));

        return sb.toString();
    }
}
