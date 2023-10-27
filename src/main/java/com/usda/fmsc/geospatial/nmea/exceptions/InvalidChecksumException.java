package com.usda.fmsc.geospatial.nmea.exceptions;

public class InvalidChecksumException extends NmeaException {
    public InvalidChecksumException(String sentence) {
        super("The nmea sentence had in invalid checksum.", sentence);
    }
}
