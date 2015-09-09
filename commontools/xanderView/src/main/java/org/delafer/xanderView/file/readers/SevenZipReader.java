package org.delafer.xanderView.file.readers;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.List;

import org.delafer.xanderView.file.ContentChangeWatcher;
import org.delafer.xanderView.file.entry.ImageEntry;
import org.delafer.xanderView.file.entry.ZipImageEntry;
import org.delafer.xanderView.file.entry.ImageEntry.ImageType;
import org.delafer.xanderView.gui.config.ApplInstance;
import org.delafer.xanderView.interfaces.IAbstractReader;

import net.j7.commons.io.FileUtils;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.impl.RandomAccessNioStream;

public class SevenZipReader implements IAbstractReader {


	private RandomAccessFile randomAccessFile;
	private IInArchive archive;
	private File sourceFile;



	public SevenZipReader(File sourceFile) {
		super();

		this.sourceFile = sourceFile;
	}

	public void read(List<ImageEntry<?>> entries) {
		try {
			String fileName = getContainerPath();
			boolean neu = true;
			randomAccessFile = new RandomAccessFile(fileName, "r");
			IInStream stream = !neu ? new RandomAccessFileInStream(randomAccessFile) : new RandomAccessNioStream(fileName);
			archive = SevenZip.openInArchive(null, stream);
			int numberOfItems = archive.getNumberOfItems();

			for (int i = 0; i < numberOfItems; i++) {
				ZipImageEntry entry = getEntryByIdentifier(i);
//				System.out.println(entry);
				if (entry != null) entries.add(entry);
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
	public void initialize() {
	}

	@SuppressWarnings("unchecked")
	public  ZipImageEntry getEntryByIdentifier(Object id) throws IOException {
		if (id == null) return null;
		int i = ((Number)id).intValue();
		String pathName = (String)archive.getProperty(i, PropID.PATH);
		Long size = (Long)archive.getProperty(i, PropID.SIZE);

		ImageType imageType = ImageEntry.getType(pathName);
		if (imageType.equals(ImageType.UNKNOWN)) return null;

		return new ZipImageEntry(this, archive, i, pathName, size);
	}

	@Override
	public Comparator<ImageEntry<?>> getComparator() {
		return null;
	}

	@Override
	public String getContainerPath() {
		return FileUtils.extractFullPathName(sourceFile);
	}

	@Override
	public Object getSingleEntry() {
		return null;
	}

}
