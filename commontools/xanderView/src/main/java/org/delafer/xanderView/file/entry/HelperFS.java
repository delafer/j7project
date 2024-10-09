package org.delafer.xanderView.file.entry;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class HelperFS {


	public final static Buf readDataBuf(String fileName, int size) throws IOException {

		final RandomAccessFile fis = new RandomAccessFile(fileName, "r");
		final FileChannel fc = fis.getChannel();
		if (size < 0) {
			size = (int)fc.size();
		}

		MappedByteBuffer buf = null;
		try {
			buf = fc.map(MapMode.READ_ONLY, 0, size);
		} catch (java.io.IOException e) {
			System.out.println("filename: "+fileName+" size: "+size);
			throw e;
		}

		return new Buf(buf, fis);
	}


	public final static byte[] readData(String fileName, int size) throws IOException {

		final RandomAccessFile fis = new RandomAccessFile(fileName, "r");
		final FileChannel fc = fis.getChannel();
		if (size < 0) {
			size = (int)fc.size();
		}
		MappedByteBuffer buf = fc.map(MapMode.READ_ONLY, 0, size);
        final byte[] bytes = new byte[size];
        buf.get(bytes);
        fc.force(true);

        fis.close();
        closeDirectBuffer(buf);

        return bytes;
	}

	@SuppressWarnings("restriction")
	public static void closeDirectBuffer(ByteBuffer cb) {
	    if (!cb.isDirect()) return;
//      java < 9
//	    try {
//	    	((sun.nio.ch.DirectBuffer)cb).cleaner().clean();
//	    } catch(Exception ex) { }
//	    cb = null;

        try {
			Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
			Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			Object unsafe = unsafeField.get(null);
			Method invokeCleaner = unsafeClass.getMethod("invokeCleaner", ByteBuffer.class);
			invokeCleaner.invoke(unsafe, cb);
        } catch (ReflectiveOperationException e) {

			boolean skip = false;
			if (e instanceof InvocationTargetException ite && ite.getTargetException() != null) {
				String msg = ite.getTargetException().getMessage();
				skip = msg != null && (msg.contains("duplicate") || msg.contains("slice"));
			}
			if (!skip) {
				e.printStackTrace();
			}
        }

	}

	public static int min(long a1, long a2) {
		return (int) (a1 < a2 ? a1 : a2);
	}
}
