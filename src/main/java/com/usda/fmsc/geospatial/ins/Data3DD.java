package com.usda.fmsc.geospatial.ins;

public class Data3DD {
    private double x, y, z;


    public Data3DD(Data3DF data) {
        this.x = data.getX();
        this.y = data.getY();
        this.z = data.getZ();
    }

    public Data3DD(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Data3DD(Data3DD data) {
        this.x = data.x;
        this.y = data.y;
        this.z = data.z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    
    // Addition of vectors
    public Data3DD add(Data3DD data) {
        return new Data3DD(this.x + data.x, this.y + data.y, this.z + data.z);
    }

    // Scalar multiplication of vectors
    public Data3DD multiply(double scalar) {
        return new Data3DD(this.x * scalar, this.y * scalar, this.z * scalar);
    }
}
