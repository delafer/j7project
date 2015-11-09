package org.delafer.xmlbench.compressors;

import java.util.Map;
import java.util.TreeMap;

import org.delafer.xmlbench.config.Config;

public class CompressionFactory {

	private static TreeMap<Integer, ICompressor> compressors;

	static {
		compressors = new TreeMap<Integer, ICompressor>();

		compressors.put(LZFCompressor.UID, new LZFCompressor());

		compressors.put(JavaZipFast.UID, new JavaZipFast());
		compressors.put(JavaZipNormal.UID, new JavaZipNormal());
		compressors.put(JavaJZLib.UID, new JavaJZLib());

		compressors.put(LZ4Compressor.UID, new LZ4Compressor());

		compressors.put(XZCompressor.UID, new XZCompressor());

		compressors.put(SnappyIQ80.UID, new SnappyIQ80());
		compressors.put(SnappyJS.UID, new SnappyJS());

		compressors.put(XerialSnappy.UID, new XerialSnappy());

	}

	public static Map<Integer, ICompressor> getCompressors() {
		return compressors;
	}

	public static ICompressor getSelectedCompressor() {
		return compressors.get(Config.instance().defaultCompressor);
	}

}
