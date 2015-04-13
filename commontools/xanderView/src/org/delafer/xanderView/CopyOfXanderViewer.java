package org.delafer.xanderView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.libjpegturbo.turbojpeg.test.ImageViewer4;

public class CopyOfXanderViewer {
	
	private Display display;
	private Shell shell;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CopyOfXanderViewer app = new CopyOfXanderViewer();
		app.open();
	}

	private void open() {
		// Create a window and set its title.
		display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM);
		
		createMenuBar();
		
		shell.layout(true);
		shell.open();
		
		runGlobalEventLoop();
	}
	
	private void runGlobalEventLoop()
	{
		while (!shell.isDisposed ()) 
			if (!display.readAndDispatch ()) display.sleep ();
		display.dispose ();
	}

	Menu createMenuBar() {
		// Menu bar.
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		createFileMenu(menuBar);
		return menuBar;
	}

	void createFileMenu(Menu menuBar) {
		// File menu
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(fileMenu);

		item = new MenuItem(fileMenu, SWT.NULL);
		item.setText("&Open");
		item.setAccelerator(SWT.CTRL + 'O');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//openFile();
			}
		});

		item = new MenuItem(fileMenu, SWT.NULL);
		item.setText("Open &URL");
		item.setAccelerator(SWT.CTRL + 'U');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//openURL();
			}
		});

		new MenuItem(fileMenu, SWT.SEPARATOR);

		// File -> Exit
		item = new MenuItem(fileMenu, SWT.NULL);
		item.setText("Exit");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

		// Test menu
		item = new MenuItem(menuBar, SWT.CASCADE);

	}
	
	
}
