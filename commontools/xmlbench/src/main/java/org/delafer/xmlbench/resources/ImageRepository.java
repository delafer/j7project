/*
 * Created on 29 juin 2003
 * Copyright (C) 2003, 2004, 2005, 2006 Aelitis, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * AELITIS, SAS au capital de 46,603.30 euros
 * 8 Allee Lenotre, La Grille Royale, 78600 Le Mesnil le Roi, France.
 *
 */
package org.delafer.xmlbench.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.delafer.xmlbench.config.Constants;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;

/**
 * @author Olivier
 * 
 */
public class ImageRepository {
	private static boolean NO_IMAGES = false;

	private static Display display;
	private static final HashMap<String, String> imagesToPath;
	private static final HashMap<String, Image> images;
	private static final HashMap<String, Image> registry;
	private static final String[] noCacheExtList = new String[] { ".exe" };
	private static final boolean doNotUseAWTIcon = Constants.isOSX;

	static {
		images = new HashMap<String, Image>(150);
		imagesToPath = new HashMap<String, String>(150);
		registry = new HashMap<String, Image>();
	}

	public static void loadImagesForSplashWindow(Display display) {
		ImageRepository.display = display;
	}

	public static void loadImages(Display display) {
		
		addPath("org/delafer/xmlbench/resources/icons/cpu.png", "main_icon");
		
	}

	private static void addPathOnOff(String path, String id) {
		imagesToPath.put(id, path);
		
		int idx = path.lastIndexOf(".");
		String pathOff = path.substring(0, idx) + "_off"+path.substring(idx);
		System.out.println(">"+pathOff);
		imagesToPath.put(id+".off", pathOff);
		// 2x longer
		// loadImage(display, path, id);
	}
	
	private static void addPath(String path, String id) {
		imagesToPath.put(id, path);
		// 2x longer
		// loadImage(display, path, id);
	}

	private static Image loadImage(Display display, String res, String name) {
		return loadImage(display, res, name, 255);
	}

	private static Image loadImage(Display display, String res, String name,
			int alpha) {
		return loadImage(ImageRepository.class.getClassLoader(), display, res,
				name, alpha);
	}

	static Image onlyOneImage = null;

	private static Image loadImage(ClassLoader loader, Display display,
			String res, String name, int alpha) {
		if (NO_IMAGES) {
			if (onlyOneImage == null)
				onlyOneImage = new Image(display, 1, 1);
			return onlyOneImage;
		}
		imagesToPath.put(name, res);
		Image im = getImage(name, false);
		if (null == im) {
			InputStream is = loader.getResourceAsStream(res);
			if (null != is)
				try {
					if (alpha == 255)
						im = new Image(display, is);
					else {
						ImageData icone = new ImageData(is);
						icone.alpha = alpha;
						im = new Image(display, icone);
					}
					images.put(name, im);
				} catch (SWTException e) {
					return null;
				}
			else {
				System.out.println("ImageRepository:loadImage:: Resource not found: "
								+ res);

				im = new Image(display, 1, 1);

				images.put(name, im);
			}
		}
		return im;
	}

	public static void unLoadImages() {
		Iterator iter;
		iter = images.values().iterator();
		while (iter.hasNext()) {
			Image im = (Image) iter.next();
			im.dispose();
		}

		iter = registry.values().iterator();
		while (iter.hasNext()) {
			Image im = (Image) iter.next();
			if (im != null)
				im.dispose();
		}
	}

	public static Image getImage(String name) {
		if (NO_IMAGES) {
			if (onlyOneImage == null)
				onlyOneImage = new Image(display, 1, 1);
			return onlyOneImage;
		}
		return getImage(name, true);
	}

	public static InputStream getImageAsStream(String name) {
		String path = (String) imagesToPath.get(name);

		if (path == null) {

			System.out.println("ImageRepository: Unknown image name '" + name
					+ "'");

			return null;
		}

		return ImageRepository.class.getClassLoader().getResourceAsStream(path);
	}

	private static Image getImage(String name, boolean allowLoading) {
		Image result = (Image) images.get(name);
		if (allowLoading && result == null) {
			String path = (String) imagesToPath.get(name);
			if (path != null)
				return loadImage(display, path, name);
		}
		return result;
	}

	/**
	 * Gets an image for a file associated with a given program
	 * 
	 * @param program
	 *            the Program
	 */
	public static Image getIconFromProgram(Program program) {
		Image image = null;

		try {
			image = (Image) images.get(program.toString());

			if (image == null)
				if (program != null) {

					ImageData imageData = program.getImageData();
					if (imageData != null) {
						image = new Image(null, imageData);
						images.put(program.toString(), image);
					}
				}
		} catch (Throwable e) {
			// seen exceptions thrown here, due to images.get failing in
			// Program.hashCode
			// ignore and use default icon
		}

		if (image == null)
			image = getImage("folder", true);
		return image;
	}

	/**
	 * @deprecated Does not account for custom or native folder icons
	 * @see ImageRepository#getPathIcon(String)
	 */
	public static Image getFolderImage() {
		return getImage("folder", true);
	}

	/**
	 * <p>
	 * Gets a small-sized iconic representation of the file or directory at the
	 * path
	 * </p>
	 * <p>
	 * For most platforms, the icon is a 16x16 image; weak-referencing caching
	 * is used to avoid abundant reallocation.
	 * </p>
	 * @param path Absolute path to the file or directory
	 * @return The image
	 */
	public static Image getPathIcon(final String path) {
		if (path == null)
			return null;

		try {
			final File file = new File(path);

			// workaround for unsupported platforms
			// notes:
			// Mac OS X - Do not mix AWT with SWT (possible workaround: use
			// IPC/Cocoa)

			String key;
			if (file.isDirectory()) {
				if (doNotUseAWTIcon)
					return getFolderImage();

				key = file.getPath();
			} else {
				final int lookIndex = file.getName().lastIndexOf(".");

				if (lookIndex == -1) {
					if (doNotUseAWTIcon)
						return getFolderImage();

					key = "?!blank";
				} else {
					final String ext = file.getName().substring(lookIndex);
					key = ext;

					if (doNotUseAWTIcon)
						return getIconFromProgram(Program.findProgram(ext));

					// case-insensitive file systems
					for (String element : noCacheExtList)
						if (element.equalsIgnoreCase(ext)) {
							key = file.getPath();
							break;
						}
				}
			}

			// this method mostly deals with incoming torrent files, so there's
			// less concern for
			// custom icons (unless user sets a custom icon in a later session)

			// other platforms - try sun.awt
			Image image = (Image) registry.get(key);
			if (image != null)
				return image;

			java.awt.Image awtImage = null;

			final Class sfClass = Class.forName("sun.awt.shell.ShellFolder");
			if (sfClass != null && file != null) {
				Method method = sfClass.getMethod("getShellFolder",
						new Class[] { File.class });
				if (method != null) {
					Object sfInstance = method.invoke(null,
							new Object[] { file });

					if (sfInstance != null) {
						method = sfClass.getMethod("getIcon",
								new Class[] { Boolean.TYPE });
						if (method != null)
							awtImage = (java.awt.Image) method.invoke(
									sfInstance, new Object[] { new Boolean(
											false) });
					}
				}
			}

			if (awtImage != null) {
				final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ImageIO.write((BufferedImage) awtImage, "png", outStream);
				final ByteArrayInputStream inStream = new ByteArrayInputStream(
						outStream.toByteArray());

				image = new Image(null, inStream);

				if (Constants.isWindows) {
					// recomposite to avoid artifacts - transparency mask does
					// not work
					final Image dstImage = new Image(Display.getCurrent(),
							image.getBounds().width, image.getBounds().height);
					GC gc = new GC(dstImage);
					gc.drawImage(image, 0, 0);
					gc.dispose();
					image.dispose();
					image = dstImage;
				}

				registry.put(key, image);

				return image;
			}
		} catch (Exception e) {
			// Debug.printStackTrace(e);
		}

		// Possible scenario: Method call before file creation
		final int fileSepIndex = path.lastIndexOf(File.separator);
		if (fileSepIndex == path.length() - 1)
			return getFolderImage();

		final int extIndex;
		if (fileSepIndex == -1)
			extIndex = path.indexOf('.');
		else
			extIndex = path.substring(fileSepIndex).indexOf('.');

		if (extIndex == -1)
			return getFolderImage();

		return getIconFromProgram(Program.findProgram(path.substring(extIndex)));
	}

	/**
	 * <p>
	 * Gets an image with the specified canvas size
	 * </p>
	 * <p>
	 * No scaling is performed on the original image, and a cached version will
	 * be used if found.
	 * </p>
	 * 
	 * @param name
	 *            ImageRepository image resource name
	 * @param canvasSize
	 *            Size of image
	 * @return The image
	 */
	public static Image getImageWithSize(String name, Point canvasSize) {
		String key = new StringBuffer().append(name).append('.')
				.append(canvasSize.x).append('.').append(canvasSize.y)
				.toString();

		Image newImage = (Image) images.get(key);

		if (newImage == null) {
			Image oldImage = getImage(name);

			if (oldImage == null)
				return null;

			newImage = new Image(Display.getCurrent(), canvasSize.x,
					canvasSize.y);
			GC gc = new GC(newImage);

			int x = Math.max(0, (canvasSize.x - oldImage.getBounds().width) / 2);
			int y = Math.max(0,(canvasSize.y - oldImage.getBounds().height) / 2);
			gc.drawImage(oldImage, x, y);

			gc.dispose();

			images.put(key, newImage);
		}

		return newImage;
	}

	public static void unloadImage(String name) {
		Image img = (Image) images.get(name);
		if (img != null) {
			images.remove(name);
			if (!img.isDisposed())
				img.dispose();
		}
	}

	public static void unloadPathIcon(String path) {
		String key = getKey(path);
		Image img = (Image) registry.get(key);
		if (img != null) {
			registry.remove(key);
			if (!img.isDisposed())
				img.dispose();
		}
	}

	private static String getKey(String path) {
		final File file = new File(path);

		String key;
		if (file.isDirectory())
			key = file.getPath();
		else {
			final int lookIndex = file.getName().lastIndexOf(".");

			if (lookIndex == -1)
				key = "?!blank";
			else {
				final String ext = file.getName().substring(lookIndex);
				key = ext;

				// case-insensitive file systems
				for (String element : noCacheExtList)
					if (element.equalsIgnoreCase(ext))
						key = file.getPath();
			}
		}

		return key;
	}
}