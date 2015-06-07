package org.delafer.xanderView.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JPanel;

import net.j7.commons.base.CommonUtils;
import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.gui.helpers.LazyUpdater;
import org.delafer.xanderView.gui.helpers.MultiShell;
import org.delafer.xanderView.orientation.CommonRotator;
import org.delafer.xanderView.orientation.OrientationCommons;
import org.delafer.xanderView.orientation.OrientationCommons.Action;
import org.delafer.xanderView.orientation.OrientationCommons.Orientation;
import org.delafer.xanderView.orientation.Rotator2D;
import org.delafer.xanderView.scale.ScaleFactory;

public class ImageCanvas extends JPanel {

	private static final long serialVersionUID = 9162619010168531038L;
	BufferedImage imageSource;
	BufferedImage drawImage;
	String text;

	Orientation orientation;
	Orientation orientationDefault;

	LazyUpdater updater;
	MultiShell shell;
	private final static transient Font font = new Font("MS Reference Sans Serif", Font.BOLD, 20);

    public ImageCanvas(MultiShell shell) {
    	this.shell = shell;
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
        this.setOpaque(true);
    }

    public void setImage(BufferedImage image, String text, Orientation direction) {
   	 	this.imageSource = image;
   	 	this.text = text;
   	 	this.orientationDefault = getDirectionDefault();
   	 	this.orientation = CommonUtils.nvl(direction, orientationDefault);
   	 	preRenderImage();
    }

    private Orientation getDirectionDefault() {
    	ImageSize imgSize = getImageSize(false);
    	ImageSize cnvSize = getCanvasImageSize();

    	boolean rotate = (imgSize.width() > imgSize.height()) != (cnvSize.width() > cnvSize.height());
    	rotate = rotate && (max(imgSize.width(), imgSize.height()) / min(imgSize.width(), imgSize.height()) >= 1.1f);

    	return !rotate ? Orientation.Original : Orientation.RotatedLeft;
	}



	private float max(int arg1, int arg2) {
		return (float)(arg1 > arg2 ? arg1 : arg2);
	}

	private float min(int arg1, int arg2) {
		return (float)(arg1 < arg2 ? arg1 : arg2);
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


    public void preRenderImage() {
    	if (null == imageSource) return ;
    	boolean swapXY = Orientation.Original != orientation && orientation.swapXY();
    	ImageSize imgSize = getImageSize(swapXY);
    	ImageSize cnvSize = getCanvasImageSize();

    	if (!imgSize.equals(cnvSize)) {
    		ImageSize size = OrientationCommons.getNewSize(imgSize.width(), imgSize.height(), cnvSize.width(), cnvSize.height());
    		if (size.empty()) return ;
    		drawImage = ScaleFactory.instance(imgSize).resize(imageSource, !swapXY ? size.width() : size.height(), !swapXY ? size.height() : size.width());
    	} else {
    		drawImage = imageSource;
    	}

    	if (!Orientation.Original.equals(orientation)) {
    		CommonRotator ir = new Rotator2D();
        	drawImage = ir.rotate(drawImage, orientation);
    	}

    }


    public void showImage() {
//    	Graphics g = getGraphics();
//    	paint(g);
    	this.getParent().repaint();
    }


	public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Paint background
        paintCanvas(this, g);

    }

	public void paintCanvas(ImageCanvas panel, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        if (panel.drawImage!=null) {
        	Rectangle dim = g.getClipBounds();
        	int x = (dim.width - panel.drawImage.getWidth(null)) / 2;
        	int y  = (dim.height - panel.drawImage.getHeight(null)) / 2;

        	g.drawImage(panel.drawImage, x, y, null);
        }

        if (!StringUtils.isEmpty(panel.text)) {

        	AttributedString as = new AttributedString(panel.text);

            as.addAttribute(TextAttribute.FONT,font);
            as.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
            g.setFont(font);

            drawText(g, as);
        }
	}

	private void drawText(Graphics g, AttributedString as) {
		int y = g.getClipBounds().height - 15;
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


}

