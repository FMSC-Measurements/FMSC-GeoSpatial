package com.usda.fmsc.geospatial.ins.vectornav;

import java.util.ArrayList;
import java.util.List;

import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.VNBinMsgParser;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.CommonBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.VNNmeaParser;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public class VNParser {
    private final List<IVNMsgListener> listeners;

    private VNBinMsgParser binMsgParser;
    private VNNmeaParser nmeaParser;

    private BinaryMsgConfig config;
    private boolean isConsecutive = true;
    

    public VNParser() {
        this(null);
    }

    public VNParser(BinaryMsgConfig config) {
        listeners = new ArrayList<>();
        this.config = config;

        this.binMsgParser = new VNBinMsgParser(this.config);
        this.nmeaParser = new VNNmeaParser();
    }

    public VNBinMessage parseBinMessage(byte[] data) {
        VNBinMessage message = binMsgParser.parse(data);
        
        if (message != null) {
            onBinMessageReceived(message);

            if (message instanceof CommonBinMessage) {
                onInsData(new VNInsData((CommonBinMessage)message, isConsecutive));
            }

            isConsecutive = true;
        } else {
            onInvalidData(data);
        }

        return message;
    }

    public VNNmeaSentence parseNmeaMessage(byte[] data) {
        VNNmeaSentence sentence = nmeaParser.parse(new String(data));

        if (sentence != null) {
            onNmeaMessageReceived(sentence);
        } else {
            onInvalidData(data);
        }

        return sentence;
    }

    public void onInvalidData(byte[] data) {
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
