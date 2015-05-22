package org.delafer.xanderView.scale.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.j7.commons.strings.Args;
import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.file.entry.FileImageEntry;
import org.delafer.xanderView.orientation.OrientationCommons;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public class Tester {
	//HQ_NAA_dither
	public Tester() {
		// TODO Auto-generated constructor stub
	}

	//Nearest HQ AA Dither
	//Bilinear HQ AA Dither

	//downscale - HermiteFilter (618), BiCubicHighFreqResponse (883) , Lanczos3Filter (1053)
	//upscale - Hermite, BiCubicHighFreqResponse

	public static BufferedImage getImage(String name) {
		try {
			byte[] a = FileImageEntry.readNIO(name);

			TJScalingFactor sf =  new TJScalingFactor(1, 1);
			TJDecompressor tjd = new TJDecompressor(a);
			int width = sf.getScaled(tjd.getWidth());
			int height = sf.getScaled(tjd.getHeight());
			BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
			return img;
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		try {

			ResizerBase[] rs = new ResizerBase[] {
					MortenResizer.instance,
					Graph2DResizer.instance,
					ScalrResizer.instance,
					BufferedImageResizer.instance,
					CustomScalers.instance
			};

			List<ResizerBase> filters = new ArrayList<ResizerBase>();

			for (ResizerBase next : rs) {
				int max = next.getMaxFilters();
				for (int i = 0; i < max; i++) {
					ResizerBase filter = next.as(i);
					filters.add(filter);
				}
			}

			String base = "10mp";
			BufferedImage bi = getImage("e:\\"+base+".jpg");//ImageIO.read(new File("e:\\big.jpg"));


			ImageSize size = OrientationCommons.getNewSize(bi.getWidth(), bi.getHeight(), 1920, 1080);

			int wn =size.width();
			int hn = size.height();
			double koeff = 1920d / (double)bi.getWidth();
			int iter = (int)Math.round(15  / (koeff * koeff));

			int i = 0;
			for (ResizerBase next : filters) {
				i++;
				BufferedImage res = null;
				res = next.resize(bi, wn, hn);
				res = next.resize(bi, wn, hn);
				long t1 = System.currentTimeMillis();
				for (int j = 0; j < iter; j++) {
					res = next.resize(bi, wn, hn);
				}
				long t2 = System.currentTimeMillis();

				System.gc();
				Thread.currentThread().sleep(100);
				System.runFinalization();
				Runtime r = Runtime.getRuntime();
				r.gc();
				System.gc();
				System.runFinalization();
				Thread.currentThread().sleep(1000);
				System.gc();
				Thread.currentThread().sleep(1000);
				System.gc();


				System.out.println(i);

				long r1 = Math.round((((double)(t2-t1))/3.0d));

				String ext = "PNG";
				ImageIO.write(res, ext, new File(Args.fill("e:/out2/"+base+"/result_%1(%2).%3", StringUtils.fillString(""+(r1),5,'0'), next.name(), ext)));
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

}
