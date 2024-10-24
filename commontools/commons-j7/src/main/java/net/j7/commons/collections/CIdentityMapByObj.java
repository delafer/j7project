package net.j7.commons.collections;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings({"unchecked"})
public final class CIdentityMapByObj<K, E> {

	private transient E[] table;
	private transient K[] keys;
	private transient boolean sw;
	private transient final static int N = -1;

    private transient final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private transient final ReentrantReadWriteLock.ReadLock r = rwl.readLock();
    private transient final ReentrantReadWriteLock.WriteLock w = rwl.writeLock();

    /**
     * The number of key-value mappings contained in this map.
     */
	private final transient int size;

    public CIdentityMapByObj() {
    	this(16);
    }

    public CIdentityMapByObj(int value) {
    	size = value << 1;
    	table = (E[]) new Object[size];
    	keys = 	(K[]) new Object[size];
    }

    /**
     * Default hash for object
     */
    final static long hash (Object obj)
    {
    	return obj!=null ? obj.hashCode() : 0;
    }

   /**
    * Delafer2000 rehashing algorithm.
    *
    * @param h the h
    *
    * @return the long
    */
   final static long rehash(long h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        long hash = h ^ (h >>> 7) ^ (h >>> 4);
        hash += ~(hash << 15);
        hash ^= (hash >>> 10);
        hash += (hash << 3);
        hash ^= (hash >>> 6);
        hash += ~(hash << 11);
        hash ^= (hash >>> 16);
        return hash;
    }

    /**
     * Returns index for hash code h.
     */
   final static int indexFor(long h, int old, int size) {
    	h  = h % size;
    	int ret = (int) ( h < 0  ? -h  : h);

    	if (ret==old) indexFor(h+1, old, size);

    	return ret;
    }

    /**
     * Put.
     *
     * @param key the key
     * @param value the value
     */
    public final void put(K key, E value) {

		try {
			w.lock();

			long hash = hash(key);

			int i = indexFor(hash, N, size);
			if (null != keys[i] && keys[i] != key) {
				int i2 = indexFor(rehash(hash), i, size);
				if (null != keys[i2] && keys[i2] != key) {
					sw = sw == false;
					if (sw) i = i2;
				}
			}
			keys[i] = key;
			table[i] = value;

		} finally {
			w.unlock();
		}

	}


	/**
	 * Gets the.
	 * @param key the key
	 * @return the object
	 */
	public final E get(K key) {

		try {
			r.lock();

			long hash = hash(key);
			int i = indexFor(hash, N, size);

			E obj = getValue(key, i);
			if (obj == null) {
				i = indexFor(rehash(hash), i, size);
				obj = getValue(key, i);
			}
			return obj;

		} finally {
			r.unlock();
		}

	}

	private E getValue(K key, int i) {
		if (null!=keys[i] && keys[i] == key) return table[i];
        return null;
	}

	public static int getN() {
		return N;
	}

	public synchronized void stat() {
		for (K x : keys)
		if (null!=x) System.out.println("key="+x);
	}


}

