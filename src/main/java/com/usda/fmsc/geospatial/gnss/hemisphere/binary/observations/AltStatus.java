package com.usda.fmsc.geospatial.gnss.hemisphere.binary.observations;

public class AltStatus {
    public final short ALT_STATUS;

    public AltStatus(short altStatus) {
        ALT_STATUS = altStatus;
    }

    public int getYaw() {
        return ALT_STATUS & 0xF;
    }

    public int getPitch() {
        return (ALT_STATUS >>> 4) & 0xF;
    }

    public int getRoll() {
        return (ALT_STATUS >>> 8) & 0xF;
    }

    public boolean isValid() {
        return ALT_STATUS != 0;
    }

    public Type getType() {
        return Type.parse((ALT_STATUS >>> 12) & 0xF);
    }

    public static enum Type {
        INVALID(0),
        GNSS(1),
        INERTIAL(2),
        MAGNETIC(3),
        Unknown(Integer.MAX_VALUE);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static Type parse(int id) {
            Type[] types = values();
            if (id < 4 && id > -1)
                return types[id];
            return Type.Unknown;
        }
    }
}
