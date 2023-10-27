package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class TimeGroup extends BaseGroup {
    public static final int None = 0;
    public static final int TimeStartup = 1 << 0;
    public static final int TimeGps = 1 << 1;
    public static final int GpsTow = 1 << 2;
    public static final int GpsWeek = 1 << 3;
    public static final int TimeSyncIn = 1 << 4;
    public static final int TimeGpsPps = 1 << 5;
    public static final int TimeUTC = 1 << 6;
    public static final int SyncInCnt = 1 << 7;
    public static final int SyncOutCnt = 1 << 8;
    public static final int TimeStatus = 1 << 9;
    
    public static final int ALL_FIELDS_VN100 = 0b110010001;

    public static final byte[] FIELD_SIZES = { 8, 8, 8, 2, 8, 8, 8, 4, 4, 1, 1, 0, 0, 0, 0, 0 };

    
    public TimeGroup(int value) {
        super(value);
    }

    
    @Override
    protected byte[] getFieldSizes() {
        return FIELD_SIZES;
    }


    public boolean hasTimestartup() {
        return (value & TimeStartup) == TimeStartup;
    }

    public boolean hasTimeGps() {
        return (value & TimeGps) == TimeGps;
    }

    public boolean hasGpsTow() {
        return (value & GpsTow) == GpsTow;
    }

    public boolean hasGpsWeek() {
        return (value & GpsWeek) == GpsWeek;
    }

    public boolean hasTimesyncin() {
        return (value & TimeSyncIn) == TimeSyncIn;
    }

    public boolean hasTimeGpsPps() {
        return (value & TimeGpsPps) == TimeGpsPps;
    }

    public boolean hasTimeUTC() {
        return (value & TimeUTC) == TimeUTC;
    }

    public boolean hasSyncInCnt() {
        return (value & SyncInCnt) == SyncInCnt;
    }

    public boolean hasSyncOutCnt() {
        return (value & SyncOutCnt) == SyncOutCnt;
    }

    public boolean hasTimeStatus() {
        return (value & TimeStatus) == TimeStatus;
    }
}
