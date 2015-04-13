package org.delafer.xanderView.scale;

import java.awt.image.BufferedImage;

public class ScaleFactory {
	
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		return  Scalr.resize(image, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_HEIGHT,
				width, height, Scalr.OP_ANTIALIAS);
	}
	
}
