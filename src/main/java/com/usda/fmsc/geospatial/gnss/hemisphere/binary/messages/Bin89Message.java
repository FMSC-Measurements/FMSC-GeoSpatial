package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;
import com.usda.fmsc.geospatial.gnss.hemisphere.binary.diagnostic.ChannelData;

/*
 * SBAS satellite tracking information (supports three SBAS satellites) 
 */
public class Bin89Message extends BaseBinMessage {
    protected static final int GPS_TIME_OF_WEEK_IDX = HEADER_SIZE; // int
    protected static final int MASK_SBAS_TRACKED_IDX = GPS_TIME_OF_WEEK_IDX + 4; // byte
    protected static final int MASK_SBAS_USED_IDX = MASK_SBAS_TRACKED_IDX + 1; // byte
    protected static final int SPARE_IDX = MASK_SBAS_USED_IDX + 1; // ushort
    protected static final int CHANNEL_DATA_IDX = GPS_TIME_OF_WEEK_IDX + 2; // ChannelData[3] 48

    protected static final int NUMBER_OF_CHANNELS = 3;


    public Bin89Message(byte[] message) {
        super(message);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.Bin89;
    }
    

    /**
     * GPS Time of Week (sec) associated with this message
     */
    public long getGPSTimeOfWeek() {
        return _message.getInt(GPS_TIME_OF_WEEK_IDX) & 0xFFFFFFFF;
    }


    /*
     * SBAS satellites tracked, bit mapped 0..3
     */
    public byte getSBASTrackedMask() {
        return _message.get(MASK_SBAS_TRACKED_IDX);
    }

    /*
     * SBAS satellites used, bit mapped 0..3
     */
    public byte getSBASUsedMask() {
        return _message.get(MASK_SBAS_USED_IDX);
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Bin89 [SBAS Info]\n  SBAS Tracked:");

        byte tmp = getSBASTrackedMask();
        boolean prev = false;

        for (int i = 0; i < 4; i++) {
            if (((tmp >> i) & 1) == 1) {
                sb.append(prev ? "," + i : i);
                prev = true;
            }
        }

        tmp = getSBASUsedMask();
        prev = false;

        sb.append("\n  SBAS Used: ");
        for (int i = 0; i < 4; i++) {
            if (((tmp >> i) & 1) == 1) {
                sb.append(prev ? "," + i + 1 : i + 1);
                prev = true;
            }
        }

        sb.append("\n");

        sb.append("  Channel Data:\n");
        for (ChannelData ob : getChannelData()) {
            sb.append(String.format("    %s\n", ob.toString()));
        }

        return sb.toString();
    }}
