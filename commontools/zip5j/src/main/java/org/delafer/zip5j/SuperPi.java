package org.delafer.zip5j;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;

public class SuperPi {

	protected Shell shell;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SuperPi window = new SuperPi();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		createMenu();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("Super PI / mod2.0 Java");

	}

	public void createMenu() {
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem calcItem = new MenuItem (bar, SWT.NONE);
		calcItem.setText ("Calculate(&C)");
		calcItem.setAccelerator('C');

		MenuItem aboutItem = new MenuItem (bar, SWT.NONE);
		aboutItem.setText ("About...(&A)");
		aboutItem.setAccelerator('A');

		MenuItem helpItem = new MenuItem (bar, SWT.NONE);
		helpItem.setText ("Help...(&H)");
		helpItem.setAccelerator('H');

		text = new Text(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

		calcItem.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				System.out.println ("Select All");
			}
		});

	}

}
