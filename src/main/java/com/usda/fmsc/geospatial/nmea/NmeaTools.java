package com.usda.fmsc.geospatial.nmea;

public class NmeaTools {
    public static String[] tokenize(String nmea) {
        return nmea.substring(0, nmea.indexOf("*")).split(",", -1);
    }

    public static boolean validateChecksum(String nmea) {
        try {
            if (nmea.length() > 10 && nmea.startsWith("$") && nmea.contains("*")) {
                String calcString = nmea.substring(1);
                int ast = calcString.indexOf("*");
                int nCheckSum = Integer.parseInt(calcString.substring(ast + 1, ast + 3), 16);
                calcString = calcString.substring(0, ast);

                int checksum = 0;

                for(int i = 0; i < calcString.length(); i++) {
                    checksum ^= (byte)calcString.charAt(i);
                }
                return checksum == nCheckSum;
            }
        } catch (Exception e) {
            //
        }

        return false;
    }

    public static boolean validateChecksumOld(String nmea) {
        if (nmea.length() > 10 && nmea.startsWith("$") && nmea.contains("*")) {
            String calcString = nmea.substring(1);
            int ast = calcString.indexOf("*");
            String checkSumStr = calcString.substring(ast + 1, ast + 3);
            calcString = calcString.substring(0, ast);

            int checksum = 0;

            for(int i = 0; i < calcString.length(); i++) {
                checksum ^= (byte)calcString.charAt(i);
            }

            String hex = Integer.toHexString(checksum);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            return hex.equalsIgnoreCase(checkSumStr);
        }

        return false;
    }
    
    public static String sanitizeNmea(String nmea) {
        return nmea.replaceAll("[$]>", "");
    }
}
