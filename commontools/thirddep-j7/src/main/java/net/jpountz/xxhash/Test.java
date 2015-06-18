package net.jpountz.xxhash;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	static long[] std = new long[] {Byte.MIN_VALUE, Byte.MAX_VALUE, Short.MIN_VALUE, Short.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE};

	public static void main(String[] args) {
		int value = 374761393;

		System.out.println("2) "+Long.toString(value, 2));
		System.out.println("8) "+Long.toString(value, 8));
		System.out.println("10) "+Long.toString(value, 10));
		System.out.println("16) "+Long.toString(value, 16));

		for (long ext : std) {
			if (ext == value) System.out.println("Std: "+ext);
		}

		long l = 1;
		for (int i = 0; i < 32; i++) {
			l *= 2;
			if (l == value) System.out.println("xx["+i+"] "+i);
		}

	}

}
