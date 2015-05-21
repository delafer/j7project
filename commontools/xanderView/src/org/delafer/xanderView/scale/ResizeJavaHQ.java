package org.delafer.xanderView.scale;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ResizeJavaHQ implements IResizer {

	public ResizeJavaHQ() {
	}

	@Override
	public BufferedImage resize(BufferedImage sourceImage, int outputWidth, int outputHeight) {

		BufferedImage result =  new BufferedImage(outputWidth,outputHeight,sourceImage.getType());
		Graphics2D resultGraphics = result.createGraphics();

		// Scale the image to the new buffer using the specified rendering hint.
		resultGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		resultGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		resultGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		resultGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		resultGraphics.drawImage(sourceImage, 0, 0, outputWidth, outputHeight, null);

		// Just to be clean, explicitly dispose our temporary graphics object
		resultGraphics.dispose();
		return result;
	}

}
