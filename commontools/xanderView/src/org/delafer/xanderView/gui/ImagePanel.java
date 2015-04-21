package org.delafer.xanderView.gui;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 9162619010168531038L;
	Image image;

    public ImagePanel() {
        this.setBackground(Color.BLACK);
//        this.setDoubleBuffered(true);
//        this.setIgnoreRepaint(false);
    }

    public void showImage(Image image) {
    	System.out.println("redraw it");
    	 this.image = image;
//    	 this.invalidate();
//    	 this.doLayout();
//    	 this.layout();
//    	 this.revalidate();
    	 this.updateUI();
    }

    public void paintComponent(Graphics g) {
    	System.out.println("redraw it2 " +image.getWidth(null)+" <> "+image.getHeight(null));
        super.paintComponent(g);  // Paint background
        // Draw image at its natural size first.
        if (image!=null) {
        	Rectangle dim = g.getClipBounds();
        	int x = (dim.width - image.getWidth(null)) / 2;
        	int y  = (dim.height - image.getHeight(null)) / 2;


        	g.drawImage(image, x, y, null);
        }


//        AttributedString as = new AttributedString("An AttributedString holds text and related attribute information.");
//
//        as.addAttribute(TextAttribute.FONT, new Font("Courier New", Font.BOLD, 36));
//        as.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);
//
//
//        g.setFont(new Font("Arial", Font.PLAIN, 20));
//        g.drawString(as.getIterator(), 10, 20);

    }
}

