package org.delafer.xanderView.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

import net.j7.commons.base.Equals;
import net.j7.commons.collections.SortedLinkedList;

import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;

public class CommonContainer {

	SortedLinkedList<ImageEntry<?>> images;
	ListIterator<ImageEntry<?>> iterator;
	String location;
	IAbstractReader reader;
	ImageEntry<?> current = null;
	int direction = 0;

	public CommonContainer(String location, Comparator<ImageEntry<?>> comparator) {
		super();
		this.location = location;
		images = new SortedLinkedList<ImageEntry<?>>(comparator);
		this.reader = getReader(location);
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure(SortedLinkedList<ImageEntry<?>> list) throws Exception {
		reader.read(location, list);
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
			//current = null;
		}

		return current;
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
		System.out.println(1);
		System.out.println(iterator.nextIndex()+" <> "+iterator.previousIndex());
		System.out.println("Current: "+current);
		iterator = current != null ? images.listIterator(current) : images.listIterator();
		iterator.next();
		System.out.println(iterator.nextIndex()+" <> "+iterator.previousIndex());
		direction = 1;
	}

	public interface ContentChangeWatcher {

		void onEvent(FileEvent type, Object identifier) throws IOException;

	}

	public static void main(String[] args) {
//		CommonContainer cc = new CommonContainer("d:\\test3.zip", null);
//		show(cc.getNext());
//		show(cc.getNext());
////		show(cc.getPrevious());
//		show(cc.getNext());
//		show(cc.getPrevious());
//		show(cc.getPrevious());
//		show(cc.getPrevious());

		LinkedList<Integer> l = new LinkedList<Integer>();
		for (int i = 0; i < 10; i++) {
			l.add(i);
		}
		ListIterator<Integer> li = l.listIterator();

		System.out.println(li.next());
		System.out.println(li.next());
		System.out.println(li.previous());
		System.out.println(li.previous());
		System.out.println(li.next());
		System.out.println(li.next());

	}

	private static void show(ImageEntry<?> next) {
		System.out.println(next.name+" > "+next.getIdentifier());

	}

	public ImageEntry<?> getCurrent() {
		return current;
	}

}
