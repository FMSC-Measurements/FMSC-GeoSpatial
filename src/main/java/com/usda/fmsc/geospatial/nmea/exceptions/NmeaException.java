package com.usda.fmsc.geospatial.nmea.exceptions;

@SuppressWarnings("unused")
public class NmeaException extends RuntimeException {
    public NmeaException() {
        super("Unknown NMEA Exception");
    }

    public NmeaException(String message) {
        super(message);
    }
}