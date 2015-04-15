package org.delafer.xanderView.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import net.j7.commons.collections.SortedLinkedList;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;


public class SevenZipReader extends CommonContainer {

	public SevenZipReader(String location) {
		super(location, null);
	}

	@Override
	protected void readStructure(SortedLinkedList<IImageEntry> list) throws Exception {
	    ISevenZipInArchive archive;
	    RandomAccessFile randomAccessFile;

	    randomAccessFile = new RandomAccessFile(location, "r");

	    archive = SevenZip.openInArchive(ArchiveFormat.ZIP, // null - autodetect
	            new RandomAccessFileInStream(randomAccessFile));
	    int numberOfItems = archive.getNumberOfItems();
        for (int i = 0; i < numberOfItems; i++) {
            System.out.println(String.format("%9s | %9s | %s", //
            		archive.getProperty(i, PropID.SIZE),
            		archive.getProperty(i, PropID.PACKED_SIZE),
            		archive.getProperty(i, PropID.PATH)));
        }
	    archive.close();
	    randomAccessFile.close();

	}

	public static void main(String[] args) {
	    try {
			ISevenZipInArchive archive;
			RandomAccessFile randomAccessFile;

			randomAccessFile = new RandomAccessFile("d:\\sevenzipjbinding-4.65-1.06-rc-extr-only-AllWindows.zip", "r");

			archive = SevenZip.openInArchive(ArchiveFormat.ZIP, // null - autodetect
			        new RandomAccessFileInStream(randomAccessFile));
			int numberOfItems = archive.getNumberOfItems();
			for (int i = 0; i < numberOfItems; i++) {
			    System.out.println(String.format("%9s | %9s | %s", //
			    		archive.getProperty(i, PropID.SIZE),
			    		archive.getProperty(i, PropID.PACKED_SIZE),
			    		archive.getProperty(i, PropID.PATH)));
			}
			archive.close();
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

}
