package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jzlib.JZlib;

public class JavaJZLib implements ICompressor {
	
	public static final int UID = 3;

	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "JZlib is a implementation of zlib/zip in pure Java (fast mode)";
	}
	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getDescription()
	 */
	public String getDescription() {
		return getName();
	}
	
	public static int BUFFER_SIZE = 2048;
	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getURL()
	 */
	public String getURL() {
		return "";
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#decompressData(java.io.InputStream)
	 */
	public InputStream decompressor(InputStream is)throws Exception  {
		return new com.jcraft.jzlib.InflaterInputStream(is, new com.jcraft.jzlib.Inflater(), BUFFER_SIZE);
	}
	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData) throws Exception {
		final com.jcraft.jzlib.Deflater deflt = new com.jcraft.jzlib.Deflater(JZlib.Z_BEST_SPEED);
		return new com.jcraft.jzlib.DeflaterOutputStream(inData, deflt, BUFFER_SIZE);

	}
	
}
