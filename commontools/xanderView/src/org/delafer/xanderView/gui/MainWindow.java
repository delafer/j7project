package org.delafer.xanderView.gui;

import static org.eclipse.swt.SWT.APPLICATION_MODAL;
import static org.eclipse.swt.SWT.DOUBLE_BUFFERED;
import static org.eclipse.swt.SWT.EMBEDDED;
import static org.eclipse.swt.SWT.HORIZONTAL;
import static org.eclipse.swt.SWT.NO_BACKGROUND;
import static org.eclipse.swt.SWT.NO_REDRAW_RESIZE;
import static org.eclipse.swt.SWT.NO_SCROLL;
import static org.eclipse.swt.SWT.ON_TOP;
import static org.eclipse.swt.SWT.SHELL_TRIM;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import javax.swing.SwingUtilities;
import javax.swing.text.MutableAttributeSet;

import net.j7.commons.reflection.ReflectionHelper;
import net.j7.commons.types.MutableLong;

import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.CommonContainer;
import org.delafer.xanderView.interfaces.CopyService;
import org.delafer.xanderView.orientation.OrientationCommons.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

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

	public void open() {
		testInit();
		intitialize();
		show();
		runGlobalEventLoop();
	}

	private void testInit() {
//		String path = "D:\\test3.zip";
		String path = "D:\\test\\";
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
			CopyService.instance().copy(pointer.getCurrent());
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
//			e.doit = false;
			break;
		default:
			System.out.println("code:"+e.keyCode);
			break;
		}
	}

	private void toggleFullscreen() {
		boolean isFullScreen  = !shell.getFullScreen();

		shell.setMaximized (isFullScreen);
		shell.setFullScreen(isFullScreen);


		if (isFullScreen) {
			int a = (Integer)ReflectionHelper.getFieldValue(shell, "style");
//			System.out.println(a);
//			int x = (ON_TOP  | NO_BACKGROUND  | NO_SCROLL | DOUBLE_BUFFERED  |  SWT.NO_TRIM);
//			ReflectionHelper.setFieldValue(shell, "style", x);
//			System.out.println("x="+x+" style"+shell.getStyle());
//			shell.getMenuBar().dispose();
//			shell.setBounds(this.getActiveMonitor().getBounds());
//			shell.getVerticalBar().dispose();
			shell.setVisible(false);
		} else {
			createMenuBar();
		}

		shell.setModified(true);
//		shell.layout(true);
//		shell.setRedraw(true);
//		shell.redraw();
//    	System.out.println(">"+cmpEmbedded.getSize().x+ "<>"+cmpEmbedded.getSize().y);
//    	System.out.println(">>"+cmpEmbedded.getClientArea().width+ "<>"+cmpEmbedded.getClientArea().height);
//    	System.out.println(">>>"+panel.getWidth()+"<>"+panel.getHeight());
//    	System.out.println("4>"+panel.getBounds().width+"<>"+panel.getBounds().height);
//    	System.out.println("4>"+panel.getSize().width+"<>"+panel.getSize().height);
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
				if (shells.length == 0) break;
				display.sleep();
			}

		display.dispose ();
	}

	public Dimension getSize() {
		return panel.getSize();
	}





	}
