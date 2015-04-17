package org.delafer.xanderView.gui;

import org.delafer.xanderView.general.Shutdown;

public class MainEntryPoint {


	public static void main(String[] args) {
		MainWindow gui = new MainWindow();
		Shutdown.addHook();
		gui.open();

	}

}
