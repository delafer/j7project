package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import de.jarnbjo.jsnappy.SnzInputStream;
import de.jarnbjo.jsnappy.SnzOutputStream;

public class SnappyJS implements ICompressor {

	public static final int UID = 11;


	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "Snappy Jarnbjo (Google codec)";
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

		SnzInputStream iis = new SnzInputStream(is);
		return iis;
	}

	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		SnzOutputStream deflaterStream = new SnzOutputStream(inData);
		return deflaterStream;

	}

}
