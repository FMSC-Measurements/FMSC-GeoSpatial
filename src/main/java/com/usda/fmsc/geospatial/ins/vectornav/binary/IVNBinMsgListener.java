package com.usda.fmsc.geospatial.ins.vectornav.binary;

import com.usda.fmsc.geospatial.base.listeners.IBinMsgListener;
import com.usda.fmsc.geospatial.ins.vectornav.binary.messages.VNBinMessage;

public interface IVNBinMsgListener<T extends VNBinMessage> extends IBinMsgListener<T> {
    
}
