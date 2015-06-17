package org.delafer.xanderView.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import net.j7.commons.base.CommonUtils;
import net.j7.commons.strings.StringUtils;
import net.j7.commons.ui.StackTrace;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.gui.config.OrientationStore.ImageData;
import org.delafer.xanderView.gui.helpers.MultiShell;
import org.delafer.xanderView.orientation.CommonRotator;
import org.delafer.xanderView.orientation.OrientationCommons;
import org.delafer.xanderView.orientation.OrientationCommons.Action;
import org.delafer.xanderView.orientation.OrientationCommons.Orientation;
import org.delafer.xanderView.orientation.Rotator2D;
import org.delafer.xanderView.scale.ScaleFactory;

import com.carrotsearch.hppc.IntFloatHashMap;

public class ImageCanvas extends Canvas implements MouseListener  {

	private static final float scaleFactor = 1.05f;
	private static final long serialVersionUID = 9162619010168531038L;
	BufferedImage imageSource;
	BufferedImage drawImage;
	String text;

	Orientation orientation;
	Orientation orientationDefault;
	int scaleIdx;

	static IntFloatHashMap scaleData;
	static {
		scaleData = new IntFloatHashMap(63);
		float s1 = 1f, s2 = s1;
		for (int i = 1; i < 32; i++) {
			s1 /= ImageCanvas.scaleFactor;
			s2 *= ImageCanvas.scaleFactor;
			scaleData.put(-i, s1);
			scaleData.put(i, s2);
		}
	}

//	LazyUpdater updater;
	MultiShell shell;
	private final static transient Font font = new Font("MS Reference Sans Serif", Font.BOLD, 20);

    public ImageCanvas(MultiShell shell) {
    	super();
    	this.createBufferStrategy(1);
    	this.shell = shell;
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        //this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);

        addMouseListener(this);
        //this.setOpaque(true);
    }

    public boolean setImage(BufferedImage image, String text, ImageData imgData) {
   	 	this.imageSource = image;
   	 	this.text = text;
   	 	this.orientationDefault = getDirectionDefault();
   	 	this.orientation = CommonUtils.nvl(imgData.getOrientation(), orientationDefault);
   	 	this.scaleIdx = imgData.getScaleConst();
   	 	return preRenderImage();
    }

    private Orientation getDirectionDefault() {
    	ImageSize imgSize = getImageSize(false);
    	ImageSize cnvSize = getCanvasImageSize();

    	boolean rotate = (imgSize.width() > imgSize.height()) != (cnvSize.width() > cnvSize.height());
    	rotate = rotate && (max(imgSize.width(), imgSize.height()) / min(imgSize.width(), imgSize.height()) >= ImageCanvas.scaleFactor);

    	return !rotate ? Orientation.Original : Orientation.RotatedLeft;
	}



	private float max(int arg1, int arg2) {
		return (float)(arg1 > arg2 ? arg1 : arg2);
	}

	private float min(int arg1, int arg2) {
		return (float)(arg1 < arg2 ? arg1 : arg2);
	}


	public void scaleReset() {
		scaleIdx = 0;
		preRenderImage();
	}

	public void scaleUp() {
		if (scaleIdx<31) scaleIdx++;

		preRenderImage();
	}

	public void scaleDown() {
		if (scaleIdx>-31) scaleIdx--;

		preRenderImage();
	}


	public Orientation rotate(Action action) {
    	orientation = orientation.newState(action);
    	preRenderImage();
    	return orientation;
    }

    private ImageSize getCanvasImageSize() {
    	return new ImageSize(getWidth(), getHeight());
    }

    private ImageSize getImageSize(boolean swapXY) {
    	ImageSize is = new ImageSize(imageSource.getWidth(null), imageSource.getHeight(null));
    	if (swapXY) is.swapXY();
    	return is;
    }


    public boolean preRenderImage() {
    	if (null == imageSource || orientation == null) return false;
    	boolean swapXY = Orientation.Original != orientation && orientation.swapXY();
    	ImageSize imgSize = getImageSize(swapXY);
    	ImageSize cnvSize = getCanvasImageSize();

    	if (!imgSize.equals(cnvSize)) {
    		ImageSize size = OrientationCommons.getNewSize(imgSize.width(), imgSize.height(), cnvSize.width(), cnvSize.height());
    		if (size.empty()) return false;

    		if (scaleIdx != 0) size.scale(scaleData.get(scaleIdx));
    		drawImage = ScaleFactory.instance(imgSize).resize(imageSource, !swapXY ? size.width() : size.height(), !swapXY ? size.height() : size.width());
    	} else {
    		drawImage = imageSource;
    	}

    	if (!Orientation.Original.equals(orientation)) {
    		CommonRotator ir = new Rotator2D();
        	drawImage = ir.rotate(drawImage, orientation);
    	}

//    	new Thread() {
//			public void run() {
////		    	GammaFilter filter = new GammaFilter(0.5f);
//				SwimFilter filter = new SwimFilter();
//				filter.setTurbulence(0.4f);
//				filter.setAmount(50f);
//				filter.setStretch(10);
//				filter.setTime(1f);
//				drawImage = filter.filter(drawImage, null);
//				ImageCanvas.this.showImage();
//			}
//
//    	}.start();

    	return true;
    }


    public void showImage() {
    	Graphics g = getGraphics();
    	if (null != g) paint(g);
//    	this.getParent().repaint();
    }


	public void paintComponent(Graphics g) {
        //super.paintComponent(g);  // Paint background
        paintCanvas(this, g);

    }

	public void paintCanvas(ImageCanvas panel, Graphics g) {
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        if (panel.drawImage!=null) {
        	Rectangle dim = this.getBounds();
        	int x = (dim.width - panel.drawImage.getWidth(null)) >> 1;
        	int y  = (dim.height - panel.drawImage.getHeight(null)) >> 1;

        	g.drawImage(panel.drawImage, x, y, null);
        }

        if (!StringUtils.isEmpty(panel.text)) {

        	AttributedString as = new AttributedString(panel.text);

            as.addAttribute(TextAttribute.FONT,font);
            as.addAttribute(TextAttribute.FOREGROUND,  (panel.text.indexOf('*')<0 ? Color.GREEN : Color.RED));
            g.setFont(font);

            drawText(g, as);
        }
	}

	private void drawText(Graphics g, AttributedString as) {
		int y =  this.getBounds().height - 15;
		int x =  (shell.getFullScreen()) ? 25 : 15;

		if (shell.getFullScreen()) {
		AffineTransform at = new AffineTransform();
		at.setToQuadrantRotation(3, x,y);
		((Graphics2D)g).setTransform(at);
		}

		g.drawString(as.getIterator(), x, y);
	}

	public Orientation getOrientation() {
		return orientation != orientationDefault ? orientation : null;
	}

	public int getScaleFactor() {
		return this.scaleIdx;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintCanvas(this, g);
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}


}

