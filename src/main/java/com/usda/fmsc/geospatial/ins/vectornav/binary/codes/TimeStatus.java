package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class TimeStatus {
    public static final int Unknown = Integer.MIN_VALUE;
    public static final int TimeOK = 1 << 0;
    public static final int DateOK = 1 << 1;
    public static final int UtcTimeValid = 1 << 2;
    

    private final int value;

    
    public TimeStatus(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
    
    public boolean isTimeOK() {
        return (value & TimeOK) == TimeOK;
    }

    public boolean isDateOK() {
        return (value & DateOK) == DateOK;
    }

    public boolean isUtcTimeValid() {
        return (value & UtcTimeValid) == UtcTimeValid;
    }

    
    public static TimeStatus parse(int value) {
        for (int i = 0; i < 4; i++) {
            if (i == value)
                return new TimeStatus(value);
        }
        return new TimeStatus(Unknown);
    }
}
