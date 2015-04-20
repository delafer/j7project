package org.delafer.xanderView.scale;

import java.awt.image.BufferedImage;

import org.delafer.xanderView.gui.config.ApplConfiguration;

import no.nixx.opencl.ImageResizer;


public class ScaleFactory implements IResizer {


	public static final int SCALER_AWT_2D_FAST = 1;
	public static final int SCALER_OPENCL = 2;

	/**
	 * Gets the single instance of ResourcesDR.
	 * @return single instance of ResourcesDR
	 */
	public static ScaleFactory instance() {
		return Holder.INSTANCE;
	}

	IResizer scaler;

	private ScaleFactory() {
		ApplConfiguration cfg = ApplConfiguration.instance();
		scaler = getInstanceByType(cfg.getInt(ApplConfiguration.SCALER_FAST));
		System.out.println("Used scaler: "+scaler);
	}


	private static IResizer getInstanceByType(int type) {
		switch (type) {
		case SCALER_OPENCL:
			return new ImageResizer();
		case SCALER_AWT_2D_FAST:
		default:
			return new ResizerFastAwt2D();
		}
	}


	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient ScaleFactory INSTANCE = new ScaleFactory();
	}


	public BufferedImage resize(BufferedImage sourceImage, int outputWidth, int outputHeight) {
		return scaler.resize(sourceImage, outputWidth, outputHeight);
	}
}
