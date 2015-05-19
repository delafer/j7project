package org.delafer.xanderView;


import org.delafer.xanderView.general.State;
import org.delafer.xanderView.sound.SoundBeep;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class SplashWindow {

   private int splashPos = 0;

   private final int SPLASH_MAX = 5;

   public SplashWindow(Shell parent, State state)
   {

       final Shell splash = new Shell(parent, SWT.ON_TOP | SWT.APPLICATION_MODAL);
       Display display  = Display.getCurrent();

//       splash.setLayout(new FillLayout());
       Rectangle shellBounds = parent.getBounds();

       int width = shellBounds.width / 10;
       if (width<100) width = 100;

       int height = shellBounds.height / 10;
       if (height<100) height = 100;

       splash.setSize(width, height);

       Color color = display.getSystemColor(getColorConstant(state));
       splash.setBackground(color);
	    // Move the dialog to the center of the top level shell.

	    org.eclipse.swt.graphics.Point dialogSize = splash.getSize();

	    splash.setLocation(
	      shellBounds.x + (shellBounds.width - dialogSize.x) / 2,
	      shellBounds.y + (shellBounds.height - dialogSize.y) / 2);

//	       splash.pack();
	       splash.layout(true);
	       splash.open();
	       SoundBeep.beep();
       display.asyncExec(new Runnable()
       {
           public void run()
           {

               for(splashPos = 0; splashPos < SPLASH_MAX; splashPos++)
               {
                   try {

                       Thread.sleep(40);
                   }
                   catch(InterruptedException e) {

                       e.printStackTrace();
                   }
               }
               //ApplicationLauncher.reportWindow.initWindow();
               splash.close();
           }
      });

       while(splashPos != SPLASH_MAX)
       {
           if(!display.readAndDispatch())
           {
               display.sleep();
           }
       }
   }

	private int getColorConstant(State state) {
	       switch (state) {
	       case Success:
	    	   return SWT.COLOR_GREEN;
	       case Error:
	    	   return SWT.COLOR_RED;
	       case Ignore:
	       default:
	    	   return SWT.COLOR_BLUE;
	       }
	}
}
