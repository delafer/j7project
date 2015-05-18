package org.delafer.xanderView.gui;

import static org.eclipse.swt.SWT.APPLICATION_MODAL;
import static org.eclipse.swt.SWT.DOUBLE_BUFFERED;
import static org.eclipse.swt.SWT.NO_BACKGROUND;
import static org.eclipse.swt.SWT.NO_REDRAW_RESIZE;
import static org.eclipse.swt.SWT.NO_SCROLL;
import static org.eclipse.swt.SWT.ON_TOP;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class MultiShell  {


	Shell wndShell;
	public Shell getWndShell() {
		return wndShell;
	}

	Shell fsShell;
	boolean fullscreen;

	public MultiShell(Display display, int style) {
		wndShell = new Shell(display,  ON_TOP | NO_REDRAW_RESIZE | NO_BACKGROUND | APPLICATION_MODAL | NO_SCROLL | DOUBLE_BUFFERED  | SWT.SHELL_TRIM  );
		fsShell = new Shell(display,  ON_TOP | NO_REDRAW_RESIZE | NO_BACKGROUND | APPLICATION_MODAL | NO_SCROLL | DOUBLE_BUFFERED  | SWT.NO_TRIM  );
	}

	public void setLayout(FillLayout layout) {
		wndShell.setLayout(layout);
		fsShell.setLayout(layout);

	}

	public Shell active() {
		return fullscreen ? fsShell : wndShell;
	}

	public void setSize(Point shellSize) {
		active().setSize(shellSize);

	}

	public void addListener(int move, Listener listener) {
		wndShell.addListener(move, listener);
		fsShell.addListener(move, listener);

	}

	public void open() {
		active().open();

	}

	public Rectangle getBounds() {
		return active().getBounds();
	}

	public void setLocation(int x, int y) {
		active().setLocation(x, y);

	}

	public boolean getFullScreen() {
		return this.fullscreen;
	}

	public void setMaximized(boolean isFullScreen) {
		active().setMaximized(isFullScreen);
	}


	public void setModified(boolean b) {
		active().setModified(b);

	}

	public Rectangle getClientArea() {
		return active().getClientArea();
	}

	public void close() {
		active().close();

	}


	public void setFullScreen(boolean isFullScreen) {
		// TODO Auto-generated method stub

	}

	public void setVisible(boolean b) {
//		active().se

	}

	public void setMenuBar(Menu menuBar) {
		active().setMenuBar(menuBar);

	}

	public Point getLocation() {
		return active().getLocation();
	}


}
