package com.usda.fmsc.geospatial.ins.vectornav.binary;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.AttitudeGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.CommonGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.IMUGroup;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.OutputGroups;
import com.usda.fmsc.geospatial.ins.vectornav.binary.codes.TimeGroup;

//todo update for all 7 groups
public class BinaryMsgConfig {
    private final CommonGroup commonGroup;
    private final TimeGroup timeGroup;
    private final IMUGroup imuGroup;
    private final AttitudeGroup attitudeGroup;
    private final OutputGroups outputGroups;

    private int groupField1, groupField2, groupField3, fieldIdx = 1, totalPacketSize = 4, headerSize = 2;

    public BinaryMsgConfig(int commonGroup) {
        this(commonGroup, 0, 0, 0);
    }

    public BinaryMsgConfig(int commonGroup, int timeGroup, int imuGroup, int attitudeGroup) {
        this(new CommonGroup(commonGroup), new TimeGroup(timeGroup), new IMUGroup(imuGroup), new AttitudeGroup(attitudeGroup));
    }
    
    public BinaryMsgConfig(CommonGroup commonGroup, TimeGroup timeGroup, IMUGroup imuGroup, AttitudeGroup attitudeGroup) {
        this.commonGroup = commonGroup;
        this.timeGroup = timeGroup;
        this.imuGroup = imuGroup;
        this.attitudeGroup = attitudeGroup;

        int outputGroups = 0;
        groupField1 = groupField2 = groupField3 = -1;

        if (commonGroup.hasAny()) {
            outputGroups |= OutputGroups.Common;
            setGroupField(commonGroup.getValue());
            this.totalPacketSize += commonGroup.getSizeOfUsedFields();
        }

        if (timeGroup.hasAny()) {
            outputGroups |= OutputGroups.Time;
            setGroupField(timeGroup.getValue());
            this.totalPacketSize += timeGroup.getSizeOfUsedFields();
        }

        if (imuGroup.hasAny()) {
            outputGroups |= OutputGroups.IMU;
            setGroupField(imuGroup.getValue());
            this.totalPacketSize += imuGroup.getSizeOfUsedFields();
        }

        if (attitudeGroup.hasAny()) {
            outputGroups |= OutputGroups.Attitude;
            setGroupField(attitudeGroup.getValue());
            this.totalPacketSize += attitudeGroup.getSizeOfUsedFields();
        }

        this.outputGroups = new OutputGroups(outputGroups);
    }


    //todo update for all 7 groups
    public boolean isValid() {
        return outputGroups.getValue() <= 0b1110000 && //max of the 7 groups
                (commonGroup.hasAny() || timeGroup.hasAny() || imuGroup.hasAny() || attitudeGroup.hasAny()) && //no groups selected
                (!commonGroup.hasAny() || !timeGroup.hasAny() || !imuGroup.hasAny() || !attitudeGroup.hasAny());
    }

    
    private void setGroupField(int fields) {
        switch (fieldIdx) {
            case 1: groupField1 = fields; break;
            case 2: groupField2 = fields; break;
            case 3: groupField3 = fields; break;
            default: throw new RuntimeException("No more than 3 groups can be selected");
        }
        
        fieldIdx++;
        headerSize += 2;
        totalPacketSize += 2;
    }


    public CommonGroup getCommonGroup() {
        return commonGroup;
    }

    public TimeGroup getTimeGroup() {
        return timeGroup;
    }

    public IMUGroup getImuGroup() {
        return imuGroup;
    }

    public AttitudeGroup getAttitudeGroup() {
        return attitudeGroup;
    }
    

    public OutputGroups getOutputGroups() {
        return outputGroups;
    }

    
    public int getHeaderSize() {
        return headerSize;
    }

    public int getTotalPacketSize() {
        return totalPacketSize;
    }


    public int getGroupField1() {
        return groupField1;
    }


    public boolean hasGroupField2() {
        return groupField2 > -1;
    }

    public int getGroupField2() {
        return groupField2;
    }


    public boolean hasGroupField3() {
        return groupField3 > -1;
    }

    public int getGroupField3() {
        return groupField3;
    }


    public boolean containsIINSDataFields() {
        return attitudeGroup.hasLinearAccelBody() &&
            (commonGroup.hasDeltaTheta() || imuGroup.hasDeltaTheta()) &&
            (commonGroup.hasTimeStartup() || timeGroup.hasTimeStartup()) &&
            (commonGroup.hasYawPitchRoll() || attitudeGroup.hasYawPitchRoll());
    }


    public static BinaryMsgConfig fromBytes(byte[] data) {
        return fromBytes(ByteBuffer.wrap(data));
    }

    public static BinaryMsgConfig fromBytes(ByteBuffer data) {
        data = data.order(ByteOrder.LITTLE_ENDIAN);
        OutputGroups groups = new OutputGroups(data.get(1));
        if (!groups.isValid() || groups.getGroupCount() * 2 + 2 > data.capacity()) {
            return null;
        }

        int groupNum = 0;
        CommonGroup commonGroup;
        TimeGroup timeGroup;
        IMUGroup imuGroup;
        AttitudeGroup attitudeGroup;

        if (!groups.isValid())
            return null;

        if (groups.hasCommon()) {
            commonGroup = new CommonGroup(data.getShort(2) & 0xFFFFFFFF);
            groupNum++;
        } else {
            commonGroup = new CommonGroup(CommonGroup.None);
        }

        if (groups.hasTime()) {
            timeGroup = new TimeGroup(data.getShort((groupNum * 2) + 2) & 0xFFFFFFFF);
            groupNum++;
        } else {
            timeGroup = new TimeGroup(TimeGroup.None);
        }

        if (groups.hasIMU()) {
            imuGroup = new IMUGroup(data.getShort((groupNum * 2) + 2) & 0xFFFFFFFF);
            groupNum++;
        } else {
            imuGroup = new IMUGroup(IMUGroup.None);
        }

        if (groups.hasAttitude()) {
            attitudeGroup = new AttitudeGroup(data.getShort((groupNum * 2) + 2) & 0xFFFFFFFF);
            groupNum++;
        } else {
            attitudeGroup = new AttitudeGroup(AttitudeGroup.None);
        }

        return new BinaryMsgConfig(commonGroup, timeGroup, imuGroup, attitudeGroup);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((commonGroup == null) ? 0 : commonGroup.hashCode());
        result = prime * result + ((timeGroup == null) ? 0 : timeGroup.hashCode());
        result = prime * result + ((imuGroup == null) ? 0 : imuGroup.hashCode());
        result = prime * result + ((attitudeGroup == null) ? 0 : attitudeGroup.hashCode());
        result = prime * result + ((outputGroups == null) ? 0 : outputGroups.hashCode());
        result = prime * result + groupField1;
        result = prime * result + groupField2;
        result = prime * result + groupField3;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BinaryMsgConfig other = (BinaryMsgConfig) obj;
        if (commonGroup == null) {
            if (other.commonGroup != null)
                return false;
        } else if (!commonGroup.equals(other.commonGroup))
            return false;
        if (timeGroup == null) {
            if (other.timeGroup != null)
                return false;
        } else if (!timeGroup.equals(other.timeGroup))
            return false;
        if (imuGroup == null) {
            if (other.imuGroup != null)
                return false;
        } else if (!imuGroup.equals(other.imuGroup))
            return false;
        if (attitudeGroup == null) {
            if (other.attitudeGroup != null)
                return false;
        } else if (!attitudeGroup.equals(other.attitudeGroup))
            return false;
        if (outputGroups == null) {
            if (other.outputGroups != null)
                return false;
        } else if (!outputGroups.equals(other.outputGroups))
            return false;
        if (groupField1 != other.groupField1)
            return false;
        if (groupField2 != other.groupField2)
            return false;
        if (groupField3 != other.groupField3)
            return false;
        return true;
    }   
}
