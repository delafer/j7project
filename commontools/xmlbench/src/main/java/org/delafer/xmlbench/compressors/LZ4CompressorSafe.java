package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Factory;

public class LZ4CompressorSafe implements ICompressor {

	public static final int UID = 4;


	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "LZ4 Compressor (Java Safe)";
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
		LZ4BlockInputStream iis = new LZ4BlockInputStream(is, LZ4Factory.safeInstance().fastDecompressor());
		return iis;
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		LZ4BlockOutputStream deflaterStream = new LZ4BlockOutputStream(inData, ( 1 << 16),  LZ4Factory.safeInstance().fastCompressor());
		return deflaterStream;

	}

}
