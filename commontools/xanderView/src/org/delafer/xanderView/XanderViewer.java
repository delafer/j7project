package org.delafer.xanderView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import javax.swing.JPanel;

import org.delafer.xanderView.file.FilePointer;
import org.delafer.xanderView.file.ImageFinder;
import org.delafer.xanderView.scale.ScaleFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public class XanderViewer {
	
	private Display display;
	private Shell shell;
	private Composite cmpEmbedded;
	private ImagePanel panel;
	private static FilePointer pointer;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "L:\\best5.CCC\\CURRENT\\picu\\089.jpg";
		String[] files = ImageFinder.getImages(path);
		pointer = new FilePointer(files, path);
		
		XanderViewer app = new XanderViewer();
		
		app.open();
		
	}
	
	protected void loadImage(String location) {
		try {
			System.out.println(location);
			TJScalingFactor sf =  new TJScalingFactor(1, 1);
			long l1 = System.currentTimeMillis();
			byte[] bytes = XFileReader.readNIO(location);
			long l2 = System.currentTimeMillis();
			System.out.println(">"+(l2-l1));
			TJDecompressor tjd = new TJDecompressor(bytes);
			int width = sf.getScaled(tjd.getWidth());
			int height = sf.getScaled(tjd.getHeight());

			BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
			BufferedImage res = ScaleFactory.resize(img, 1680,1050);
			panel.image = res;
			panel.updateUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void open() {
		// Create a window and set its title.
		display = new Display();
		display.addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
//				System.out.println("---------");
//				System.out.println(e.character);
//				System.out.println(e.keyCode);
//				System.out.println(e.button);
//				System.out.println(">"+e.type);
//				System.out.println("---------");
				switch (e.keyCode) {
				case SWT.ESC:
					shell.close();
					System.exit(0);
					break;
				case SWT.ARROW_LEFT:
				case SWT.ARROW_UP:
				case SWT.BS:
				case SWT.PAGE_UP:
					XanderViewer.this.loadImage(pointer.prev());
					break;
				case SWT.ARROW_RIGHT:
				case SWT.ARROW_DOWN:
				case SWT.SPACE:
				case SWT.PAGE_DOWN:
					XanderViewer.this.loadImage(pointer.next());
					break;
				case SWT.CR:
					shell.setFullScreen(!shell.getFullScreen());
					try {
						Thread.currentThread().sleep(50);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.doit = false;
					break;
				default:
					System.out.println(e.keyCode);
					break;
				}
				
			}
		});
		
		shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP | SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND | SWT.APPLICATION_MODAL);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        shell.setMaximized (true);
        shell.setFullScreen(true);

		cmpEmbedded = new Composite(shell, SWT.EMBEDDED  | SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		java.awt.Frame frm = SWT_AWT.new_Frame( cmpEmbedded );
		cmpEmbedded.setLayout(null);

        panel = new ImagePanel (null );
    	frm.add(panel);
    	panel.setBackground(Color.BLACK);
    	
    	shell.open();
    	
    	loadImage(pointer.current());
		
		runGlobalEventLoop();
	}
	
	private void runGlobalEventLoop()
	{
		while (!shell.isDisposed ()) 
			if (!display.readAndDispatch ()) display.sleep ();
		display.dispose ();
	}

	Menu createMenuBar() {
		// Menu bar.
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		createFileMenu(menuBar);
		return menuBar;
	}

	void createFileMenu(Menu menuBar) {
		// File menu
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(fileMenu);

		item = new MenuItem(fileMenu, SWT.NULL);
		item.setText("&Open");
		item.setAccelerator(SWT.CTRL + 'O');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//openFile();
			}
		});

		item = new MenuItem(fileMenu, SWT.NULL);
		item.setText("Open &URL");
		item.setAccelerator(SWT.CTRL + 'U');
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//openURL();
			}
		});

		new MenuItem(fileMenu, SWT.SEPARATOR);

		// File -> Exit
		item = new MenuItem(fileMenu, SWT.NULL);
		item.setText("Exit");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});

		// Test menu
		item = new MenuItem(menuBar, SWT.CASCADE);

	}
	
	class ImagePanel extends JPanel {
	    Image image;

	    public ImagePanel(Image image) {
	        this.image = image;
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);  // Paint background

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
	
	
}
