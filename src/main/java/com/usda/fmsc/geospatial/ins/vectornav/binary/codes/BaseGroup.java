package com.usda.fmsc.geospatial.ins.vectornav.binary.codes;

public abstract class BaseGroup {
    protected final int value, groupDataSize;
    protected final byte[] fieldDataOffsets;


    public BaseGroup(int value) {
        this.value = value;
        this.fieldDataOffsets = new byte[getFieldSizes().length];

        int totalSize = 0;
        for (int i = 0; i < this.fieldDataOffsets.length; i++) {
            if (((value >> i) & 1) == 1) {
                byte fieldSize = getFieldSizes()[i];
                totalSize += fieldSize;
                fieldDataOffsets[i] = fieldSize;
            } else {
                fieldDataOffsets[i] = -1;
            }
        }

        groupDataSize = totalSize;
    }


    public final int getValue() {
        return value;
    }


    protected abstract byte[] getFieldSizes();

    public final int getSizeOfUsedFields() {
        return groupDataSize;
    }


    public int getFieldDataOffset(int fieldMask) {
        int idx = getFieldIndex(fieldMask);
        if (idx < 0 || idx >= fieldDataOffsets.length) throw new RuntimeException("Invalid Field Index");
        return fieldDataOffsets[idx];
    }

    public int getFieldDataOffsetByIndex(int index) {
        if (index < 0 || index >= fieldDataOffsets.length) throw new RuntimeException("Invalid Field Index");
        return index > -1 ? fieldDataOffsets[index] : index;
    }

    protected final int getFieldIndex(int fieldMask) {
        for (int i = 0; i < fieldDataOffsets.length; i++) {
            if (((fieldMask >> i) & 1) == 1) return i;
        }

        return -1;
    }


    public boolean hasAny() {
        return value != 0;
    }
    

    public static int getFieldDataOffset(int fieldMask, int usedFieldsMask, byte[] fieldSizes) {
        int index = -1;
        for (int i = 0, fl = 0; i < Short.BYTES * 2 && fl < fieldSizes.length; i++) {
            if (((fieldMask >> i) & 1) == 1)
                    break;
            else if (((usedFieldsMask >> i) & 1) == 1) {
                index += fieldSizes[i];
            }
        }

        return index;
    }

    public static int getSizeOfUsedFields(byte[] fieldSizes, int fieldsUsedMask) {
        int totalSize = 0;
        for (int i = 0; i < Short.BYTES * 8; i++) {
            if (((fieldsUsedMask >> i) & 1) == 1) {
                totalSize += fieldSizes[i];
            }
        }
        return totalSize;
    }

    public static boolean areAllFieldsEnabled(int value, int usedFieldsMask) {
        return (value & usedFieldsMask) == usedFieldsMask;
    }

}
