package com.usda.fmsc.geospatial.ins.vectornav;

import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.IVNBinMsgListener;
import com.usda.fmsc.geospatial.ins.vectornav.binary.VNBinMsgParser;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.VNNmeaParser;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.VNNmeaParserListener;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class VNParser {
    private final List<IVNMsgListener> listeners;

    private final VNBinMsgParser binMsgParser;
    private final VNNmeaParser nmeaParser;
    private final BinaryMsgConfig config;

    private boolean isConsecutive = false;
    

    public VNParser() {
        this(null);
    }

    public VNParser(BinaryMsgConfig config) {
        listeners = new ArrayList<>();
        this.config = config;

        this.binMsgParser = new VNBinMsgParser(this.config);
        this.binMsgParser.addListener(new IVNBinMsgListener<VNBinMessage>() {
            @Override
            public void onMessageReceived(VNBinMessage message) {
                onBinMessageReceived(message);
            }

            @Override
            public void onInvalidMessageReceived(byte[] invalidMessageData) {
                VNParser.this.onInvalidMessageReceived(invalidMessageData);
            }
        });

        this.nmeaParser = new VNNmeaParser();
        this.nmeaParser.addListener(new VNNmeaParserListener() {
            @Override
            public void onCommandResponseReceived(VNCommand command) {
                VNParser.this.onCommandResponseReceived(command);
            }

            @Override
            public void onMessageReceived(VNNmeaSentence message) {
                onNmeaMessageReceived(message);
            }

            @Override
            public void onInvalidMessageReceived(String invalidMessageData) {
                VNParser.this.onInvalidMessageReceived(invalidMessageData.getBytes());
            }
        });
    }

    public VNBinMessage parseBinMessage(byte[] data) {
        VNBinMessage message = binMsgParser.parse(data, isConsecutive);
        
        if (message != null) {
            if (message instanceof VNInsData) {
                onInsData((VNInsData)message);
            }

            isConsecutive = true;
        }

        return message;
    }

    public VNNmeaSentence parseNmeaMessage(byte[] data) {
        return nmeaParser.parse(new String(data));
    }

    public void postInvalidData(byte[] data) {
        isConsecutive = false;
        onInvalidMessageReceived(data);
    }


    protected void onInsData(VNInsData data) {
        for (IVNMsgListener listener : listeners) {
            listener.onInsData(data);
        }
    }

    protected void onBinMessageReceived(VNBinMessage message) {
        for (IVNMsgListener listener : listeners) {
            listener.onBinMsgReceived(message);
        }
    }

    protected void onNmeaMessageReceived(VNNmeaSentence sentence) {
        for (IVNMsgListener listener : listeners) {
            listener.onNmeaMsgReceived(sentence);
        }
    }

    protected void onCommandResponseReceived(VNCommand command) {
        for (IVNMsgListener listener : listeners) {
            listener.onCommandResponseReceived(command);
        }
    }

    protected void onInvalidMessageReceived(byte[] data) {
        for (IVNMsgListener listener : listeners) {
            listener.onInvalidDataRecieved(data);
        }

        isConsecutive = false;
    }


    public final void addListener(IVNMsgListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public final void removeListener(IVNMsgListener listener) {
        listeners.remove(listener);
    }
}
