package org.delafer.xanderView.interfaces;

import java.util.ListIterator;

import net.j7.commons.collections.SortedLinkedList;
import net.j7.commons.strings.BogusComparator;

public class CommonContainer {

	public CommonContainer(String location) {
		super();
	}

	public static void main(String[] args) {
		SortedLinkedList<String> ts = new SortedLinkedList<String>(BogusComparator.instance());

		ts.add("abcdd");
		ts.add("aaaa4");
		ts.add("aaaa3");
		ts.add("aaaa1");
		ts.add("abc");

		ListIterator<String>a = ts.listIterator("aaaa3");
		a.next();
		System.out.println(a.next());

//		for (String string : ts) {
//			System.out.println(string);
//		}
	}
}
