package com.usda.fmsc.geospatial.ins.vectornav;

import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;
import com.usda.fmsc.geospatial.ins.vectornav.commands.VNCommand;
import com.usda.fmsc.geospatial.ins.vectornav.nmea.sentences.base.VNNmeaSentence;

public interface IVNMsgListener {
    void onInsData(VNInsData data);
    void onBinMsgReceived(VNBinMessage message);
    void onNmeaMsgReceived(VNNmeaSentence sentence);
    void onCommandResponseReceived(VNCommand command);
    void onInvalidDataRecieved(byte[] data);
}
