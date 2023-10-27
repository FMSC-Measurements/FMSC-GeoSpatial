package com.usda.fmsc.geospatial.gnss.codes;

public enum GnssFixQuality {
    NoFix(0),
    GPS(1),
    DGPS(2),
    PPS(3),
    RTK(4),
    RTK_Float(5),
    Estimated(6),
    Manual(7),
    Simulation(8),

    RTK_Float_SureFix(10),
    RTK_Int_SureFix(11),
    RTK_SureFix(12),
    aRTK_Int(13),
    aRTK_Float(14),
    aRTK_Atlas_Converged(15),
    aRTK_Atlas_UnConverged(16),
    Atlas_Converged(17),
    Atlas_UnConverged(18);

    private final int value;

    GnssFixQuality(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GnssFixQuality parse(int id) {
        GnssFixQuality[] types = values();
        if (types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid GpsFixType id: " + id);
    }

    public static GnssFixQuality parse(String value) {
        switch (value.replace("_", "").toLowerCase()) {
            case "nofix":
            case "no fix":
            case "0":
                return NoFix;
            case "gps":
            case "1":
                return GPS;
            case "dgps":
            case "gpsd":
            case "2":
                return DGPS;
            case "pps":
            case "3":
                return PPS;
            case "rtk":
            case "rtkint":
            case "realtime":
            case "realtimekinematic":
            case "4":
                return RTK;
            case "float":
            case "floatrtk":
            case "rtkfloat":
            case "5":
                return RTK_Float;
            case "estimate":
            case "estimated":
            case "6":
                return Estimated;
            case "manual":
            case "7":
                return Manual;
            case "simulate":
            case "simulation":
            case "8":
                return Simulation;

            case "rtkfloatsurefix":
            case "rtkfloat(surefix)":
            case "10":
                return RTK_Float_SureFix;
            case "rtkintsurefix":
            case "rtkint(surefix)":
            case "11":
                return RTK_Int_SureFix;
            case "rtksurefix":
            case "rtk(surefix)":
            case "12":
                return RTK_SureFix;
            case "artkint":
            case "13":
                return aRTK_Int;
            case "artkfloat":
            case "14":
                return aRTK_Int;
            case "artkatlasconverged":
            case "15":
                return aRTK_Atlas_Converged;
            case "artkatlasunconverged":
            case "16":
                return aRTK_Atlas_UnConverged;
            case "atlasconverged":
            case "17":
                return Atlas_Converged;
            case "atlasunconverged":
            case "18":
                return Atlas_Converged;
            default:
                throw new IllegalArgumentException("Invalid GnssFixQuality Name: " + value);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case NoFix:
                return "No Fix";
            case GPS:
                return "GPS";
            case DGPS:
                return "GPS (Diff)";
            case PPS:
                return "PPS";
            case RTK:
                return "RTK";
            case RTK_Float:
                return "RTK Float";
            case Estimated:
                return "Estimated";
            case Manual:
                return "Manual";
            case Simulation:
                return "Simulation";
                
            case RTK_Float_SureFix:
                return "RTK-Float (SureFix)";
            case RTK_Int_SureFix:
                return "RTK-Integer (SureFix)";
            case RTK_SureFix:
                return "RTK (SureFix)";
            case aRTK_Atlas_Converged:
                return "aRTK Atlas (Converged)";
            case aRTK_Atlas_UnConverged:
                return "aRTK Atlas (UnConverged)";
            case aRTK_Float:
                return "aRTK Float";
            case aRTK_Int:
                return "aRTK Int";
            case Atlas_Converged:
                return "Atlas (Converged)";
            case Atlas_UnConverged:
                return "Atlas (UnConverged)";
            default:
                throw new IllegalArgumentException();
        }
    }

    public String toStringF() {
        switch (this) {
            case NoFix:
                return "0 (No Fix)";
            case GPS:
                return "1 (GPS)";
            case DGPS:
                return "2 (DGPS)";
            case PPS:
                return "3 (PPS)";
            case RTK:
                return "5 (RTK)";
            case RTK_Float:
                return "4 (RTK Float)";
            case Estimated:
                return "6 (Estimated)";
            case Manual:
                return "7 (Manual)";
            case Simulation:
                return "8 (Simulation)";

            case RTK_Float_SureFix:
                return "10 (RTK-Float SureFix)";
            case RTK_Int_SureFix:
                return "11 (RTK-Integer SureFix)";
            case RTK_SureFix:
                return "12 (RTK SureFix)";
            case aRTK_Int:
                return "13 (aRTK Int)";
            case aRTK_Float:
                return "14 (aRTK Float)";
            case aRTK_Atlas_Converged:
                return "15 (aRTK Atlas Converged)";
            case aRTK_Atlas_UnConverged:
                return "16 (aRTK Atlas UnConverged)";
            case Atlas_Converged:
                return "17 (Atlas Converged)";
            case Atlas_UnConverged:
                return "18 (Atlas UnConverged)";
            default:
                throw new IllegalArgumentException();
        }
    }

}
