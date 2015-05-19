package org.delafer.xanderView.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JPanel;

import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.orientation.CommonRotator;
import org.delafer.xanderView.orientation.OrientationCommons;
import org.delafer.xanderView.orientation.OrientationCommons.Action;
import org.delafer.xanderView.orientation.OrientationCommons.Orientation;
import org.delafer.xanderView.orientation.RotatorCPU;
import org.delafer.xanderView.scale.ScaleFactory;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 9162619010168531038L;
	BufferedImage imageSource;
	BufferedImage drawImage;
	String text;
	Orientation ccwNew;
	LazyUpdater updater;
	private final static transient Font font = new Font("MS Reference Sans Serif", Font.BOLD, 20);

    public ImagePanel() {
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setIgnoreRepaint(true);
        this.setOpaque(true);
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
//    	return new ImageSize((int)getSize().getWidth(), (int)getSize().getHeight());
    }

    private ImageSize getImageSize(boolean swapXY) {
    	ImageSize is = new ImageSize(imageSource.getWidth(null), imageSource.getHeight(null));
    	if (swapXY) is.swapXY();
    	return is;
    }


    public void preRenderImage() {
    	if (null == imageSource) return ;
    	boolean swapXY = Orientation.Original != ccwNew && ccwNew.swapXY();
    	ImageSize imgSize = getImageSize(swapXY);
    	ImageSize cnvSize = getCanvasImageSize();
    	if (!imgSize.equals(cnvSize)) {
    		System.out.println("resising"+cnvSize+" "+imgSize);
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
    	Graphics g = getGraphics();
    	paint(g);
    }



//    @Override
//    public void repaint() {
//    	System.out.println("repaint");
//    	super.repaint();
//    }
//
//    @Override
//	public void updateUI() {
//    	System.out.println("updateUI");
//		super.updateUI();
//	}
//
//
//
//	@Override
//	public void update(Graphics g) {
//		System.out.println("update(Graphics g)");
//		super.update(g);
//	}
//
//
//
//	@Override
//	public void paint(Graphics g) {
//		System.out.println("paint(Graphics g)");
//		super.paint(g);
//	}
//
//
//
//	@Override
//	public void paintImmediately(Rectangle r) {
//		System.out.println("paintImmediately(Rectangle r)");
//		super.paintImmediately(r);
//	}
//
//
//
//	@Override
//	public void paintComponents(Graphics g) {
//		System.out.println("paintComponents(Graphics g)");
//		super.paintComponents(g);
//	}
//
//
//
//	@Override
//	public void paintAll(Graphics g) {
//		System.out.println("paintAll(Graphics g)");
//		super.paintAll(g);
//	}



	public void paintComponent(Graphics g) {
//		System.out.println("paintComponent(Graphics g)");
        super.paintComponent(g);  // Paint background
        paintCanvas(this, g);

    }



	public static void paintCanvas(ImagePanel panel, Graphics g) {

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
            g.drawString(as.getIterator(), 12, g.getClipBounds().height - 15);
        }
	}


//	private static class LazyUpdater extends Thread {
//
//		volatile long start;
//		final static long interval = 800;
//		private transient ImagePanel panel;
//		private transient Graphics g;
//
//
//		/**
//		 *
//		 */
//		public LazyUpdater(ImagePanel panel, Graphics gr) {
//			super("");
//			this.panel = panel;
//			this.g = gr;
//			this.setDaemon(true);
//			this.setPriority((Thread.MIN_PRIORITY + Thread.NORM_PRIORITY)>>1);
//		}
//
//		public synchronized void update(Graphics g) {
//
//			if (!this.isAlive()) this.start();
//
//			this.start = System.currentTimeMillis();
//
//			this.g = g;
//		}
//
//
//
//		/* (non-Javadoc)
//		 * @see java.lang.Thread#run()
//		 */
//		public void run() {
//			while (System.currentTimeMillis() - start < interval) {
//				try {
//					Thread.sleep(5);
//				} catch (InterruptedException e) {}
//			}
//			drawIt();
//		}
//
//		/**
//		 *
//		 */
//		private final void drawIt() {
//
//	        if (panel.drawImage!=null) {
//	        	Rectangle dim = g.getClipBounds();
//	        	int x = (dim.width - panel.drawImage.getWidth(null)) / 2;
//	        	int y  = (dim.height - panel.drawImage.getHeight(null)) / 2;
//	        	g.drawImage(panel.drawImage, x, y, null);
//	        }
//
//	        if (!StringUtils.isEmpty(panel.text)) {
//
//	        	AttributedString as = new AttributedString(panel.text);
//
//	            as.addAttribute(TextAttribute.FONT,font);
//	            as.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
//
////	            g.setFont(font);
//	            g.drawString(as.getIterator(), 12, g.getClipBounds().height - 15);
//	        }
//	        System.out.println("done");
//		}
//
//	}

}

