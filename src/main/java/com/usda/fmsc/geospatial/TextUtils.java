package com.usda.fmsc.geospatial;

public class TextUtils {
    public static boolean isEmpty(String text) {
        return text == null || text.trim().length() < 1;
    }

    public static short hexStrToUInt16(byte[] buffer, int index)
	{
		short result = (short) (hexStrToByte(buffer, index) << 8);

		result += hexStrToByte(buffer, index + 2);

		return result;
	}

	public static byte hexStrToByte(byte[] buffer, int index)
	{
		byte result = (byte) (hexCharToByte(buffer[index]) << 4);

		result += hexCharToByte(buffer[index + 1]);

		return result;
	}

    public static byte hexCharToByte(byte hexChar)
	{
		if (hexChar < ':')
			return (byte) (hexChar - '0');

		if (hexChar < 'G')
			return (byte) (hexChar - '7');

		return (byte) (hexChar - 'W');
	}

    public static byte checksum8(byte[] buffer, int index, int length)
	{
		byte xorVal = 0;

		for (int i = 0; i < length; i++)
			xorVal ^= buffer[index + i];

		return xorVal;
	}

	public static short checksum16(byte[] buffer, int index, int length)
	{
		short crc = 0;

		for (int i = 0; i < length; i++)
		{
			crc = (short) ((crc >> 8) | (crc << 8));

			crc ^= buffer[index + i];
			crc ^= (short) ((short) (crc & 0xFF) >> 4);
			crc ^= (short) ((crc << 8) << 4);
			crc ^= (short) (((crc & 0xFF) << 4) << 1);
		}

		return crc;
	}
}