package org.delafer.zip5j;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class MainWindow {

	private Display display;
	private Shell shell;
	/**
	 *
	 */
	public MainWindow(Display display) {

		this.display = display;
		init();

	}

	private void init() {
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setSize( 600, 400 );
		shell.setLayout ( new FillLayout() );

		Monitor primary = display.getPrimaryMonitor ();
		Rectangle bounds = primary.getBounds ();
		Rectangle rect = shell.getBounds ();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation (x, y);


		createMenu();
		createTabFolder();
	}

	private void createTabFolder() {
		final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
		for (int i=0; i<2; i++) {
			TabItem item = new TabItem (tabFolder, SWT.NONE);
			item.setText ("TabItem " + i);
			Button button = new Button (tabFolder, SWT.PUSH);
			button.setText ("Page " + i);
			item.setControl (button);
		}
	}

	public void createMenu() {
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("&File");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (submenu);
		MenuItem item = new MenuItem (submenu, SWT.PUSH);
		item.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				System.out.println ("Select All");
			}
		});
		item.setText ("Select &All\tCtrl+A");
		item.setAccelerator (SWT.MOD1 + 'A');

	}

	public Shell getShell() {
		return shell;
	}


}
