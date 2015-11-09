package org.delafer.xmlbench.config;

import org.delafer.xmlbench.compressors.LZFCompressor;


public class Config {
	
    /** demand holder idiom Lazy-loaded Singleton */
    private static class Holder {
        private final static Config	INSTANCE	= new Config();
    }

    /**
     * Gets the single instance of LockCore.
     * @return single instance of LockCore
     */
    public static Config instance() {
        return Holder.INSTANCE;
    }
    
    
    public int threads = 8;
    public int threadsRatio = 25;
    public int cacheRatio = 50;
    public int defaultCompressor = LZFCompressor.UID;
    public boolean cacheOn = false; 
    public int cacheEntries = 500;
    
    public int selectedFile = 2;
    
    
    public long sizeCompressed = 0l;
    public double ratioCompresion = 100d;
    public long sizedSaved = 0l;
	
    
    public static final String PATH_SEPARATOR = System.getProperty("file.separator");
    
}
