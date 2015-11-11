package org.delafer.xmlbench;

import org.delafer.xmlbench.resources.ImageRepository;
import org.delafer.xmlbench.wizard.SmartWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {

	protected Shell shlXmlBenchmarks;
	private Display display;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			System.out.println(System.getProperty("java.vm.name"));
			System.out.println(System.getProperty("java.vm.info"));

			MainWindow window = new MainWindow();
			Shutdown.addHook();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MainWindow() {
		super();
		display = Display.getDefault();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		createContents();
		shlXmlBenchmarks.open();
		shlXmlBenchmarks.layout();
		while (!shlXmlBenchmarks.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		System.exit(0);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlXmlBenchmarks = new Shell();
		shlXmlBenchmarks.setSize(700, 500);
		shlXmlBenchmarks.setText("XML Compression Benchmarks");
		shlXmlBenchmarks.setLayout(new FillLayout(SWT.HORIZONTAL));
		ImageRepository.loadImages(display);
		shlXmlBenchmarks.setImage(ImageRepository.getImage("main_icon"));
		Menu menu = new Menu(shlXmlBenchmarks, SWT.BAR);
		shlXmlBenchmarks.setMenuBar(menu);

		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Menu");

		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);

		MenuItem mntmNewItem_1 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Shutdown.exit();
				System.exit(0);
			}
		});
		mntmNewItem_1.setText("Exit");

		SmartWizard composite = new SmartWizard(shlXmlBenchmarks, SWT.NONE);
		composite.init();
	}
}
