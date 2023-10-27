package com.usda.fmsc.geospatial.ins;

public class NorthEastDown {
    private float north, east, down;

    public NorthEastDown(float north, float east, float down) {
        this.north = north;
        this.east = east;
        this.down = down;
    }

    public float getNorth() {
        return north;
    }

    public float getEast() {
        return east;
    }

    public float getDown() {
        return down;
    }
    
}
