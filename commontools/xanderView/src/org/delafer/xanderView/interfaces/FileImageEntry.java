package org.delafer.xanderView.interfaces;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.delafer.xanderView.hash.Hasher;

import net.j7.commons.base.Equals;
import net.sf.sevenzipjbinding.IInArchive;

public class FileImageEntry extends ImageEntry<String> {


//	IInArchive archive;
	String identifier;


	public FileImageEntry(String fullPath, String name, long size) {
		this.name = name;
		this.size = size;
		this.identifier = fullPath;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public byte[] content() {

		try {
			byte[] ret = readNIO(identifier);
			calcCRC(ret);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void calcCRC(byte[] ret) {
		this.crc = Hasher.hash().calc(ret, this.size);
	}

	private static void closeDirectBuffer(ByteBuffer cb) {
	    if (!cb.isDirect()) return;

	    // we could use this type cast and call functions without reflection code,
	    // but static import from sun.* package is risky for non-SUN virtual machine.
	    //try { ((sun.nio.ch.DirectBuffer)cb).cleaner().clean(); } catch (Exception ex) { }
	    try {
	        Method cleaner = cb.getClass().getMethod("cleaner");
	        cleaner.setAccessible(true);
	        Method clean = Class.forName("sun.misc.Cleaner").getMethod("clean");
	        clean.setAccessible(true);
	        clean.invoke(cleaner.invoke(cb));
	    } catch(Exception ex) { }
	    cb = null;
	}

	public final static byte[] readNIO(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		FileChannel fc = fis.getChannel();
		final int size = fis.available();
		MappedByteBuffer buf = fc.map(MapMode.READ_ONLY, 0, size);
        final byte[] bytes = new byte[size];
        buf.get(bytes);
        buf.clear();
        fc.force(true);
        fc.close();
        fis.close();
        closeDirectBuffer(buf);

        return bytes;
	}

	@Override
	public int hashCode() {
		int result =  ((name == null) ? 0 : name.hashCode());
		result = 31 * result + ((identifier == null) ? 0 : identifier.hashCode());
//		result = 31 * result + (int) (size ^ (size >>> 32));

		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		final FileImageEntry o = (FileImageEntry) obj;
		if (!Equals.equal(this.name, o.name)) return false;
		if (!Equals.equal(this.identifier, o.identifier)) return false;
		if (this.size != 0l && o.size != 0l && !Equals.equal(this.size, o.size)) return false;
		return true;
	}


}
