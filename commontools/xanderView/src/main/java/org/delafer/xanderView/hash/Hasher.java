package org.delafer.xanderView.hash;

import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;

public class Hasher {

	private XXHash64 hash64;
	public static final int SIZE = 2048;


	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient Hasher INST = new Hasher();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public final static Hasher hash() {
		return Holder.INST;
	}


	private Hasher() {
		XXHashFactory hash = XXHashFactory.fastestInstance();
		hash64 = hash.hash64();
	}

	public long calc(byte[] buf, long seed) {
		return hash64.hash(buf, 0, min(buf.length, SIZE), seed);
	}


	private int min(int a1, int a2) {
		return a1 < a2 ? a1 : a2;
	}

}
