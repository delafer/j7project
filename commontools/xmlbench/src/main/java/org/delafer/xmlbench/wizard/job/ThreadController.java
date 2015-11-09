package org.delafer.xmlbench.wizard.job;

import java.util.LinkedList;
import java.util.List;

import org.delafer.xmlbench.config.Config;
import org.delafer.xmlbench.stats.Statistic;
import org.delafer.xmlbench.stats.StatsUpdaterThread;
import org.delafer.xmlbench.wizard.pages.ProgressPage;

public class ThreadController {
	
	
	int threads;
	boolean alive;
	List<CompressorThread> th = new LinkedList<CompressorThread>();
	ProgressPage page;
	private StatsUpdaterThread guiUpdater;
	
	public ThreadController(ProgressPage page) {
		super();
		threads  = Config.instance().threads;
		alive = false;
		this.page = page;
	}
	
	public void checkAlive() {
		if (alive) return ;
		
		for (int i = 0; i < threads; i++) {
			CompressorThread thread = new CompressorThread();
			th.add(thread);
		}
		
		alive = true;
	}
	
	
	public synchronized void start() {
		checkAlive();
		
		startGuiUpdater();
		
		for (CompressorThread thread : th) {
			thread.runJob();
		}
	}

	private void startGuiUpdater() {
		guiUpdater = new StatsUpdaterThread(page);
	};
	
	private void stopGuiUpdater() {
		guiUpdater.cancel();
		guiUpdater.purge();
		guiUpdater = null;
	};
	
	private void updateGUI() {
		guiUpdater.updateGUI();
	};
	
	public synchronized void pause() {
		if (!alive) return ;
		
		stopGuiUpdater();
		
		
		for (CompressorThread thread : th) {
			thread.pauseJob();
		}
	}


	
	
	public synchronized void stop() {
		if (!alive) return ;
		
		for (CompressorThread thread : th) {
			thread.stopJob();
		}
		
		try {
			Thread.sleep(450);
		} catch (InterruptedException e) {}
		
		Statistic.reset();
		updateGUI();
		stopGuiUpdater();
		
//		try {
//			Thread.sleep(450);
//		} catch (InterruptedException e) {}
		
		
		alive = false;
	};
	
	
	
}
