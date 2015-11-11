package org.delafer.xmlbench.compressors;

import java.util.Map;
import java.util.TreeMap;

import org.delafer.xmlbench.config.Config;

public class CompressionFactory {

	private static TreeMap<Integer, ICompressor> compressors;

	static {
		compressors = new TreeMap<Integer, ICompressor>();

		compressors.put(LZFCompressorSafe.UID, new LZFCompressorSafe());
		compressors.put(LZFCompressorUnsafe.UID, new LZFCompressorUnsafe());

		compressors.put(JavaZipFast.UID, new JavaZipFast());
		compressors.put(JavaZipNormal.UID, new JavaZipNormal());
		compressors.put(JavaJZLib.UID, new JavaJZLib());

		compressors.put(LZ4CompressorSafe.UID, new LZ4CompressorSafe());
		compressors.put(LZ4CompressorUnsafe.UID, new LZ4CompressorUnsafe());

		compressors.put(XZCompressor.UID, new XZCompressor());

		compressors.put(SnappyIQ80.UID, new SnappyIQ80());
		compressors.put(SnappyJS.UID, new SnappyJS());

		compressors.put(SnappyXerial.UID, new SnappyXerial());
//		compressors.put(SnappyIndeed.UID, new SnappyIndeed());

		compressors.put(LZOCompressor.UID, new LZOCompressor());

	}

	public static Map<Integer, ICompressor> getCompressors() {
		return compressors;
	}

	public static ICompressor getSelectedCompressor() {
		return compressors.get(Config.instance().defaultCompressor);
	}

}
