package org.delafer.xanderView;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;

public class FullScreenTest extends JFrame {
public static void main(String[] args) {
   DisplayMode displayMode;
   if (args.length == 3) {
       displayMode = new DisplayMode(Integer.parseInt(args[0]),
               Integer.parseInt(args[1]),        Integer.parseInt(args[2]),
               DisplayMode.REFRESH_RATE_UNKNOWN);

   } else {
       displayMode = new DisplayMode(800, 600, 16,
               DisplayMode.REFRESH_RATE_UNKNOWN);
   }
   FullScreenTest test = new FullScreenTest();
   test.run(displayMode);
}

private static final long DEMO_TIME = 1000;

public void run(DisplayMode displayMode) {
   setBackground(Color.blue);
   setForeground(Color.white);
   setFont(new Font("Dialog", Font.PLAIN, 24));

   SimpleScreenManager screen = new SimpleScreenManager();
   try {
       screen.setFullScreen(displayMode, this);
       try {
           Thread.sleep(DEMO_TIME);
       } catch (Exception e) {
       }

   } finally {
       screen.restoreScreen();
   }
}

public void paint(Graphics g) {

   g.drawString("Hello World!", 20, 50);
}
}