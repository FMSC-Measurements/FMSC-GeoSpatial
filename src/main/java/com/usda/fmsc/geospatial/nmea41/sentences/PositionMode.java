package com.usda.fmsc.geospatial.nmea41.sentences;

public enum PositionMode {
    Unknown(0),
    Auto(1),
    Differential(2),
    Estimated(3),
    Manual(4),
    Simulation(5),
    RTK(6),
    FloatRTK(7),
    NotValid(8);

    private final int value;

    PositionMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PositionMode parse(int id) {
        PositionMode[] types = values();
        if(types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid GpsFixType id: " + id);
    }

    public static PositionMode parse(String value) {
        switch(value.toLowerCase()) {
            case "a":
            case "auto":
            case "autonomous": return Auto;
            case "d":
            case "diff":
            case "differential": return Differential;
            case "e":
            case "estimated":
            case "deadreckoning":
            case "dead reckoning": return Estimated;
            case "m":
            case "man":
            case "manual": return Manual;
            case "s":
            case "simulated":
            case "simulation": return Simulation;
            case "n":
            case "notvalid":
            case "not valid": return NotValid;
            case "f":
            case "float":
            case "floatrtk":
            case "float rtk": return FloatRTK;
            case "r":
            case "rtk": return RTK;
            default: return Unknown;
        }
    }

    @Override
    public String toString() {
        switch(this) {
            case Auto: return "Auto";
            case Differential: return "Differential";
            case NotValid: return "Not Valid";
            case Unknown: return "Unknown";
            case RTK: return "RTK";
            case FloatRTK: return "Float RTK";
            case Estimated: return "Estimated";
            case Manual: return "Manual";
            case Simulation: return "Simulation";
            default: throw new IllegalArgumentException();
        }
    }

    public String toStringF() {
        switch(this) {
            case Auto: return "A (Auto)";
            case Differential: return "D (Differential)";
            case NotValid: return "N (Not Valid)";
            case Unknown: return "Unknown";
            case RTK: return "R (RTK)";
            case FloatRTK: return "F (Float RTK)";
            case Estimated: return "E (Estimated)";
            case Manual: return "M (Manual)";
            case Simulation: return "S (Simulation)";
            default: throw new IllegalArgumentException();
        }
    }
}