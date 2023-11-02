package com.usda.fmsc.geospatial.ins.vectornav.codes;

public enum PauseResume {
    Pause(0),
    Resume(1);

    private final int value;

    private PauseResume(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PauseResume parse(String value) {
        if (value.length() == 1) {
            if (value.equals("0")) return PauseResume.Pause;
            if (value.equals("1")) return PauseResume.Resume;
        }

        throw new RuntimeException("Invalid Pause/Resume value");
    }
}
