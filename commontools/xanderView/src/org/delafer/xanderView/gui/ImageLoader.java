package org.delafer.xanderView.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.j7.commons.strings.Args;
import net.j7.commons.utils.Metrics;

import org.delafer.xanderView.file.CommonContainer;
import org.delafer.xanderView.file.entry.ImageEntry;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public abstract class ImageLoader {

	public abstract Dimension getSize();


	protected void loadImage(CommonContainer container, ImageEntry<?> entry, ImageCanvas panel) {
		try {
			if (entry == null) return ;
			Metrics m = Metrics.start();
			System.out.println(entry.getImageType());
			String info = Args.fill("%1 [%2/%3]", entry.name(),""+container.currentIndex(),""+container.size());
			loadImage(entry, info, panel);
			m.measure("IO Read ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void loadImage(ImageEntry<?> entry, String text, ImageCanvas panel) {
		try {
			Metrics m = Metrics.start();
			BufferedImage img = null;
			switch (entry.getImageType()) {
			case JPEG:
				img = loadJpegImage(entry.content());
				break;
			case BMP:
			case PNG:
			default:
				img = loadCommonImage(entry.content());
				break;
			}

			m.measure("ImageDecode ");

			panel.setImage(img, text, null);
			panel.showImage();
			m.measure("Draw ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private BufferedImage loadCommonImage(byte[] content) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(content));
	}


	protected BufferedImage loadJpegImage(byte[] bytes) throws Exception {

			Metrics m = Metrics.start();
			TJScalingFactor sf =  new TJScalingFactor(1, 1);
			TJDecompressor tjd = new TJDecompressor(bytes);
			int width = sf.getScaled(tjd.getWidth());
			int height = sf.getScaled(tjd.getHeight());

			BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_3BYTE_BGR, 0);
//			BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
			m.measure(">>>decoding time jpeg");
			return img;
	}

}
