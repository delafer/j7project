package org.delafer.xanderView.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import net.j7.commons.utils.Metrics;
import no.nixx.opencl.ImageResizer;

import org.delafer.xanderView.XFileReader;
import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.orientation.CommonRotator;
import org.delafer.xanderView.orientation.OrientationCommons;
import org.delafer.xanderView.orientation.OrientationCommons.Orientation;
import org.delafer.xanderView.orientation.Rotator2D;
import org.delafer.xanderView.scale.ResizerFastAwt2D;
import org.delafer.xanderView.scale.ScaleFactory;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public abstract class ToRefactor {

	public abstract Dimension getSize();

	protected void loadImage(String location, ImagePanel panel) {
		try {
			//ver1 1243,1255,1269,1249,1254,1254,1254
			//ver 1224, 1225,1223,1233
			//vs is faster
			System.out.println(location);
			Metrics m = Metrics.start();
			TJScalingFactor sf =  new TJScalingFactor(1, 1);
			byte[] bytes = XFileReader.readNIO(location);
			m.measure("IO Read ");

			TJDecompressor tjd = new TJDecompressor(bytes);
			int width = sf.getScaled(tjd.getWidth());
			int height = sf.getScaled(tjd.getHeight());
			System.out.println(width+" "+height);
			BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
			m.measure("JPEGDecode ");
			Dimension dim = getSize();

			ImageSize size = OrientationCommons.getNewSize(img.getWidth(), img.getHeight(), dim.width, dim.height);

		    BufferedImage res = ScaleFactory.instance().resize(img, size.width, size.height);
//			ImageResizer ir = new ImageResizer();
//			BufferedImage res = ir.resize(img, dim.width,dim.height);
		    m.measure("Scale1 ");
//			ImageResizer is = new ImageResizer();
//			BufferedImage res2 = is.resize(img, 1920, 1200);
//			m.measure("Scale2 ");
//			CommonRotator ir = new Rotator2D();
//			BufferedImage res = ir.rotate(res1, Orientation.RotatedRight);
			m.measure("Flip ");

			panel.image = res;
			panel.updateUI();
			m.measure("Draw ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}