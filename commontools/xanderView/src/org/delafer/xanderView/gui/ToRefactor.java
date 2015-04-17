package org.delafer.xanderView.gui;

import java.awt.image.BufferedImage;

import net.j7.commons.utils.Metrics;

import org.delafer.xanderView.XFileReader;
import org.delafer.xanderView.orientation.CommonRotator;
import org.delafer.xanderView.orientation.OrientationCommons.Orientation;
import org.delafer.xanderView.orientation.Rotator2D;
import org.delafer.xanderView.scale.ScaleFactory;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public class ToRefactor {

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
		    BufferedImage res1 = ScaleFactory.resize(img, width/2,height/2);
		    m.measure("Scale1 ");
//			ImageResizer is = new ImageResizer();
//			BufferedImage res2 = is.resize(img, 1920, 1200);
//			m.measure("Scale2 ");
			CommonRotator ir = new Rotator2D();


			BufferedImage res = ir.rotate(res1, Orientation.RotatedRight);
			m.measure("Flip ");

			panel.image = res;
			panel.updateUI();
			m.measure("Draw ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
