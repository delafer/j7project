package org.delafer.xanderView.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import net.j7.commons.strings.Args;
import net.j7.commons.utils.Metrics;

import org.delafer.xanderView.XFileReader;
import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.interfaces.CommonContainer;
import org.delafer.xanderView.interfaces.ImageEntry;
import org.delafer.xanderView.orientation.OrientationCommons;
import org.delafer.xanderView.scale.ScaleFactory;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public abstract class ToRefactor {

	public abstract Dimension getSize();
//
//	protected void loadImage(String location, ImagePanel panel) {
//		try {
//			Metrics m = Metrics.start();
//			byte[] bytes = XFileReader.readNIO(location);
//			loadImage(bytes, panel);
//			m.measure("IO Read ");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	protected void loadImage(CommonContainer container, ImageEntry<?> entry, ImagePanel panel) {
		try {
			if (entry == null) return ;
			System.out.println(entry.name()+" "+entry.getIdentifier());
			Metrics m = Metrics.start();
			byte[] bytes = entry.content();
			String info = Args.fill("%1 [%2/%3]", entry.name(),""+container.currentIndex(),""+container.size() );
			loadImage(bytes, info, panel);
			m.measure("IO Read ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	protected void loadImage(byte[] bytes, String text, ImagePanel panel) {
		try {
			Metrics m = Metrics.start();

			TJScalingFactor sf =  new TJScalingFactor(1, 1);
			TJDecompressor tjd = new TJDecompressor(bytes);
			int width = sf.getScaled(tjd.getWidth());
			int height = sf.getScaled(tjd.getHeight());
			BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
			m.measure("JPEGDecode ");
			Dimension dim = getSize();

			ImageSize size = OrientationCommons.getNewSize(img.getWidth(), img.getHeight(), dim.width, dim.height);

		    BufferedImage res = ScaleFactory.instance().resize(img, size.width, size.height);
		    m.measure("Scale1 ");
			m.measure("Flip ");

			panel.showImage(res, text);
			m.measure("Draw ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
