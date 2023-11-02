package com.usda.fmsc.geospatial.ins.vectornav.codes;

public enum ErrorCode {
    Unknown(0),
    HardFault(1),
    SerialBufferOverflow(2),
    InvalidChecksum(3),
    InvalidCommand(4),
    NotEnoughParams(5),
    TooManyParams(6),
    InvalidParam(7),
    InvalidRegister(8),
    UnauthorizedAccess(9),
    WatchdogReset(10),
    OutputBufferOverflow(11),
    InsufficientBaudRate(12),
    ErrorBufferOverflow(255);

    private static final ErrorCode[] VALUES = values();
    private final int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ErrorCode parse(int value) {
        for (ErrorCode code : VALUES) {
            if (code.value == value)
                return code;
        }

        return Unknown;
    }

    public static ErrorCode parse(String value) {
        return parse(Integer.parseInt(value, 16));
    }
}
