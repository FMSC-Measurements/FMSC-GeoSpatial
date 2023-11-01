package com.usda.fmsc.geospatial.ins.vectornav.nmea.codes;

public enum MessageID {
    UNKNOWN(0),
    RRG(1000), //Read Register
    WRG(1002), //Write Register
    WNV(1003), //Write Settings
    ERR(1004), //Error
    RFS(1005), //Restore Factory Settings
    RST(1006), //Reset
    FWU(1007), //Firmare Update
    CMD(1008), //Serial Command
    ASY(1009), //Asynchronous Output Pause Command
    BOM(1010), //Binary Output Poll

    //ASYNC Headers
    IMU(54), //IMU Measurements
    DTV(80), //Delta Theta and Delta Velocity

    YPR(8), //Yaw, Pitch, and Roll
    QTN(9), //Attitude Quaternion
    YMR(27), //Yaw, Pitch, Roll, Magnetic, Acceleration, and Angular Rates
    QMR(15), //Quaternion, Magnetic, Acceleration, and Angular Rates
    MAG(17), //Magnetic Measurements
    ACC(18), //Acceleration Measurements
    GYR(19), //Angular Rate Measurements
    MAR(20), //Magnetic, Acceleration and Angular Rates
    YBA(239), //Yaw, Pitch, Roll, True Body Acceleration, and Angular Rates
    YIA(240), //Magnetic, True Acceleration, and Angular Rates
    HVE(115); //Heave

    private final int value;

    MessageID(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MessageID parse(int id) {
        for (MessageID mid : values()) {
            if (mid.getValue() == id) return mid;
        }
        throw new IllegalArgumentException("Invalid MessageID value: " + id);
    }

    public static MessageID parse(String msg) {
        if (msg == null) {
            throw new NullPointerException();
        }

        if (msg.startsWith("$VN") && msg.length() > 6) {
            switch (msg.substring(3, 6).toUpperCase()) {
                case "RRG": return RRG;
                case "WRG": return WRG;
                case "ERR": return ERR;
                case "RFS": return RFS;
                case "RST": return RST;
                case "FWU": return FWU;
                case "CMD": return CMD;
                case "ASY": return ASY;
                case "BOM": return BOM;

                //ASYNC Headers
                case "IMU": return IMU;
                case "DTV": return DTV;
                case "YPR": return YPR;
                case "QTN": return QTN;
                case "YMR": return YMR;
                case "QMR": return QMR;
                case "MAG": return MAG;
                case "ACC": return ACC;
                case "GYR": return GYR;
                case "MAR": return MAR;
                case "YBA": return YBA;
                case "YIA": return YIA;
                case "HVE": return HVE;
            }
        }

        return UNKNOWN;
    }

    @Override
    public String toString() {
        switch (this) {
            case ASY: return "Asynchronous Output Pause Command";
            case BOM: return "Binary Output Poll";
            case CMD: return "Serial Command";
            case ERR: return "Error";
            case FWU: return "Firmare Update";
            case RFS: return "Restore Factory Settings";
            case RRG: return "Read Register";
            case RST: return "Reset";
            case WNV: return "Write Settings";
            case WRG: return "Write Register";
            
            //ASYNC Headers
            case IMU: return "IMU Measurements";
            case DTV: return "Delta Theta and Delta Velocity";
            case YPR: return "Yaw, Pitch, and Roll";
            case QTN: return "Attitude Quaternion";
            case YMR: return "Yaw, Pitch, Roll, Magnetic, Acceleration, and Angular Rates";
            case QMR: return "Quaternion, Magnetic, Acceleration, and Angular Rates";
            case MAG: return "Magnetic Measurements";
            case ACC: return "Acceleration Measurements";
            case GYR: return "Angular Rate Measurements";
            case MAR: return "Magnetic, Acceleration and Angular Rates";
            case YBA: return "Yaw, Pitch, Roll, True Body Acceleration, and Angular Rates";
            case YIA: return "Magnetic, True Acceleration, and Angular Rates";
            case HVE: return "Heave";
            
            case UNKNOWN:
            default: return "Unknown";
        }
    }

    public String toStringCode() {
        switch (this) {
            case ASY: return "ASY";
            case BOM: return "BOM";
            case CMD: return "CMD";
            case ERR: return "ERR";
            case FWU: return "FWU";
            case RFS: return "RFS";
            case RRG: return "RRG";
            case RST: return "RST";
            case WNV: return "WNV";
            case WRG: return "WRG";
            
            //ASYNC Headers
            case IMU: return "IMU";
            case DTV: return "DTV";
            case YPR: return "YPR";
            case QTN: return "QTN";
            case YMR: return "YMR";
            case QMR: return "QMR";
            case MAG: return "MAG";
            case ACC: return "ACC";
            case GYR: return "GYR";
            case MAR: return "MAR";
            case YBA: return "YBA";
            case YIA: return "YIA";
            case HVE: return "HVE";
            
            case UNKNOWN:
            default: return "Unknown";
        }
    }
}
