package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public class GPS1Group extends BaseGroup {
    public static final int None = 0;
    
    public static final int ALL_FIELDS = 0b1111111111111111;

    public static final byte[] FIELD_SIZES = { 8, 8,  2,  1,  1,  24, 24, 12, 12, 12, 4,  4,  2, 28, 0, 0 };


    public GPS1Group(int value) {
        super(value);
    }


    @Override
    protected byte[] getFieldSizes() {
        return FIELD_SIZES;
    }


}
