package org.delafer.xmlbench.stats;

import java.util.concurrent.atomic.AtomicLong;

import org.delafer.xmlbench.resources.Convertors;
import org.delafer.xmlbench.test.FindIt;
import org.delafer.xmlbench.test.FindIt.ThreadRatio;

public final class Statistic {
	
	public static transient AtomicLong pkdData = new AtomicLong(0l);
	public static transient AtomicLong unpData = new AtomicLong(0l);
	
	
	public static transient AtomicLong reqComp = new AtomicLong(0l);
	public static transient AtomicLong reqDecom = new AtomicLong(0l);
	
	
	public static transient float cacheFactor = 1.0f;
	public static transient boolean cacheOn = false; 
	
	public static void setCacheOn(boolean cacheOn) {
		Statistic.cacheOn = cacheOn;
	}

	public final static void incReqComp() {
		reqComp.incrementAndGet();
	}
	
	public final static void incReqDecomp() {
		reqDecom.incrementAndGet();
	}

	
	public final static void setCacheEfficiency(int rate) {
		ThreadRatio ratio = FindIt.findRatio(rate);
		float hits = (float)ratio.packers;
		float misses = (float)ratio.depackers;
		float total = hits + misses;
		
		cacheFactor = misses > 0f ? (1f * total )/ misses : 75f;
	}
	
	
	public final static void incPacked(int toAdd) {
		pkdData.addAndGet(toAdd);
	}
	
	public final static void incUnpacked(int toAdd) {
		unpData.addAndGet(toAdd);
	}
	
	
	public static Info getRequestAsInfo() {
		return new Info(getTotalRequests());
	}
	
	public static Info getCompressedBytesAsInfo() {
		return new Info(pkdData.get());
	}
	
	public static Info getDecompressedBytesAsInfo() {
		return new Info(unpData.get());
	}
	
	public static String getCompressedBytes() {
		return Convertors.autoSize(pkdData.get());
	}
	
	public static String getDecompressedBytes() {
		return Convertors.autoSize(correctCache(unpData.get()));
	}
	
	public static String getTotalBytes() {
		return Convertors.autoSize(pkdData.get() + correctCache(unpData.get()));
	}
	
	private static final long correctCache(long bytes) {
		return cacheOn ? (long)(cacheFactor * (float)bytes) : bytes;
	}
	
	public static void reset() {
		pkdData.set(0l);
		unpData.set(0l);
		reqComp.set(0l);
		reqDecom.set(0l);
		
	}
	
	public static String getRequests() {
		StringBuilder sb = new StringBuilder(48);
		long total = getTotalRequests();
		sb.append(total);
		sb.append(" / ");
		sb.append(getCompressionRequests());
		sb.append(" / ");
		sb.append(getDecompressionRequests());
		return sb.toString();
	}
	
	public static long getTotalRequests() {
		return getCompressionRequests() + getDecompressionRequests();
	}
	
	public static long getCompressionRequests() {
		return reqComp.get();
	}
	
	public static long getDecompressionRequests() {
		return correctCache(reqDecom.get());
	}
	
	public static class Info {
		
		public long bytes;
		public long time;
		
		public Info(long bytes) {
			this.bytes = bytes;
			this.time = System.currentTimeMillis();
		}
		
		
		
	}
	
	
}
