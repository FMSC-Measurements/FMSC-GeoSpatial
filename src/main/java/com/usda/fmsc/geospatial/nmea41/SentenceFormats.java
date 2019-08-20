package com.usda.fmsc.geospatial.nmea41;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SentenceFormats {
    public static final DateTimeFormatter RMCTimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS ddMMYY");
    public static final DateTimeFormatter RMCTimeFormatterAlt = DateTimeFormat.forPattern("HHmmss ddMMYY");

    public static final DateTimeFormatter GGATimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS");
    public static final DateTimeFormatter GGATimeFormatterAlt = DateTimeFormat.forPattern("HHmmss");
}
