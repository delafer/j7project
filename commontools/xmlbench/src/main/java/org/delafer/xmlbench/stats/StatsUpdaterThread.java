package org.delafer.xmlbench.stats;

import java.util.Timer;
import java.util.TimerTask;

import org.delafer.xmlbench.TimeUtils;
import org.delafer.xmlbench.config.Config;
import org.delafer.xmlbench.resources.Convertors;
import org.delafer.xmlbench.wizard.pages.ProgressPage;
import org.eclipse.swt.widgets.Display;

public class StatsUpdaterThread extends Timer {

	private UpdaterTask task;

	public StatsUpdaterThread(ProgressPage page) {
		super(true);
		task = new UpdaterTask(page);
		this.schedule(task, (long)(TimeUtils.SECOND * 1.5), (long)(TimeUtils.SECOND / 2));
	}
	
	public void updateGUI() {
		task.runTask(true);
	}
	
	public static class UpdaterTask extends TimerTask {
		
		protected final ProgressPage page;
		
		
		public UpdaterTask(ProgressPage page) {
			this.page = page;
		}

		private volatile static transient int runs = 0;
		
		private static Statistic.Info compressed = null;
		private static Statistic.Info decompressed = null;
		private static Statistic.Info requests = null;
		
		public void normalUpdate() {
			
			this.page.txtTotalBytes.setText(Statistic.getTotalBytes());
			this.page.txtCompressedBytes.setText(Statistic.getCompressedBytes());
			this.page.txtDecompressedBytes.setText(Statistic.getDecompressedBytes());
			
//			System.out.println("a");
//			Random r = new Random();
//			this.page.memoryBar.setSelection(r.nextInt(100));
		}
		
		private final static double speed(long size, long time) {
			return ((double)size * (double)TimeUtils.SECOND) /  (double)time ;
		}
		
		
		private final static String speedReqText(double rc) {
			return Convertors.formatValue(rc) + " req / sec";
		}
		
		private final static String speedText(double speed) {
			long v = Math.round( speed );
			return Convertors.autoSize(v) + " / sec";
		}
		
		public void slowUpdateFactor2() {
			
			double doubleSpeedPack = 0d;
			double doubleSpeedUnPack = 0d;
			
			Statistic.Info requestNow = Statistic.getRequestAsInfo();
			if (null != requests && requestNow.bytes > requests.bytes) {
				long req = requestNow.bytes - requests.bytes;
				long time = requestNow.time - requests.time;
				double reqBench = speed(req, time);
				
				this.page.txtReqProSec.setText(speedReqText(reqBench));
			}
			
			Statistic.Info compressedNow = Statistic.getCompressedBytesAsInfo();
			if (null != compressed && compressedNow.bytes > compressed.bytes) {
				long size = compressedNow.bytes - compressed.bytes;
				long time = compressedNow.time - compressed.time;
				doubleSpeedPack = speed(size, time);
				
				this.page.txtComprSpeed.setText(speedText(doubleSpeedPack));
			}
			
			Statistic.Info decompressedNow = Statistic.getDecompressedBytesAsInfo();
			if (null != decompressed && decompressedNow.bytes > decompressed.bytes) {
				long size = decompressedNow.bytes - decompressed.bytes;
				long time = decompressedNow.time - decompressed.time;
				doubleSpeedUnPack = speed(size, time);
				
				this.page.txtDecompSpeed.setText(speedText(doubleSpeedUnPack));
			}
			
			if (doubleSpeedPack > 0d || doubleSpeedUnPack > 0d) {
				
				double divider = 0d;
				if (doubleSpeedPack > 0d) divider += 1d;
				if (doubleSpeedUnPack > 0d) divider += 1d;
				
				double average = (doubleSpeedPack + doubleSpeedUnPack)/divider;
				this.page.txtAverageSpeed.setText(speedText(average));
			}
									
			compressed = compressedNow;
			decompressed = decompressedNow;
			requests = requestNow;
			
			this.page.txtReqCounter.setText(Statistic.getRequests());
			
			this.page.txtMemSaved.setText(Convertors.autoSize(Statistic.getCompressionRequests() * Config.instance().sizedSaved));
			
		}
		
		private volatile transient static long maxUsedB = 0l;
		
		public void slowUpdateFactor4() {
//			System.out.println("c");
			Runtime rt = Runtime.getRuntime();
			
			long usedB = rt.totalMemory() - rt.freeMemory();
			long maxB = rt.maxMemory();
			if (usedB > maxUsedB) maxUsedB = usedB;
			
			long used = (usedB)/1024;
			long max = (maxB)/1024;
			
			this.page.txtMemUsed.setText(Convertors.autoSize(usedB));
			if (maxUsedB>0l) this.page.txtMemMaxUsed.setText(Convertors.autoSize(maxUsedB));
			
			this.page.memoryBar.setMaximum((int)max);
			this.page.memoryBar.setSelection((int)used);
			
		}
		
		@Override
		public void run() {
			
			runTask(false);
		}

		public void runTask(final boolean updateAll) {
			Display.getDefault().asyncExec(new Runnable() {
	            public void run() {
	            	if (UpdaterTask.this.page == null || UpdaterTask.this.page.isDisposed()) return ; 
	            	//UpdaterTask.this.page.setRedraw(false);
	            	normalUpdate();
	            	if (updateAll || runs % 2 == 0) {
	            		slowUpdateFactor2();
	            		if (updateAll || runs % 4 == 0) {
	            			slowUpdateFactor4();
	            		}
	            	}
	            	//UpdaterTask.this.page.setRedraw(true);
	            }
	         });
			
			runs++;
			Thread.yield();
		}
		
	}
	
	
}
