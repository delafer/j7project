package org.delafer.xanderView.gui;

class LazyUpdater extends Thread {

	volatile long start;
	final static long interval = 250;
	private ImagePanel panel;

	/**
	 *
	 */
	public LazyUpdater(ImagePanel panel) {
		super("");
		this.panel = panel;
		this.setDaemon(true);
		this.setPriority((Thread.MIN_PRIORITY + Thread.NORM_PRIORITY)>>1);
	}

	public synchronized void update() {

		if (!this.isAlive()) this.start();

		this.start = System.currentTimeMillis();

	}



	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (System.currentTimeMillis() - start < interval) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
		drawIt();
	}

	/**
	 *
	 */
	private final void drawIt() {
		panel.preRenderImage();
    	panel.showImage();
	}

}
