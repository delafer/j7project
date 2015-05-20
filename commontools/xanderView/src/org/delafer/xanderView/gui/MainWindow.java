package org.delafer.xanderView.gui;

import static org.eclipse.swt.SWT.*;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import org.delafer.xanderView.SplashWindow;
import org.delafer.xanderView.general.State;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.CommonContainer;
import org.delafer.xanderView.interfaces.CopyService;
import org.delafer.xanderView.orientation.OrientationCommons.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public final class MainWindow extends ToRefactor{

	private MultiShell shell;
	private Display display;
	private Composite cmpEmbedded;
	private ImagePanel panel;
	private static CommonContainer pointer;
	LazyUpdater updater = null;
	private static final Point SHELL_SIZE = new Point(940, 720);


	public MainWindow() {
	}

	public void open(String path) {
		initPath(path);
		intitialize();
		show();
		runGlobalEventLoop();
	}

	private void initPath(String path) {
//		String path = "D:\\test3.zip";
		pointer = new CommonContainer(path);

	}

	protected void show () {

		shell = new MultiShell(display,  ON_TOP | NO_REDRAW_RESIZE | NO_BACKGROUND | APPLICATION_MODAL | NO_SCROLL | DOUBLE_BUFFERED  | SWT.SHELL_TRIM  );
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
	        	if(updater == null || !updater.isAlive()) {
	    			updater = new LazyUpdater(MainWindow.this.panel);
	    		}
	    		updater.update();

	    		e.doit = false;
	        }

	    });

		Monitor monitor = getActiveMonitor();
		locateWindow(monitor);

		createMenuBar();
		createToolBar();
		createImageCanvas();
		shell.open ();
		if (pointer.getCurrent() != null ){
			loadImage(pointer, pointer.getCurrent(), panel);
		}

	}

	private void createImageCanvas() {
		panel = new ImagePanel ();


		cmpEmbedded = new Composite(shell.active(),  EMBEDDED | NO_REDRAW_RESIZE  | NO_BACKGROUND  | NO_SCROLL | DOUBLE_BUFFERED );

//		cmpEmbedded.setParent(shell);


		cmpEmbedded.setLayout(null);
		cmpEmbedded.addListener (SWT.Resize,  new Listener () {
		    public void handleEvent (Event e) {
		        Rectangle rect = shell.getClientArea ();
		        System.out.println(rect);
		      }
		    });
		java.awt.Frame awtFrame = SWT_AWT.new_Frame( cmpEmbedded );
//		awtFrame.setUndecorated(true);
		awtFrame.setResizable(false);
		awtFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		awtFrame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getClickCount()==2){
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							toggleFullscreen();
						}
					});

		        }
			}

		});
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
			loadImage(pointer, pointer.getPrevious(), panel);
			break;

		case 16777233://F8
		case 16777232://F7
			State res = CopyService.instance().copy(pointer.getCurrent());

			SplashWindow splash = new SplashWindow(shell.active(), res);
			break;
		case 108:
			panel.rotate(Action.RotateLeft);
			panel.showImage();
			break;
		case 114:
			panel.rotate(Action.RotateRight);
			panel.showImage();
			break;
		case 118:
			panel.rotate(Action.FlipVertical);
			panel.showImage();
			break;
		case 104:
			panel.rotate(Action.FlipHorizontal);
			panel.showImage();
			break;
		case SWT.ARROW_RIGHT:
		case SWT.ARROW_DOWN:
		case SWT.SPACE:
		case SWT.PAGE_DOWN:
			loadImage(pointer, pointer.getNext(), panel);
			break;
		case SWT.CR:
			toggleFullscreen();
			break;
		default:
			System.out.println("code:"+e.keyCode);
			break;
		}
	}

	private void toggleFullscreen() {
		boolean isFullScreen  = !shell.getFullScreen();

		shell.setFullScreen(isFullScreen);
		shell.setMaximized (isFullScreen);

		cmpEmbedded.setParent(shell.active());

		shell.setModified(true);
		shell.active().redraw();
		shell.active().layout(true);

		panel.preRenderImage();
		panel.showImage();

		shell.active().notifyListeners(SWT.Resize, getResizeEvent());

	}

	private Event getResizeEvent() {
		Event event = new Event();
		event.type = SWT.Resize;
		event.doit = true;
		event.display = Display.getCurrent();
		event.time = OS.GetMessageTime();
		event.widget = shell.active();
		event.item = event.widget;
		return event;
	}

	Menu createMenuBar() {
		// Menu bar.
		Menu menuBar = new Menu(shell.getWndShell(), SWT.BAR);
		shell.setMenuBar(menuBar);
		createFileMenu(menuBar);
		return menuBar;
	}

	void createFileMenu(Menu bar) {
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("&Menu");
		Menu submenu = new Menu (shell.getWndShell(), SWT.DROP_DOWN);
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
		Shell[] shells;
		while (!display.isDisposed ())
			if (!display.readAndDispatch()) {
				shells = display.getShells();
				if (shells.length < 2) break;
				display.sleep();
			}

		display.dispose ();
	}

	public Dimension getSize() {
		return panel.getSize();
	}





	}
