package org.libjpegturbo.turbojpeg.test;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.delafer.xanderView.XFileReader;
import org.delafer.xanderView.scale.ResizerFastAwt2D;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public class TestAll {
	public static void main(String[] args) throws Exception {
		TJScalingFactor sf =  new TJScalingFactor(1, 4);
		long l1 = System.currentTimeMillis();
		byte[] bytes = XFileReader.readNIO("L:\\best5.CCC\\CURRENT\\sam.posl\\YannaViewOfBarcelona_111408_056xxxl.jpg");
		long l2 = System.currentTimeMillis();
		System.out.println(">"+(l2-l1));
        TJDecompressor tjd = new TJDecompressor(bytes);
        int width = sf.getScaled(tjd.getWidth());
        int height = sf.getScaled(tjd.getHeight());

        BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);

   	  SWTDraw draw = new SWTDraw();

//	  draw.drawImage(ResizerFastAwt2D.resize(img, 1680,1050));
	}
}
