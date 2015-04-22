package org.delafer.xanderView.gui;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JPanel;

import net.j7.commons.strings.StringUtils;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 9162619010168531038L;
	Image image;
	String text;

	private final static transient Font font = new Font("MS Reference Sans Serif", Font.BOLD, 20);

    public ImagePanel() {
        this.setBackground(Color.BLACK);
//        this.setDoubleBuffered(true);
//        this.setIgnoreRepaint(false);
    }

    public void showImage(Image image, String text) {
    	 this.image = image;
    	 this.text = text;
//    	 this.invalidate();
//    	 this.doLayout();
//    	 this.layout();
//    	 this.revalidate();
    	 this.updateUI();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);  // Paint background
        // Draw image at its natural size first.
        if (image!=null) {
//        	System.out.println("redraw it2 " +image.getWidth(null)+" <> "+image.getHeight(null));
        	Rectangle dim = g.getClipBounds();
        	int x = (dim.width - image.getWidth(null)) / 2;
        	int y  = (dim.height - image.getHeight(null)) / 2;


        	g.drawImage(image, x, y, null);
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

