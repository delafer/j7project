package org.delafer.xanderView.file;

import java.util.Arrays;

import net.j7.commons.types.BitNumber;
import net.j7.commons.utils.RandomUtil;

public class Encryptor {

	public Encryptor() {
		// TODO Auto-generated constructor stub
	}


	public static byte[] encrypt(byte[] in) {
		return in;
	}

	public static byte[] decrypt(byte[] in) {
		return in;
	}


	public static boolean equals(byte[] a1, byte[] a2) {
		return Arrays.equals(a1, a2);
	}


	public static byte shift1(byte a, int to) {
		to = to % 8;
		return (byte)((a >>> to) | (a << (8-to)));
	}

	public static byte shift2(byte a, int to) {
		to = to % 8;
		return (byte)((a << to) | (a >>> (8-to)));
	}

	public static String show(byte a) {
		 return String.format("%8s", Integer.toBinaryString(a & 0xFF)).replace(' ', '0');
	}

	public static void main(String[] args) {

		byte a = (byte)64+32+4+2;
		System.out.println(show(a));

		for (int i = 1; i < 9; i++) {
			byte b=  shift2(a, i);
			System.out.println(show(b));

			byte b2=  shift2(a, i+8);
			System.out.println(show(b2));
		}


		System.exit(0);
		byte[] arr = RandomUtil.generateRandomString(10).getBytes();

		byte[] enc = encrypt(arr);
		byte[] dec = decrypt(enc);


		if (!equals(arr, enc) && equals(arr, dec))
			System.out.println("PASSED");
		else
			System.out.println("NOT PASSED");
	}

}
