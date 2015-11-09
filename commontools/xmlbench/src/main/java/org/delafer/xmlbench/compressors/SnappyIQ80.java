package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import org.iq80.snappy.SnappyInputStream;
import org.iq80.snappy.SnappyOutputStream;

public class SnappyIQ80 implements ICompressor {
	
	public static final int UID = 10;

	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "Snappy (Zippy) by Google Inc. (IQ80 Java-Port)";
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
		
		SnappyInputStream iis = new SnappyInputStream(is);
		return iis;
	}
	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		SnappyOutputStream deflaterStream = new SnappyOutputStream(inData);
		return deflaterStream;

	}
	
}
