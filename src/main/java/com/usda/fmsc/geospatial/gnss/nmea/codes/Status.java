package com.usda.fmsc.geospatial.gnss.nmea.codes;

public enum Status {
    Valid(0), //Active - old
    Warning(1),
    Fault(2); //Void - old

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status parse(int id) {
        Status[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid Status id: " + id);
    }

    public static Status parse(String str) {
        switch(str.toLowerCase()) {
            case "a":
            case "valid":
            case "active": return Valid;
            case "v":
            case "warning":
            case "void": return Warning;
            case "bad":
            case "fault": return Fault;
            default: throw new IllegalArgumentException("Invalid Status Name: " + str);
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case Valid: return "Valid";
            case Warning: return "Warning";
            case Fault: return "Fault";
            default: throw new IllegalArgumentException();
        }
    }
}
