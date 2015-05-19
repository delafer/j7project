package org.delafer.xanderView.gui;

import static org.eclipse.swt.SWT.*;

import java.util.ArrayList;
import java.util.List;

import net.j7.commons.types.DoubleValue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class MultiShell  {


	Shell wndShell;


	Shell fsShell;
	boolean fullscreen;


	private List<DoubleValue<Integer, Listener>> listeners;

	public Shell getWndShell() {
		return wndShell;
	}

	public MultiShell(Display display, int style) {
		listeners = new ArrayList<DoubleValue<Integer,Listener>>();
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
		listeners.add(new DoubleValue<Integer, Listener>(move, listener));
		active().addListener(move, listener);
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

		Point loc = active().getLocation();
		for (DoubleValue<Integer, Listener> lst : listeners) {
			active().removeListener(lst.getOne(), lst.getTwo());
		}

		fullscreen = isFullScreen;

		fsShell.setVisible(isFullScreen);
		wndShell.setVisible(!isFullScreen);

		for (DoubleValue<Integer, Listener> lst : listeners) {
			active().addListener(lst.getOne(), lst.getTwo());
		}

		active().setLocation(loc);
		active().setActive();
		active().setFocus();

	}

	public void setVisible(boolean b) {
		active().setVisible(b);
	}

	public void setMenuBar(Menu menuBar) {
		active().setMenuBar(menuBar);

	}

	public Point getLocation() {
		return active().getLocation();
	}


}
