package com.usda.fmsc.geospatial.ins.vectornav.binary.messages;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.joda.time.DateTime;

import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.TimeGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.TimeStatus;

public class TimeBinMessage extends VNBinMessage {
    private TimeGroup timeGroup;

    private long timeStatup;
    private long timeGps;
    private long gpsTow;
    private int gpsWeek;
    private long timeSyncIn;
    private long timeGpsPps;
    private DateTime utcTime;
    private long syncInCnt;
    private long syncOutCnt;
    private TimeStatus timeStatus;


    public TimeBinMessage(byte[] message) {
        this(null, message);
    }

    public TimeBinMessage(BinaryMsgConfig config, byte[] message) {
        super(config, message);
        this.timeGroup = getBinaryMsgConfig().getTimeGroup();
    }


    @Override
    protected void parseMessage(ByteBuffer message) {
        message.order(ByteOrder.LITTLE_ENDIAN);
        message.position(getBinaryMsgConfig().getHeaderSize());

        if (timeGroup.hasTimestartup()) {
            timeStatup = message.getLong();
        }

        if (timeGroup.hasTimeGps()) {
            timeGps = message.getLong();
        }
        
        if (timeGroup.hasGpsTow()) {
            gpsTow = message.getLong();
        }
        
        if (timeGroup.hasGpsWeek()) {
            gpsWeek = message.getShort() & 0xFFFF;
        }
        
        if (timeGroup.hasTimesyncin()) {
            timeSyncIn = message.getLong();
        }
        
        if (timeGroup.hasTimeGpsPps()) {
            timeGpsPps = message.getLong();
        }
        
        if (timeGroup.hasTimeUTC()) {
            utcTime = new DateTime(
                message.get() + 2000, //year
                message.get() & 0xFF, //month
                message.get() & 0xFF, //day
                message.get() & 0xFF, //hour
                message.get() & 0xFF, //minute
                message.get() & 0xFF, //second
                message.getShort() & 0xFFFF);//millisecond
        }
        
        if (timeGroup.hasSyncInCnt()) {
            syncInCnt = message.getInt() & 0xFFFFFFFFL;
        }
        
        if (timeGroup.hasSyncOutCnt()) {
            syncOutCnt = message.getInt() & 0xFFFFFFFFL;
        }
        
        if (timeGroup.hasTimestartup()) {
            timeStatus = new TimeStatus(message.get());
        }
    }
    

    public long getTimeStatup() {
        return timeStatup;
    }

    public long getTimeGps() {
        return timeGps;
    }

    public long getGpsTow() {
        return gpsTow;
    }

    public int getGpsWeek() {
        return gpsWeek;
    }

    public long getTimeSyncIn() {
        return timeSyncIn;
    }

    public long getTimeGpsPps() {
        return timeGpsPps;
    }

    public DateTime getUtcTime() {
        return utcTime;
    }

    public long getSyncInCnt() {
        return syncInCnt;
    }

    public long getSyncOutCnt() {
        return syncOutCnt;
    }

    public TimeStatus getTimeStatus() {
        return timeStatus;
    }

    public TimeGroup getTimeGroup() {
        return timeGroup;
    }

    
}
