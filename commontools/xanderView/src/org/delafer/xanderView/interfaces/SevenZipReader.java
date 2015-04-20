package org.delafer.xanderView.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import net.j7.commons.collections.SortedLinkedList;
import net.j7.commons.utils.Metrics;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.impl.RandomAccessNioStream;

public class SevenZipReader extends CommonContainer {

	public SevenZipReader(String location) {
		super(location, null);
	}

	@Override
	protected void readStructure(SortedLinkedList<IImageEntry> list) throws Exception {
		IInArchive archive;
		RandomAccessFile randomAccessFile;

		randomAccessFile = new RandomAccessFile(location, "r");

		archive = SevenZip.openInArchive(ArchiveFormat.ZIP, // null - autodetect
				new RandomAccessFileInStream(randomAccessFile));
		int numberOfItems = archive.getNumberOfItems();
		for (int i = 0; i < numberOfItems; i++) {
			System.out.println(String.format(
					"%9s | %9s | %s", //
					archive.getProperty(i, PropID.SIZE), archive.getProperty(i, PropID.PACKED_SIZE),
					archive.getProperty(i, PropID.PATH)));
		}
		archive.close();
		randomAccessFile.close();

	}

	public static void main(String[] args) {
		try {
			IInArchive archive;
			RandomAccessFile randomAccessFile;
			String fileName = "d:\\test2.zip";
			boolean neu = true;
			randomAccessFile = new RandomAccessFile(fileName, "r");
			IInStream stream = !neu ? new RandomAccessFileInStream(randomAccessFile) : new RandomAccessNioStream(
					fileName);
			System.out.println(stream.getClass().getSimpleName());
			Metrics m = net.j7.commons.utils.Metrics.start();
			archive = SevenZip.openInArchive(ArchiveFormat.ZIP, stream// null -
																		// autodetect
					);
			int numberOfItems = archive.getNumberOfItems();
			for (int i = 0; i < numberOfItems; i++) {
				String a = String.format(
						"%9s | %9s | %s", //
						archive.getProperty(i, PropID.SIZE), archive.getProperty(i, PropID.PACKED_SIZE),
						archive.getProperty(i, PropID.PATH));
			}
			if (numberOfItems>10) numberOfItems = 10;
			int[] in = new int[numberOfItems];
			for (int i = 0; i < in.length; i++) {
				in[i] = i;
			}
			archive.extract(in, false, // Non-test mode
					new MyExtractCallback(archive));

			archive.close();
			m.measure("read time");
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SevenZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class MyExtractCallback implements IArchiveExtractCallback {
		private int hash = 0;
		private int size = 0;
		private int index;
		private boolean skipExtraction;
		private IInArchive inArchive;

		public MyExtractCallback(IInArchive inArchive) {
			this.inArchive = inArchive;
		}

		public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
			this.index = index;
			skipExtraction = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
			if (skipExtraction || extractAskMode != ExtractAskMode.EXTRACT) {
				return null;
			}
			return new ISequentialOutStream() {
				public int write(byte[] data) throws SevenZipException {
					System.out.println(index+" "+data.length);
					hash ^= Arrays.hashCode(data);
					size += data.length;
					return data.length; // Return amount of proceed data
				}
			};
		}

		public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
		}

		public void setTotal(long total) throws SevenZipException {

		}

		@Override
		public void setCompleted(long completeValue) throws SevenZipException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
			// TODO Auto-generated method stub

		}

	}

}
