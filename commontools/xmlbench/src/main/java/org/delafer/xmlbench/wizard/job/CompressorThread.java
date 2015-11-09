/**
 * 
 */
package org.delafer.xmlbench.wizard.job;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.delafer.xmlbench.compressors.CompressionFactory;
import org.delafer.xmlbench.compressors.DummyCompressionOutputStream;
import org.delafer.xmlbench.compressors.ICompressor;
import org.delafer.xmlbench.config.Config;
import org.delafer.xmlbench.resources.FilesFactory;
import org.delafer.xmlbench.resources.IFileAbstract;
import org.delafer.xmlbench.resources.SourceFactory;
import org.delafer.xmlbench.stats.Statistic;
import org.delafer.xmlbench.test.FindIt;

/**
 * @author Alexander Tavrovsky
 *
 */
public final class CompressorThread extends Thread {
	
	
	private static final int BUF_SIZE = 2048;
    
	private volatile boolean paused = false; /** flag stopped. */
	private volatile boolean stopped = false; /** flag stopped. */
    
//	public static volatile transient int curr = 0;
	public static volatile transient int currD = 0;
	
	private TaskChain chain;
	
	public void decompressIt() throws Exception {
		
		IFileAbstract source = SourceFactory.getCompressedFile();
		
		InputStream srcIS = source.openInputStream();
		
		ICompressor cmp= CompressionFactory.getSelectedCompressor();
		
		InputStream decomprStream =   cmp.decompressor(srcIS);
		
		byte[] buf = new byte[BUF_SIZE];
		
		int read;
		while ((read = decomprStream.read(buf))>0) {
			Statistic.incUnpacked(read);
			Thread.yield();
		}
		decomprStream.close();
		Statistic.incReqDecomp();
		
		
	}
	
	public void compressIt() throws Exception {
		
		IFileAbstract source = SourceFactory.getUncompressedFile();
		
		InputStream srcIS = source.openInputStream();
		
		DummyCompressionOutputStream destOS= new DummyCompressionOutputStream();
		ICompressor cmp= CompressionFactory.getSelectedCompressor();
		
		OutputStream comprStream =   cmp.compressor(destOS);
		
		byte[] buf = new byte[BUF_SIZE];
		
		int read;
		while ((read = srcIS.read(buf))>0) {
			comprStream.write(buf, 0, read);
			Statistic.incPacked(read);
			Thread.yield();
		}
		comprStream.flush();Thread.yield();
		comprStream.close();Thread.yield();
		Statistic.incReqComp();
		
		
	}
	
	
	public CompressorThread() {
		super();
		this.setDaemon(true);
		setPriority(Thread.NORM_PRIORITY-1);
		paused = true;
		
		FindIt.ThreadRatio ratio = FindIt.findRatio(Config.instance().threadsRatio);
		chain = new TaskChain(ratio.depackers, ratio.packers);
		
		start();
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	@Override
	public void run() {
		try {
		
		while (!stopped) {
			
			synchronized (this) {
				while (paused) wait();
			}
			
			byte tType = chain.nextTaskType();
			if (tType==TaskChain.TYPE_PACK)
				compressIt();
			else
			if (tType==TaskChain.TYPE_DEPACK)				
				decompressIt();
            
            Thread.yield();
            Thread.sleep(1);
            Thread.yield();
        
			
		}
		
		
		} catch (Exception e) { /*ignore all exceptions*/
			//e.printStackTrace();
			//System.out.println("Interrupted");
		}
		//System.out.println("done");
	}
	
	public synchronized void stopJob() {
		if (stopped || isInterrupted()) return ;
		paused = false;
		stopped = true; 
		notify();
//		try {
//			sleep(1500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		interrupt();
		
	}
	
	public synchronized void pauseJob() {
		if (paused || stopped || isInterrupted()) return ;
		paused = true;
		System.out.println("Pause");
		notify();
	}
	
	public synchronized void runJob() {
		if (!paused || stopped || isInterrupted()) return ;
		///System.out.println("Run");
		paused = false;
		notify();
	}
	
	public synchronized void updateJob() {
		notify();
	}

	

	@Override
	public void interrupt() {
		super.interrupt();
	}

	@Override
	public boolean isInterrupted() {
		return super.isInterrupted();
	}


}
