package com.usda.fmsc.geospatial.nmea.exceptions;

public class NmeaException extends RuntimeException {
    private String sentence;

    public NmeaException() {
        super("Unknown NMEA Exception");
    }

    public NmeaException(String message, String sentence) {
        super(message);
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }
}