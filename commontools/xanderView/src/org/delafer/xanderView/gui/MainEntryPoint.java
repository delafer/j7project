package org.delafer.xanderView.gui;

import net.j7.commons.strings.StringUtils;
import net.j7.commons.utils.Metrics;

import org.delafer.xanderView.general.Shutdown;
import org.delafer.xanderView.gui.helpers.ImageRepository;
import org.eclipse.swt.widgets.Display;

public class MainEntryPoint {


	public static void main(String[] args) {
		String toOpen = args.length > 0 ? args[0] : null;
		if (StringUtils.isEmpty(toOpen)) {
			System.out.println("Please specify an image name or directory to start jpeg viewer");
			System.exit(0);
		}
		Metrics.enable(true);

		ImageRepository.loadImages();
		MainWindow gui = new MainWindow();
		Shutdown.addHook();
		gui.open(args[0]);


	}

}
