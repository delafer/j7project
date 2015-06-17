package org.delafer.xanderView.file.entry;

import java.io.IOException;
import java.io.RandomAccessFile;

import net.sf.sevenzipjbinding.IInArchive;

import org.delafer.xanderView.hash.Hasher;

public class FileDirEntry extends ImageEntry<String> {


	IInArchive archive;
	public String identifier;

	public static FileDirEntry as(FileImageEntry entry) {
		return new FileDirEntry(entry.identifier, entry.name, entry.size);
	}


	public FileDirEntry(String fullPath, String name, long size) {
		this.name = name;
		this.size = size;
		this.identifier = fullPath;
		if (null != fullPath)
		try {
			calcCRC(readNIOHash(fullPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileDirEntry(String fullPath, String name, long size, long crc) {
		this.name = name;
		this.size = size;
		this.identifier = fullPath;
		this.crc  = crc;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public byte[] content() {
		throw new UnsupportedOperationException("byte[] content();");
	}

	private void calcCRC(byte[] ret) {
//		System.out.println("hash with size:"+this.size);
		this.crc = Hasher.hash().calc(ret, this.size);
	}


	private static int min(long a1, long a2) {
		return (int) (a1 < a2 ? a1 : a2);
	}

	public final byte[] readNIOHash(String fileName) throws IOException {

		RandomAccessFile raf = new RandomAccessFile(fileName, "r");

		this.size = raf.length();
//		System.out.println("size:"+size+" fileName"+fileName);

		byte[] buf = new byte[min(size, Hasher.SIZE)];
		raf.read(buf);

		raf.close();

        return buf;
	}

	@Override
	public int hashCode() {
		int result =  (int) (crc ^ (crc >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;

		if (!(obj instanceof ImageEntry))  return false;
		final ImageEntry<?> o = (ImageEntry<?>) obj;
		return (crc == o.crc);
	}

	@Override
	public String toString() {
		return String.format("FileDirEntry [identifier=%s, crc=%s]", identifier, crc);
	}



}
