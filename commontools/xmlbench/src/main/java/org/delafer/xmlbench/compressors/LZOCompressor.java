package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import org.anarres.lzo.LzoAlgorithm;
import org.anarres.lzo.commons.LzoCompressorInputStream;
import org.anarres.lzo.commons.LzoCompressorOutputStream;

public class LZOCompressor implements ICompressor {

	private static final LzoAlgorithm ALGORITM = LzoAlgorithm.LZO1X;
	public static final int UID = 8;


	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "LZO Compressor (LZO1X)";
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
		LzoCompressorInputStream iis = new LzoCompressorInputStream(is, ALGORITM);
		return iis;
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		LzoCompressorOutputStream bcs = new LzoCompressorOutputStream(inData, ALGORITM);
		return bcs;

	}

}
