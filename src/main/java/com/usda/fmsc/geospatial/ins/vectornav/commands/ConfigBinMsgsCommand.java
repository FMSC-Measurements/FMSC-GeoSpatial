package com.usda.fmsc.geospatial.ins.vectornav.commands;

import com.usda.fmsc.geospatial.ins.vectornav.binary.BinaryMsgConfig;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.AsyncMode;
import com.usda.fmsc.geospatial.ins.vectornav.codes.RegisterID;

public class ConfigBinMsgsCommand extends WriteRegisterCommand {
    public static final int DEFAULT_RATE_DIVISOR = 80; //800hz / 80 = 10hz

    private final BinaryMsgConfig binMsgConfig;
    private final AsyncMode asyncMode;
    private final int rateDivisor;


    public ConfigBinMsgsCommand(BinaryMsgConfig binMsgConfig) {
        this(binMsgConfig, DEFAULT_RATE_DIVISOR);
    }

    public ConfigBinMsgsCommand(BinaryMsgConfig binMsgConfig, int rateDivisor) {
        this(binMsgConfig, RegisterID.BINARY_OUTPUT_1, AsyncMode.BothPortsFixedRate, rateDivisor);
    }

    public ConfigBinMsgsCommand(BinaryMsgConfig binMsgConfig, AsyncMode asyncMode, int rateDivisor) {
        this(binMsgConfig, RegisterID.BINARY_OUTPUT_1, asyncMode, rateDivisor);
    }
    
    public ConfigBinMsgsCommand(BinaryMsgConfig binMsgConfig, RegisterID registerID, AsyncMode asyncMode, int rateDivisor) {
        super(registerID);

        if (!(registerID == RegisterID.BINARY_OUTPUT_1 || registerID == RegisterID.BINARY_OUTPUT_2 || registerID == RegisterID.BINARY_OUTPUT_3)) {
            throw new RuntimeException("Invalid Binary Output port register id");
        }

        this.binMsgConfig = binMsgConfig;
        this.asyncMode = asyncMode;
        this.rateDivisor = rateDivisor;
    }

    @Override
    protected String getPayload() {
        return String.format("%d, %d, %d, %04X%s%s*XX",
            asyncMode.getValue(),
            rateDivisor,
            binMsgConfig.getOutputGroups().getValue(),
            binMsgConfig.getGroupField1(),
            binMsgConfig.hasGroupField2() ? String.format(", %04X", binMsgConfig.getGroupField2()) : EMPTY,
            binMsgConfig.hasGroupField3() ? String.format(", %04X", binMsgConfig.getGroupField3()) : EMPTY);
    }
}
