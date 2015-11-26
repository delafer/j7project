/**
 *
 */
package org.delafer.zip5j;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * @author Administrator
 *
 */
public class Starter {

	private static Display display = new Display();

	private static void runGlobalEventLoop(Shell shell)
	{
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainWindow window = new MainWindow(display);
		Shell shell = window.getShell();
		shell.open();
		runGlobalEventLoop(shell);
	}

}
