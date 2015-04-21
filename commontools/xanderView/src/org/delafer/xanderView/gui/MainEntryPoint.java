package org.delafer.xanderView.gui;

import net.j7.commons.utils.Metrics;

import org.delafer.xanderView.general.Shutdown;

public class MainEntryPoint {


	public static void main(String[] args) {
		Metrics.enable(false);
		MainWindow gui = new MainWindow();
		Shutdown.addHook();
		gui.open();


	}

}
