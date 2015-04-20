package org.delafer.xanderView.gui;

import static org.eclipse.swt.SWT.*;

import java.awt.Dimension;

import org.delafer.xanderView.file.FilePointer;
import org.delafer.xanderView.file.ImageFinder;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import com.sun.glass.ui.Application;

public final class MainWindow extends ToRefactor{

	private Shell shell;
	private Display display;
	private Composite cmpEmbedded;
	private ImagePanel panel;
	private static FilePointer pointer;

	private static final Point SHELL_SIZE = new Point(940, 720);


	public MainWindow() {
	}

	public void open() {
		testInit();
		intitialize();
		show();
		runGlobalEventLoop();
	}

	private void testInit() {
		String path = "T:\\really.2015.new5.pb\\jane_k8037.jpg";
		String[] files = ImageFinder.getImages(path);
		pointer = new FilePointer(files, path);

	}

	protected void show () {

		shell = new Shell(display, SHELL_TRIM | ON_TOP | NO_REDRAW_RESIZE | NO_BACKGROUND | APPLICATION_MODAL | NO_SCROLL );
		shell.setLayout(new FillLayout(HORIZONTAL));
		shell.setSize( SHELL_SIZE );

		shell.addListener(SWT.Move, new Listener() {

	        public void handleEvent(Event e) {
	            Rectangle rect = shell.getBounds();
	            ApplConfiguration cfg = ApplConfiguration.instance();
	            cfg.set(ApplConfiguration.CFG_POS_X, rect.x);
	            cfg.set(ApplConfiguration.CFG_POS_Y, rect.y);
	            cfg.set(ApplConfiguration.CFG_WIDTH, rect.width);
	            cfg.set(ApplConfiguration.CFG_HEIGHT, rect.height);
	        }

	    });

		shell.addListener(SWT.Resize, new Listener() {
	        public void handleEvent(Event e) {
	        	System.out.println(">"+cmpEmbedded.getSize().x+ "<>"+cmpEmbedded.getSize().y);
	        	System.out.println(">>"+cmpEmbedded.getClientArea().width+ "<>"+cmpEmbedded.getClientArea().height);
	        	System.out.println(">>>"+panel.getWidth()+"<>"+panel.getHeight());
	        	System.out.println("4>"+panel.getBounds().width+"<>"+panel.getBounds().height);
	        	System.out.println("4>"+panel.getSize().width+"<>"+panel.getSize().height);
	        }

	    });

		Monitor monitor = getActiveMonitor();
		locateWindow(monitor);

		createMenuBar();
		createToolBar();
		createImageCanvas();

		shell.open ();

	}

	private void createImageCanvas() {
		panel = new ImagePanel ();

		cmpEmbedded = new Composite(shell,  EMBEDDED | NO_REDRAW_RESIZE  | NO_BACKGROUND  | NO_SCROLL );
		cmpEmbedded.setLayout(null);
		java.awt.Frame awtFrame = SWT_AWT.new_Frame( cmpEmbedded );
		awtFrame.add(panel);
	}

	private void locateWindow(Monitor monitor) {

		ApplConfiguration cfg = ApplConfiguration.instance();
		int x = cfg.getInt(ApplConfiguration.CFG_POS_X);
		int y = cfg.getInt(ApplConfiguration.CFG_POS_Y);
		if (x == 0 && y == 0) {
			Rectangle bounds = monitor.getBounds ();
			Rectangle rect = shell.getBounds ();
			x = bounds.x + (bounds.width - rect.width) / 2;
			y = bounds.y + (bounds.height - rect.height) / 2;
		}
		shell.setLocation (x, y);
	}

	private void intitialize() {
		display = new Display ();
		display.setWarnings(false);
		display.addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) { bindKeyEvent(e); }
		});
	}

	protected void bindKeyEvent(Event e) {
		switch (e.keyCode) {
		case SWT.ESC:
			shell.close();
			System.exit(0);
			break;
		case SWT.ARROW_LEFT:
		case SWT.ARROW_UP:
		case SWT.BS:
		case SWT.PAGE_UP:
			loadImage(pointer.prev(), panel);
			break;
		case SWT.ARROW_RIGHT:
		case SWT.ARROW_DOWN:
		case SWT.SPACE:
		case SWT.PAGE_DOWN:
			loadImage(pointer.next(), panel);
			break;
		case SWT.CR:
			toggleFullscreen();
//			e.doit = false;
			break;
		default:
			System.out.println(e.keyCode);
			break;
		}
	}

	private void toggleFullscreen() {
		boolean isFullScreen  = !shell.getFullScreen();
		shell.setFullScreen(isFullScreen);
		shell.setMaximized (isFullScreen);
		if (isFullScreen) {
			shell.getMenuBar().dispose();
		} else {
			createMenuBar();
		}

		shell.setModified(true);

    	System.out.println(">"+cmpEmbedded.getSize().x+ "<>"+cmpEmbedded.getSize().y);
    	System.out.println(">>"+cmpEmbedded.getClientArea().width+ "<>"+cmpEmbedded.getClientArea().height);
    	System.out.println(">>>"+panel.getWidth()+"<>"+panel.getHeight());
    	System.out.println("4>"+panel.getBounds().width+"<>"+panel.getBounds().height);
    	System.out.println("4>"+panel.getSize().width+"<>"+panel.getSize().height);
	}

	Menu createMenuBar() {
		// Menu bar.
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		createFileMenu(menuBar);
		return menuBar;
	}

	void createFileMenu(Menu bar) {
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("&Menu");
		Menu submenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (submenu);

		UIHelpers.addMenuItem(submenu, "Select &Templates to read\tCtrl+T", SWT.MOD1 + 'T', null);
		UIHelpers.addMenuItem(submenu, "Exit", 0, null);

	}

	private void createToolBar() {
		//ImageRepository.loadImages(display);
	}


	protected Monitor getActiveMonitor() {

		return getClosestMonitor(Display.getCurrent(), shell.getLocation());
	}

	private static Monitor getClosestMonitor(Display toSearch, Point toFind) {
		int closest = Integer.MAX_VALUE;

		Monitor[] monitors = toSearch.getMonitors();
		Monitor result = monitors[0];

		for (int idx = 0; idx < monitors.length; idx++) {
			Monitor current = monitors[idx];

			Rectangle clientArea = current.getClientArea();

			if (clientArea.contains(toFind)) {
				return current;
			}

			int distance = Geometry.distanceSquared(Geometry.centerPoint(clientArea), toFind);
			if (distance < closest) {
				closest = distance;
				result = current;
			}
		}

		return result;
	}



	private void runGlobalEventLoop()
	{
		while (!shell.isDisposed ())
			if (!display.readAndDispatch()) display.sleep ();

		display.dispose ();
	}

	public Dimension getSize() {
		return panel.getSize();
	}





	}
