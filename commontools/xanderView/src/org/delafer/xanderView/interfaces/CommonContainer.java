package org.delafer.xanderView.interfaces;

import java.util.Comparator;
import java.util.ListIterator;

import net.j7.commons.collections.SortedLinkedList;
import net.j7.commons.strings.BogusComparator;

public class CommonContainer {

	SortedLinkedList<IImageEntry> images;
	ListIterator<IImageEntry> iterator;
	String location;

	public CommonContainer(String location, Comparator<IImageEntry> comparator) {
		super();
		this.location = location;
		images = new SortedLinkedList<IImageEntry>(comparator);
		initialize();
	}

	public void initialize() {

		try {
			readStructure(images);
			iterator = images.listIterator();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure(SortedLinkedList<IImageEntry> list) throws Exception {
	}


	public IImageEntry getNext() {
		return iterator.hasNext() ? iterator.next() : null;
	}

	public int getNextIndex() {
		return iterator.nextIndex();
	}

	public IImageEntry getPrevious() {
		return iterator.hasPrevious() ? iterator.previous() : null;
	}

	public int getPreviousIndex() {
		return iterator.previousIndex();
	}

	public void onContainerContentChange() {

	}


//	public static void main(String[] args) {
//		SortedLinkedList<String> ts = new SortedLinkedList<String>(BogusComparator.instance());
//
//		ts.add("abcdd");
//		ts.add("aaaa4");
//		ts.add("aaaa3");
//		ts.add("aaaa1");
//		ts.add("abc");
//
//		ListIterator<String>a = ts.listIterator("aaaa3");
//		a.next();
//		System.out.println(a.next());
//		System.out.println("xxx");
//		for (String string : ts) {
//			System.out.println(string);
//		}
//	}
}
