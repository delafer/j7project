package org.delafer.xanderView.scale.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.j7.commons.strings.Args;
import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.scale.ResizeJavaHQ;
import org.delafer.xanderView.scale.Scalr;

import com.mortennobel.imagescaling.*;

public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	//downscale - HermiteFilter (618), BiCubicHighFreqResponse (883) , Lanczos3Filter (1053)
	//upscale - Hermite, BiCubicHighFreqResponse



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


			BufferedImage bi = ImageIO.read(new File("e:\\good.bmp"));
			double koeff = 1.6371d;
			int wn = (int)Math.round((double)bi.getWidth() * koeff);
			int hn = (int)Math.round((double)bi.getHeight() * koeff);
			int iter = (int)Math.round(15  / (koeff * koeff));


			int i = 0;
			for (ResizerBase next : filters) {
				i++;
				BufferedImage res = null;
				long t1 = System.currentTimeMillis();
				for (int j = 0; j < iter; j++) {
					res = next.resize(bi, wn, hn);
				}
				long t2 = System.currentTimeMillis();
				System.out.println(i);
				String ext = "PNG";
				ImageIO.write(res, ext, new File(Args.fill("e:/out/result_%1(%2).%3", StringUtils.fillString(""+(t2-t1),5,'0'), next.name(), ext)));
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

}
