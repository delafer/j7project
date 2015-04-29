package org.delafer.xanderView.interfaces;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.List;

import org.delafer.xanderView.interfaces.CommonContainer.ContentChangeWatcher;

import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.impl.RandomAccessNioStream;

public class SevenZipReader implements IAbstractReader {


	private RandomAccessFile randomAccessFile;
	private IInArchive archive;


	public void read(String fileName, List<ImageEntry<?>> entries) {
		try {

			boolean neu = true;
			randomAccessFile = new RandomAccessFile(fileName, "r");
			IInStream stream = !neu ? new RandomAccessFileInStream(randomAccessFile) : new RandomAccessNioStream(fileName);

			archive = SevenZip.openInArchive(ArchiveFormat.ZIP, stream);
			int numberOfItems = archive.getNumberOfItems();

			for (int i = 0; i < numberOfItems; i++) {
				ZipImageEntry entry = getEntryByIdentifier(i);
				entries.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close() {
		try {
			archive.close();
			randomAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void register(ContentChangeWatcher watcher) {

	}

	@Override
	public void initialize(String location) {

	}

	@SuppressWarnings("unchecked")
	public  ZipImageEntry getEntryByIdentifier(Object id) throws IOException {
		if (id == null) return null;
		int i = ((Number)id).intValue();
		String pathName = (String)archive.getProperty(i, PropID.PATH);
		Long size = (Long)archive.getProperty(i, PropID.SIZE);
		return new ZipImageEntry(archive, i, pathName, size);
	}

	@Override
	public Comparator<ImageEntry<?>> getComparator() {
		return null;
	}

}
