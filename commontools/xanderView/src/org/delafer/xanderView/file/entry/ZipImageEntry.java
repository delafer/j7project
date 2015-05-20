package org.delafer.xanderView.file.entry;

import org.delafer.xanderView.hash.Hasher;

import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZipException;

public class ZipImageEntry extends ImageEntry<Integer> {


	IInArchive archive;
	Integer identifier;


	public ZipImageEntry(IInArchive archive, Integer id, String name, long size) {
		this.archive = archive;
		this.name = name;
		this.size = size;
		this.identifier = id;
	}

	@Override
	public Integer getIdentifier() {
		return identifier;
	}

	@Override
	public byte[] content() {

		try {
			MyExtractCallback extract = new MyExtractCallback(archive, size);
			archive.extract(new int[] {identifier}, false, extract);
			byte[] ret =  extract.data();
			calcCRC(ret);
			return ret;
		} catch (SevenZipException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void calcCRC(byte[] ret) {
		this.crc = Hasher.hash().calc(ret, this.size);
	}

	public static class MyExtractCallback implements IArchiveExtractCallback {
		private int pointer = 0;
		private int index;
		private boolean skipExtraction;
		private IInArchive inArchive;
		private byte[] content;

		public MyExtractCallback(IInArchive inArchive, long size) {
			this.inArchive = inArchive;
			content = new byte[(int)size];
		}

		public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
			this.index = index;

			skipExtraction = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
			if (skipExtraction || extractAskMode != ExtractAskMode.EXTRACT) return null;

			return new ISequentialOutStream() {
				public int write(byte[] data) throws SevenZipException {

					System.arraycopy(data, 0, content, pointer, data.length);
					pointer += data.length;
//					System.out.println(index+" "+data.length);
//					hash ^= Arrays.hashCode(data);
//					size += data.length;
					return data.length; // Return amount of proceed data
				}
			};
		}

		public byte[] data() {
			return this.content;
		}

		public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {}

		public void setTotal(long total) throws SevenZipException {}

		public void setCompleted(long completeValue) throws SevenZipException {}

		public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {}

	}

}
