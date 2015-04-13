package org.delafer.xanderView;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class XFileReader {
	
	public final static byte[] readNIO(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		FileChannel fc = fis.getChannel();
		final int size = fis.available();
		MappedByteBuffer buf = fc.map(MapMode.READ_ONLY, 0, size);
        final byte[] bytes = new byte[size];
        buf.get(bytes);
        
        fis.close();
        return bytes;
	}
	
	public final static byte[] readStd(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		byte[] bytes = new byte[fis.available()];
		fis.read(bytes);
        fis.close();
        return bytes;
	}
}
