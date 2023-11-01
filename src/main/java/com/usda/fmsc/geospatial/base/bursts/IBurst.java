package com.usda.fmsc.geospatial.base.bursts;

public interface IBurst<PDT, Message> {
    public Message parseMessage(PDT data);
    public Message addMessage(PDT data);
}
