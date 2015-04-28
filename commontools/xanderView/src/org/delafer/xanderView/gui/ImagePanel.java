package org.delafer.xanderView.gui;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JPanel;

import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.orientation.*;
import org.delafer.xanderView.orientation.OrientationCommons.Action;
import org.delafer.xanderView.orientation.OrientationCommons.Orientation;
import org.delafer.xanderView.scale.ScaleFactory;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 9162619010168531038L;
	BufferedImage imageSource;
	BufferedImage drawImage;
	String text;
	Orientation ccwNew;

	private final static transient Font font = new Font("MS Reference Sans Serif", Font.BOLD, 20);

    public ImagePanel() {
        this.setBackground(Color.BLACK);
//        this.setDoubleBuffered(true);
//        this.setIgnoreRepaint(false);
    }



    public void setImage(BufferedImage image, String text, Orientation direction) {
   	 	this.imageSource = image;
   	 	this.text = text;
   	 	this.ccwNew = getDirection(direction);
   	 	preRenderImage();
    }

    private Orientation getDirection(Orientation direction) {
		if (direction != null) return direction;
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



	public void rotate(Action action) {
    	ccwNew = ccwNew.newState(action);
    	preRenderImage();
    }

    private ImageSize getCanvasImageSize() {
    	return new ImageSize(getWidth(), getHeight());
    }

    private ImageSize getImageSize(boolean swapXY) {
    	ImageSize is = new ImageSize(imageSource.getWidth(null), imageSource.getHeight(null));
    	if (swapXY) is.swapXY();
    	return is;
    }


    private void preRenderImage() {

    	boolean swapXY = Orientation.Original != ccwNew && ccwNew.swapXY();
    	ImageSize imgSize = getImageSize(swapXY);
    	ImageSize cnvSize = getCanvasImageSize();

    	if (!imgSize.equals(cnvSize)) {
    		ImageSize size = OrientationCommons.getNewSize(imgSize.width(), imgSize.height(), cnvSize.width(), cnvSize.height());
    		drawImage = ScaleFactory.instance().resize(imageSource, !swapXY ? size.width() : size.height(), !swapXY ? size.height() : size.width());
    	} else {
    		drawImage = imageSource;
    	}

    	if (!Orientation.Original.equals(ccwNew)) {
    		CommonRotator ir = new RotatorCPU();
        	drawImage = ir.rotate(drawImage, ccwNew);
    	}

    }


    public void showImage() {
//    	 this.invalidate(); this.doLayout(); this.layout(); this.revalidate();
    	 this.updateUI();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (drawImage!=null) {
        	Rectangle dim = g.getClipBounds();
        	int x = (dim.width - drawImage.getWidth(null)) / 2;
        	int y  = (dim.height - drawImage.getHeight(null)) / 2;

        	g.drawImage(drawImage, x, y, null);
        }

        if (!StringUtils.isEmpty(text)) {

        	AttributedString as = new AttributedString(text);

            as.addAttribute(TextAttribute.FONT,font);
            as.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);

//            g.setFont(font);
            g.drawString(as.getIterator(), 12, g.getClipBounds().height - 15);
        }
    }

}

