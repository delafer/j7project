package org.delafer.xanderView.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.*;

import net.j7.commons.base.Equals;

import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;

public class CopyService {


	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient CopyService INSTANCE = new CopyService();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public final static CopyService instance() {
		return Holder.INSTANCE;
	}


	private CopyService() {
		init();
	}



	Set<FileDirEntry> images;
//	ListIterator<FileImageEntry> iterator;
	File pathFile;
	FileReader reader;


	public int size() {
		return images.size();
	}

	public void init() {
		String location = ApplConfiguration.instance().get(ApplConfiguration.CFG_COPY_DIR);
		initializeByPath(location);

	}

	private void initializeByPath(String locationArg) {
		this.pathFile = new File(locationArg);
		this.reader = new FileReader(pathFile);
		this.images = new HashSet<FileDirEntry>();
		initializeInternal();

	}

	private FileDirEntry findByName(String name) {
		for (FileDirEntry next : images) {
			if (Equals.equal(next.getIdentifier(), name)) return next;
		}
		return null;

	}

	public void copy(ImageEntry<?> entry) {
		FileDirEntry fde = new FileDirEntry(null,null,0);
		fde.crc = entry.CRC();

		System.out.println();
		System.out.println(entry.name());
		System.out.println(images.contains(fde));

	}

	private void initializeInternal() {

		try {
			reader.register(new ContentChangeWatcher() {

				@Override
				public void onEvent(FileEvent type, Object id)throws IOException {
					switch (type) {
					case Create:
						FileImageEntry entryNew = reader.getEntryByIdentifier(id);
						images.add(FileDirEntry.as(entryNew));
						break;
					case Delete:
						FileImageEntry entryDel = reader.getEntryByIdentifier(id);
						FileDirEntry fde = CopyService.this.findByName(entryDel.getIdentifier());
						images.remove(fde);
						break;
					default:
					}

				}

			});

			reader.initialize();
			readStructure();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure() throws Exception {
		List<ImageEntry<?>> toAdd = new ArrayList<ImageEntry<?>>();
		reader.read(toAdd);

		for (ImageEntry<?> next : toAdd) {
			images.add(FileDirEntry.as((FileImageEntry)next));
		}
	}

	public void test() {
		for (FileDirEntry fileDirEntry : images) {
			System.out.println(fileDirEntry);
		}
	}


//	public void onContainerContentChange() {
//	}

}
