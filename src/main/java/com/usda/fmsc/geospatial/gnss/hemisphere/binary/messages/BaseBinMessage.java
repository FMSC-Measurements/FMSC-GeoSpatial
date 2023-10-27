package com.usda.fmsc.geospatial.gnss.hemisphere.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes.MessageType;

public abstract class BaseBinMessage {
    public static final int HEADER_SIZE = 8;
    public static final int EPILOGUE_SIZE = 2;
    public static final int HEADER_AND_EPILOGUE_SIZE = HEADER_SIZE + EPILOGUE_SIZE;

    protected final ByteBuffer _message;
    private final boolean _isValid;

    public BaseBinMessage(byte[] message) {
        this._message = ByteBuffer.wrap(message).order(ByteOrder.LITTLE_ENDIAN);
        this._isValid = (MessageType.parseFromMessage(message) == getMessageType()) &&
                validateChecksum(_message);
    }

    public abstract MessageType getMessageType();

    public byte[] getRawMessage() {
        return _message.array();
    }

    public boolean isValid() {
        return _isValid;
    }

    public static boolean validateChecksum(byte[] message) {
        return validateChecksum(ByteBuffer.wrap(message).order(ByteOrder.LITTLE_ENDIAN));
    }

    public static boolean validateChecksum(ByteBuffer message) {
        if (message.capacity() > HEADER_AND_EPILOGUE_SIZE) {
            message = message.order(ByteOrder.LITTLE_ENDIAN);

            final int dataSize = message.getShort(6);

            if (message.capacity() >= dataSize + HEADER_AND_EPILOGUE_SIZE) {
                final int checkSum = message.getShort(HEADER_SIZE + dataSize) & 0xffff;

                int cs = 0;

                for (int i = HEADER_SIZE; i < HEADER_SIZE + dataSize; i++) {
                    cs += message.get(i) & 0xff;
                }

                return cs == checkSum;
            }
        }
        return false;
    }

}