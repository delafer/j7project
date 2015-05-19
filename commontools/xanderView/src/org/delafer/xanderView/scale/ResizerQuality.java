package org.delafer.xanderView.scale;

import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

public class ResizerQuality implements IResizer{


	public BufferedImage resize(BufferedImage image, int width, int height) {
		System.out.println("RESIZER "+this.getClass().getSimpleName());
		ResampleOp  resampleOp = new ResampleOp (width,height);
		resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		BufferedImage rescaledTomato = resampleOp.filter(image, null);

		return rescaledTomato;


	}

}
