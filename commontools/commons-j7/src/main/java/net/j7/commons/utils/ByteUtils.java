package net.j7.commons.utils;


public final class ByteUtils {

    private static final long MIN_VALUE_B = Long.MIN_VALUE + 1;

	public static int byteArrayToInt(byte[] b) {
        return
        (b[0] << 24) +
        ((b[1] & 0xFF) << 16) +
        ((b[2] & 0xFF) << 8) +
        (b[3] & 0xFF);
    }

    public static byte[] intToByteArray(int value) {
        return new byte[] {
        		(byte) (value >>> 24),
        		(byte) (value >>> 16),
        		(byte) (value >>> 8),
        		(byte) value
        		};
    }

    private static byte byteAt(byte[] b, int p) {
    	if (b != null && p<b.length) return b[p];
    	return 0;
    }

    public static long byteArrayToLong(byte[] b) {
        //8 16 24 32 40 48 56 ..
    	return
        ((long)byteAt(b,0) << 56) +
        ((long)(byteAt(b,1) & 0xFF) << 48) +
        ((long)(byteAt(b,2) & 0xFF) << 40) +
        ((long)(byteAt(b,3) & 0xFF) << 32) +
        ((long)(byteAt(b,4) & 0xFF) << 24) +
        ((long)(byteAt(b,5) & 0xFF) << 16) +
        ((long)(byteAt(b,6) & 0xFF) << 8) +
        ((long)byteAt(b,7) & 0xFF);


    }

    public static byte[] longToByteArray(long value) {
        return new byte[] {
        		(byte) (value >>> 56),
        		(byte) (value >>> 48),
        		(byte) (value >>> 40),
        		(byte) (value >>> 32),
        		(byte) (value >>> 24),
        		(byte) (value >>> 16),
        		(byte) (value >>> 8),
        		(byte) value
        		};



    }

    public static byte[] longObjToByteArray(Long value) {
        return longToByteArray(value != null ? value.longValue() : MIN_VALUE_B);
    }

    public static Long byteArrayToLongObj(byte[] b) {
    	long value = byteArrayToLong(b);
    	return value == MIN_VALUE_B ? null : Long.valueOf(value);
    }

    public static byte[] doubleToByteArray(double value) {
    	long l = Double.doubleToLongBits(value);
    	return longToByteArray(l);
    }

    public static double byteArrayToDouble(byte[] b) {
    	long l = byteArrayToLong(b);
    	return Double.longBitsToDouble(l);
    }


    public static byte[] floatToByteArray(float value) {
    	int l = Float.floatToIntBits(value);
    	return intToByteArray(l);
    }

    public static float byteArrayToFloat(byte[] b) {
    	int l = byteArrayToInt(b);
    	return Float.intBitsToFloat(l);
    }


}
