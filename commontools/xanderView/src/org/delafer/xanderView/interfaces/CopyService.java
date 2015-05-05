package org.delafer.xanderView.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.ListIterator;

import net.j7.commons.base.Equals;
import net.j7.commons.collections.SortedLinkedList;

import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;
import org.delafer.xanderView.sound.SoundBeep;

public class CopyService {

	SortedLinkedList<ImageEntry<?>> images;
	ListIterator<ImageEntry<?>> iterator;
	File pathFile;
//	String pathContainer;
	IAbstractReader reader;
	ImageEntry<?> current = null;


	public int size() {
		return images.size();
	}

	public void init() {
		String location = ApplConfiguration.instance().get(ApplConfiguration.CFG_COPY_DIR);
		initializeByPath(location);

	}

	private void initializeByPath(String locationArg) {
		this.pathFile = new File(locationArg);
		this.reader = getReader(pathFile);
		Comparator<ImageEntry<?>> comparator = this.reader.getComparator();
		this.images = new SortedLinkedList<ImageEntry<?>>(comparator);
		initializeInternal();

	}

	private IAbstractReader getReader(File aFile) {
		boolean isFile = aFile.isFile();

		if (isFile && aFile.getName().toLowerCase().endsWith(".zip")) {
			return new SevenZipReader(aFile);
		}

		return new FileReader(aFile);
	}

	private void initializeInternal() {

		try {
			reader.register(new ContentChangeWatcher() {

				@Override
				public void onEvent(FileEvent type, Object id)throws IOException {
					switch (type) {
					case Create:
						ImageEntry<?> entryNew = reader.getEntryByIdentifier(id);
						images.add(entryNew);
						break;
					case Delete:
						ImageEntry<?> entryDel = reader.getEntryByIdentifier(id);
						images.remove(entryDel);
						if (current != null) {
							if (Equals.equal(current.getIdentifier(), entryDel.getIdentifier())) {
								current = null;
							}
						}
						break;
					default:
					}

				}

			});

			reader.initialize();
			readStructure(images);
			iterator = images.listIterator();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure(SortedLinkedList<ImageEntry<?>> list) throws Exception {
		reader.read(list);
	}



	public void onContainerContentChange() {

	}



}
