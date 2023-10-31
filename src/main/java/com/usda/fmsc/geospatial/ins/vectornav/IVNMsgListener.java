package com.usda.fmsc.geospatial.ins.vectornav;

import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public interface IVNMsgListener {
    void onInsData(VNInsData data);
    void onBinMsgReceived(VNBinMessage message);
    void onNmeaMsgReceived(VNNmeaSentence sentence);
    void onInvalidDataRecieved(byte[] data);
}
