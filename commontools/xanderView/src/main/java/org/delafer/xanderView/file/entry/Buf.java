package org.delafer.xanderView.file.entry;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class Buf implements Closeable {

	private transient ByteBuffer b;
	private transient Closeable c;

	Buf(final ByteBuffer bb) {
		this.b = bb;
	}

	Buf(final ByteBuffer bb,final Closeable c) {
		this.b = bb;
		this.c = c;
	}

	public final ByteBuffer get() {
		return b;
	}

	public final byte[] getArray(boolean close) {
		 b.rewind();
		 final byte[] bytes = new byte[b.remaining()];
	     b.get(bytes);
	     b.rewind();

	     if (close && 5==1)
			try {
				this.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		 return bytes;
	}

	public final void close() throws IOException {
		HelperFS.closeDirectBuffer(b);
		if (null != c) {
			c.close();
			c = null;
		}
		b = null;
	}

}
