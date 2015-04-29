package org.delafer.xanderView.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

import net.j7.commons.base.Equals;
import net.j7.commons.collections.SortedLinkedList;
import net.j7.commons.strings.Args;

import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;
import org.delafer.xanderView.sound.SoundBeep;

public class CommonContainer {

	SortedLinkedList<ImageEntry<?>> images;
	ListIterator<ImageEntry<?>> iterator;
	String location;
	IAbstractReader reader;
	ImageEntry<?> current = null;
	int direction = 0;
	boolean loop;


	public int size() {
		return images.size();
	}

	public CommonContainer(String location) {
		super();
		this.location = location;
		this.reader = getReader(location);
		Comparator<ImageEntry<?>> comparator = this.reader.getComparator();
		this.images = new SortedLinkedList<ImageEntry<?>>(comparator);
		loop = ApplConfiguration.instance().getBoolean(ApplConfiguration.LOOP_CURRENT_SOURCE);
		initialize();
	}

	private IAbstractReader getReader(String location) {
		File aFile = new File(location);
		boolean isFile = aFile.isFile();

		if (isFile && location.toLowerCase().endsWith(".zip")) {
			return new SevenZipReader();
		}

		return new FileReader();
	}

	public void initialize() {

		try {
			reader.register(new ContentChangeWatcher() {

				@Override
				public void onEvent(FileEvent type, Object id)throws IOException {
					switch (type) {
					case Create:
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
			reader.initialize(location);
			readStructure(images);
			iterator = images.listIterator();
			if (images.size()>0) {
				getNext();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure(SortedLinkedList<ImageEntry<?>> list) throws Exception {
		reader.read(location, list);
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
//		System.out.println(1);
//		System.out.println(iterator.nextIndex()+" <> "+iterator.previousIndex());
//		System.out.println("Current: "+current);
		iterator = current != null ? images.listIterator(current) : images.listIterator();
		if (iterator.hasNext()) {
			iterator.next();
			direction = 1;
		} else {
			direction = 0;
		}
//		System.out.println(iterator.nextIndex()+" <> "+iterator.previousIndex());

	}

	public interface ContentChangeWatcher {

		void onEvent(FileEvent type, Object identifier) throws IOException;

	}


	public ImageEntry<?> getCurrent() {
		return current;
	}

}
