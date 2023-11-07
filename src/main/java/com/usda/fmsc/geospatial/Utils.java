package com.usda.fmsc.geospatial;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Utils {
    public static byte getNumberOfSetBits(byte d)
	{
		byte count = 0;

		while (d != 0)
		{
			d &= (byte) (d - 1);
			count++;
		}

		return count;
	}
	
    public static int getNumberOfSetBits(short bits) {
		byte count = 0;

		while (bits != 0)
		{
			bits &= (bits - 1);
			count++;
		}

		return count;
    }


	public static boolean isMatch(byte[] pattern, byte[] input, int pos) {
        for(int i=0; i< pattern.length; i++) {
            if(pattern[i] != input[pos+i]) {
                return false;
            }
        }
        return true;
    }

    public static List<byte[]> split(byte[] pattern, byte[] input) {
        List<byte[]> l = new LinkedList<byte[]>();
        int blockStart = 0;
        for(int i=0; i<input.length; i++) {
            if(isMatch(pattern,input,i)) {
                l.add(Arrays.copyOfRange(input, blockStart, i));
                blockStart = i+pattern.length;
                i = blockStart;
            }
        }
        l.add(Arrays.copyOfRange(input, blockStart, input.length ));
        return l;
    }

    
    public static int getIndexOf(byte[] data, byte idxOf, int startIndex) {
        for (int i = startIndex; i < data.length; i++) {
            if (data[i] == idxOf) {
                return i;
            }
        }

        return -1;
    }

    public static int getIndexOf(byte[] data, byte[] idxOf, int startIndex) {
        for(int i = startIndex; i < data.length - idxOf.length + 1; ++i) {
            boolean found = true;
            for(int j = 0; j < data.length; ++j) {
                int ij = i + j;
                if (ij < idxOf.length && idxOf[ij] != data[j]) {
                    found = false;
                    break;
                }
            }
            if (found) return i;
        }

        return -1;
    }
    
    public static boolean validateChecksum16(byte[] data, int startIdx, int length) {
        if (startIdx + length <= data.length) {
            int crc = 0;                // initial value
            int polynomial = 0x1021;    // 0001 0000 0010 0001  (0, 5, 12)

            for (int i = startIdx; i < startIdx + length; i++) {
                byte b = data[i];
                for (int j = 0; j < 8; j++) {
                    boolean bit = ((b   >> (7-j) & 1) == 1);
                    boolean c15 = ((crc >> 15    & 1) == 1);
                    crc <<= 1;
                    if (c15 ^ bit) crc ^= polynomial;
                }
            }
            return (crc & 0xffff) == 0;
        }
        
        return false;
    }

}
