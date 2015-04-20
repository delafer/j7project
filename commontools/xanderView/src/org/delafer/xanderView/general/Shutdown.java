package org.delafer.xanderView.general;

import org.delafer.xanderView.gui.config.ApplConfiguration;


public class Shutdown {

	public static void addHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Terminating application...");
				ApplConfiguration.instance().save();
			}
		});
	}

	public static void exit() {
		try {
		Thread cur = Thread.currentThread();
		int tCnt = Thread.activeCount();
		Thread[] threads = new Thread[tCnt];
		Thread.enumerate(threads);
		for (Thread thread : threads) {
			try {
				if (thread != cur)
				thread.interrupt();
			} catch (Throwable e) {}
		}
		System.out.println(tCnt+" ACTIVE THREADS STOPPED");
		System.exit(0);
//		cur.interrupt();

	} catch (Exception e) {
		e.printStackTrace();
	}

	}
}
