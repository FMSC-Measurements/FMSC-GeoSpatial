package com.usda.fmsc.geospatial.nmea41;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SentenceFormats {
    public static final DateTimeFormatter DateTimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS ddMMYY");
    public static final DateTimeFormatter DateTimeFormatterAlt = DateTimeFormat.forPattern("HHmmss ddMMYY");

    public static final DateTimeFormatter TimeFormatter = DateTimeFormat.forPattern("HHmmss.SSS");
    public static final DateTimeFormatter TimeFormatterAlt = DateTimeFormat.forPattern("HHmmss");
}
