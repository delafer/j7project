package org.delafer.xanderView.tests;

import net.j7.commons.utils.ByteUtils;

public class Testttt {
	public static void main(String[] args) {
	byte[] a = ByteUtils.intToByteArray(193221);
	for (int i = 0; i < a.length; i++) {
		System.out.println(String.format("%02x", a[i]));
	}
	}
}
