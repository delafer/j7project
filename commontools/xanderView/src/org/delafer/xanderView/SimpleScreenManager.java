package org.delafer.xanderView;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;

public class SimpleScreenManager {

private GraphicsDevice device;

public SimpleScreenManager() {
   GraphicsEnvironment environment = GraphicsEnvironment
           .getLocalGraphicsEnvironment();
   device = environment.getDefaultScreenDevice();
}

public void setFullScreen(DisplayMode displayMode, JFrame window) {
   window.setUndecorated(true);
   window.setResizable(false);

   device.setFullScreenWindow(window);

   if (displayMode != null && device.isDisplayChangeSupported()) {

       try {
           device.setDisplayMode(displayMode);
       } catch (Exception e) {

       }

   }
}

public Window getFullScreenWindow() {
   return device.getFullScreenWindow();
}

public void restoreScreen() {
   Window window = device.getFullScreenWindow();
   if (window != null) {
       window.dispose();
   }
   device.setFullScreenWindow(null);
}
}
