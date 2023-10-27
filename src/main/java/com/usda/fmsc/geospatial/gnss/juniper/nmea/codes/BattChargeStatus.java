package com.usda.fmsc.geospatial.gnss.juniper.nmea.codes;

public enum BattChargeStatus {
    None(0),
    Charging(1),
    Discharging(2),
    Error(3);

    private final int value;

    BattChargeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BattChargeStatus parse(int id) {
        BattChargeStatus[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid BattChargeStatus value: " + id);
    }

    @Override
    public String toString() {
        switch(this) {
            case None: return "None";
            case Charging: return "Charging";
            case Discharging: return "Discharging";
            case Error: return "Error";
            default: throw new IllegalArgumentException();
        }
    }
}
