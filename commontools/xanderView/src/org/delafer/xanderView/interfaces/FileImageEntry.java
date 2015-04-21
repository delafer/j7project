package org.delafer.xanderView.interfaces;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import net.j7.commons.base.Equals;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZipException;

public class FileImageEntry extends ImageEntry<String> {


	IInArchive archive;
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
			return readNIO(identifier);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static byte[] readNIO(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		FileChannel fc = fis.getChannel();
		final int size = fis.available();
		MappedByteBuffer buf = fc.map(MapMode.READ_ONLY, 0, size);
        final byte[] bytes = new byte[size];
        buf.get(bytes);

        fis.close();
        fc.close();
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
