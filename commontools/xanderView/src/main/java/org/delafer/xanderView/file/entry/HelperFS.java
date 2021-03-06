package org.delafer.xanderView.file.entry;

import java.io.IOException;
import java.io.RandomAccessFile;
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
		MappedByteBuffer buf = fc.map(MapMode.READ_ONLY, 0, size);

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
	    try {
	    	((sun.nio.ch.DirectBuffer)cb).cleaner().clean();
	    } catch(Exception ex) { }
	    cb = null;
	}

	public static int min(long a1, long a2) {
		return (int) (a1 < a2 ? a1 : a2);
	}
}