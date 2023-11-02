package com.usda.fmsc.geospatial.ins.vectornav;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.usda.fmsc.geospatial.Utils;
import com.usda.fmsc.geospatial.base.readers.BaseMsgByteDataReader;
import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.VNNmeaTools;

public class VNDataReader extends BaseMsgByteDataReader {
    public static final byte ASII_START_CHAR = '$';
    public static final byte ASII_START_CHKSUM = '*';
    public static final byte[] ASCII_DELIMETER = new byte[] { '\r', '\n' };
    public static final byte BINARY_SYNC_BYTE = (byte)0xFA;

    public static final int MAX_BUFFER_SIZE = 256;

	private BinaryMsgConfig lastBinMsgConfig = null;

    private Listener listener;


    public VNDataReader(InputStream stream) {
        super(stream);
    }

    public VNDataReader(InputStream stream, Listener listener) {
        super(stream);
        this.listener = listener;
    }

    public byte[] readBytes() throws IOException {
        byte[] data = fill();

        if (data.length > 0)
        {  
            if (lastBinMsgConfig != null &&
                data.length >= lastBinMsgConfig.getTotalPacketSize() + 1 &&
                data[0] == BINARY_SYNC_BYTE &&
                data[lastBinMsgConfig.getTotalPacketSize()] == BINARY_SYNC_BYTE) {

                byte[] msgData = Arrays.copyOfRange(data, 0, lastBinMsgConfig.getTotalPacketSize());
                if (listener != null)
                    listener.onBinMsgBytesReceived(lastBinMsgConfig, msgData);
                dequeue(lastBinMsgConfig.getTotalPacketSize());
                return msgData;
            }

            for (int i = 0; i < data.length; i++) {
                if (data[i] == BINARY_SYNC_BYTE) {
                    if (i + VNBinMessage.MIN_PACKET_SIZE < data.length) {
                        BinaryMsgConfig config = BinaryMsgConfig.fromBytes(Arrays.copyOfRange(data, i, i + VNBinMessage.MIN_PACKET_SIZE));

                        if (config != null && config.isValid()) {
                            int totalLen = config.getTotalPacketSize();

                            if (totalLen >= VNBinMessage.MIN_PACKET_SIZE && totalLen < MAX_BUFFER_SIZE && totalLen + i < data.length) {
                                if (Utils.validateChecksum16(data, i + 1, totalLen - 1)) {
                                    lastBinMsgConfig = config;
                                    
                                    byte[] msgData = Arrays.copyOfRange(data, i, i + totalLen);

                                    if (((int)msgData[0]) != -6) {
                                        msgData[0] = (byte)-6;
                                    }

                                    if (i > 0 && listener != null) {
                                        byte[] invalidData = Arrays.copyOfRange(data, 0, i);
                                        listener.onInvalidDataRecieved(invalidData);
                                    }

                                    if (listener != null)
                                        listener.onBinMsgBytesReceived(config, msgData);
                                    dequeue(i + lastBinMsgConfig.getTotalPacketSize());
                                    return msgData;
                                }
                            }
                        }
                    }
                } else if (data[i] == ASII_START_CHAR) {
                    int csIdx = Utils.getIndexOf(data, ASII_START_CHKSUM, i);

                    if (csIdx > i && csIdx + 3 < data.length) {
                        byte[] nmeaData = Arrays.copyOfRange(data, i, csIdx + 3);

                        if (VNNmeaTools.validateChecksum(new String(nmeaData))) {
                            listener.onNmeaMsgBytesReceived(nmeaData);
                            dequeue(csIdx + 5); //3 for the Checksum and 2 for EOL
                            return nmeaData;
                        }
                    }
                }
            }
        }

        return new byte[0];
    }


    public void setListener(Listener listener) {
        this.listener = listener;
    }


    public interface Listener {
        void onBinMsgBytesReceived(BinaryMsgConfig config, byte[] data);
        void onNmeaMsgBytesReceived(byte[] data);
        void onInvalidDataRecieved(byte[] data);
    }
}
