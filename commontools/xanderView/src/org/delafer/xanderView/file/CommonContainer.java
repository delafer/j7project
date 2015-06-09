package org.delafer.xanderView.file;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.ListIterator;

import net.j7.commons.base.Equals;
import net.j7.commons.collections.SortedLinkedList;
import net.j7.commons.io.FileUtils;

import org.delafer.xanderView.file.entry.FileDirEntry;
import org.delafer.xanderView.file.entry.ImageEntry;
import org.delafer.xanderView.file.entry.ImageEntry.ImageType;
import org.delafer.xanderView.file.readers.FileReader;
import org.delafer.xanderView.file.readers.SevenZipReader;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.IAbstractReader;
import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;
import org.delafer.xanderView.sound.SoundBeep;

public class CommonContainer {

	SortedLinkedList<ImageEntry<?>> images;
	ListIterator<ImageEntry<?>> iterator;
	File pathFile;
//	String pathContainer;
	IAbstractReader reader;
	ImageEntry<?> current = null;
	int direction = 0;
	boolean loop;


	public int size() {
		return images.size();
	}

	public CommonContainer(String locationArg) {
		super();
		this.pathFile = new File(locationArg);
		this.reader = getReader(pathFile);
//		this.pathContainer = reader.getContainerPath();
		Comparator<ImageEntry<?>> comparator = this.reader.getComparator();
		this.images = new SortedLinkedList<ImageEntry<?>>(comparator);
		loop = ApplConfiguration.instance().getBoolean(ApplConfiguration.LOOP_CURRENT_SOURCE);
		initialize();

		Object entry = reader.getSingleEntry();
		if (entry == null) {
			if (images.size()>0) {
				getNext();
			}
		} else {
			try {
				current = reader.getEntryByIdentifier(entry);
				updateIterator();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private IAbstractReader getReader(File aFile) {
		boolean isFile = aFile.isFile();

		if (isFile) {
			String name = aFile.getName().toLowerCase();
			if (name.endsWith(".zip") || name.endsWith(".rar") || name.endsWith(".7z") || name.endsWith(".jzip")) {
				return new SevenZipReader(aFile);
			}

		}

		return new FileReader(aFile);
	}

	public void initialize() {

		try {
			reader.register(new ContentChangeWatcher() {

				@Override
				public void onEvent(FileEvent type, Object id)throws IOException {
					switch (type) {
					case Create:
						String fileName = id.toString();
						File aFile = new File(fileName);
						if (aFile.isDirectory()) return ;

						ImageType imgType = ImageEntry.getType(fileName);
						if (ImageType.UNKNOWN.equals(imgType)) return ;

						ImageEntry<?> entryNew = reader.getEntryByIdentifier(id);
						images.add(entryNew);
						updateIterator();
						break;
					case Delete:
						ImageEntry<?> entryDel = reader.getEntryByIdentifier(id);
						images.remove(entryDel);
						if (current != null) {
							if (Equals.equal(current.getIdentifier(), entryDel.getIdentifier())) {
								current = null;
							}
						}
						updateIterator();
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

	public int currentIndex() {
		int idx = this.getNextIndex();
		if (direction == -1) idx++;
		return idx;
	}

	public ImageEntry<?> getNext() {
		if (direction == -1) {
			if (iterator.hasNext()) {
				iterator.next();
				direction = 0;
			}
		}
		if (iterator.hasNext()) {
			current = iterator.next();
			direction = 1;
		} else {
			if (loop) {
				gotoFirst();
			}
			//current = null;
		}

		return current;
	}

	private void gotoLast() {
		while (iterator.hasNext()) {
			current = iterator.next();
		}
		beep();
		direction = 1;
	}

	private void beep() {
		SoundBeep.beep();

	}

	private void gotoFirst() {
		while (iterator.hasPrevious()) {
			current = iterator.previous();
		}
		beep();
		direction = -1;
	}

	public int getNextIndex() {
		return iterator.nextIndex();
	}

	public ImageEntry<?> getPrevious10() {
		if (size()>= 10) for (int i = 8; i >= 0; i--) getPrevious();

		return getPrevious();
	}

	public ImageEntry<?> getNext10() {
		if (size()>= 10) for (int i = 8; i >= 0; i--) getNext();

		return getNext();
	}

	public ImageEntry<?> getPrevious() {
		if (direction == 1) {
			if (iterator.hasPrevious()) {
				iterator.previous();
				direction = 0;
			}
		}
		if (iterator.hasPrevious()) {
			current = iterator.previous();
			direction = -1;
		} else {
			if (loop) {
				gotoLast();
			}
			//current = null;
		}

		return current;
	}

	public int getPreviousIndex() {
		return iterator.previousIndex();
	}

	public void onContainerContentChange() {

	}

	private void updateIterator() {
		iterator = current != null ? images.listIterator(current) : images.listIterator();
		if (iterator.hasNext()) {
			iterator.next();
			direction = 1;
		} else {
			direction = 0;
		}
	}


	public ImageEntry<?> getCurrent() {
		return current;
	}

	public String getLocation() {

		return pathFile != null ? pathFile.getParent() : ".";
	}

}
