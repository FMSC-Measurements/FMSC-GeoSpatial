package com.usda.fmsc.geospatial.gnss.hemisphere.binary.codes;

public enum NavMode {
    NoFix(0),
    FIX_2D(1),
    FIX_3D(2),
    FIX_2D_Diff(3),
    FIX_3D_Diff(4),
    RTK_Float(5),
    RTK_Int(6),

    RTK_Float_SureFix(7),
    RTK_Int_SureFix(8),
    RTK_SureFix(9),
    aRTK_Int(10),
    aRTK_Float(11),
    aRTK_Atlas_Converged(12),
    aRTK_Atlas_UnConverged(13),
    Atlas_Converged(14),
    Atlas_UnConverged(15);

    private final int value;

    NavMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static NavMode parse(int id) {
        NavMode[] types = values();
        if (types.length > id && id > -1)
            return types[id];
        throw new IllegalArgumentException("Invalid NavMode id: " + id);
    }

    public static NavMode parse(int id, boolean hasDifferential) {
        return parse((hasDifferential && id > 0 && id < 3) ? (id + 2) : id);
    }

    @Override
    public String toString() {
        switch (this) {
            case Atlas_Converged:
                return "Atlas (Converged)";
            case Atlas_UnConverged:
                return "Atlas (UnConverged)";
            case FIX_2D:
                return "2D";
            case FIX_2D_Diff:
                return "2D-Diff";
            case FIX_3D:
                return "3D";
            case FIX_3D_Diff:
                return "3D-Diff";
            case NoFix:
                return "No Fix";
            case RTK_Float:
                return "RTK-Float";
            case RTK_Float_SureFix:
                return "RTK-Float (SureFix)";
            case RTK_Int:
                return "RTK-Integer";
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
            default:
                return "Unknown";
        }
    }
}