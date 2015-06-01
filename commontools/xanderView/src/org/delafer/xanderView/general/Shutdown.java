package org.delafer.xanderView.general;

import java.io.IOException;

import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.gui.config.OrientationStore;


public class Shutdown {

	public static void addHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Terminating application...");
				ApplConfiguration.instance().save();
				try {
					OrientationStore.instance().save();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
