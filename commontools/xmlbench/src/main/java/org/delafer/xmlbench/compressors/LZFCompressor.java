package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.LZFChunk;
import com.ning.compress.lzf.LZFInputStream;
import com.ning.compress.lzf.LZFOutputStream;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import com.ning.compress.lzf.util.ChunkEncoderFactory;

public class LZFCompressor implements ICompressor {

	public static final int UID = 12;


	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "LZF (Java Safe) Compressor by Marc A. Lehmann";
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getDescription()
	 */
	public String getDescription() {
		return getName();
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getURL()
	 */
	public String getURL() {
		return "";
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#decompressData(java.io.InputStream)
	 */
	public InputStream decompressor(InputStream is) throws Exception{

		LZFInputStream iis = new LZFInputStream(ChunkDecoderFactory.safeInstance(), is, BufferRecycler.instance(), false);
		return iis;
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		LZFOutputStream deflaterStream = new LZFOutputStream(ChunkEncoderFactory.safeInstance(LZFChunk.MAX_CHUNK_LEN), inData);
		return deflaterStream;

	}

}
