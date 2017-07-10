package org.delafer.xanderView.hash;

import java.nio.ByteBuffer;

import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;

public class Hasher {

	private XXHash64 hash64;
	public static final int HSIZE = 4096;


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
	static volatile int x = 0;
	public long calc(ByteBuffer buf, long seed) {
		buf.clear();
		int size = (int)seed;
		if (size > buf.remaining()) size = buf.remaining();
		byte[] aa = new byte[size];
		buf.get(aa);
		buf.clear();
//		ByteBufferFileUtils.writeToFile("A:\\"+(++x)+".bin", ByteBuffer.wrap(aa));
		return calc(aa, seed);
	}


	public long calc(byte[] buf, long seed) {
		return hash64.hash(buf, 0, min(buf.length, HSIZE), seed);
	}


	private int min(int a1, int a2) {
		return a1 < a2 ? a1 : a2;
	}

}
