package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import com.indeed.util.compress.BlockCompressorStream;
import com.indeed.util.compress.BlockDecompressorStream;
import com.indeed.util.compress.snappy.SnappyCompressor;
import com.indeed.util.compress.snappy.SnappyDecompressor;

public class IndeedSnappy implements ICompressor {

	public static final int UID = 16;


	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "Snappy (Indeed Java-Port)";
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
		BlockDecompressorStream iis = new BlockDecompressorStream(is, new SnappyDecompressor());
		return iis;
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		BlockCompressorStream bcs = new BlockCompressorStream(inData, new SnappyCompressor());
		return bcs;

	}

}
