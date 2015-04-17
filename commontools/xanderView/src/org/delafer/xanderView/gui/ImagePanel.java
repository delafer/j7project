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
    }

    public void showImage(Image image) {
    	 this.image = image;
    	 this.updateUI();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Paint background
//        System.out.println(image.getWidth(null));
        // Draw image at its natural size first.
        if (image!=null)
        g.drawImage(image, 0, 0, null); //85x62 image

        AttributedString as = new AttributedString("An AttributedString holds text and related "+
                            "attribute information.");
        as.addAttribute(TextAttribute.FONT, new Font("Courier New", Font.BOLD, 36));
        as.addAttribute(TextAttribute.FOREGROUND, Color.YELLOW);


        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(as.getIterator(), 10, 20);

    }
}

