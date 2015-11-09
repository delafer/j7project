package org.delafer.xmlbench.compressors;

import java.io.InputStream;
import java.io.OutputStream;

import org.iq80.snappy.SnappyInputStream;
import org.iq80.snappy.SnappyOutputStream;

import com.ning.compress.lzf.LZFInputStream;
import com.ning.compress.lzf.LZFOutputStream;

public class LZFCompressor implements ICompressor {
	
	public static final int UID = 12;

	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#getName()
	 */
	public String getName() {
		return "LZF (Lempel-Ziv Fast) Compressor by Marc A. Lehmann";
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
		
		LZFInputStream iis = new LZFInputStream(is);
		return iis;
	}
	
	/* (non-Javadoc)
	 * @see org.delafer.xmlbench.compressors.ICompressor#compressData(java.io.OutputStream)
	 */

	public OutputStream compressor(OutputStream inData)throws Exception {
		LZFOutputStream deflaterStream = new LZFOutputStream(inData);
		return deflaterStream;

	}
	
}
