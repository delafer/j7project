package org.delafer.xanderView.scale;

import java.awt.image.BufferedImage;

public class ResizerFastAwt2D implements IResizer{


	public BufferedImage resize(BufferedImage image, int width, int height) {
		return  Scalr.resize(image, Scalr.Method.BALANCED, Scalr.Mode.BEST_FIT_BOTH,
				width, height, Scalr.OP_ANTIALIAS);
	}

}
