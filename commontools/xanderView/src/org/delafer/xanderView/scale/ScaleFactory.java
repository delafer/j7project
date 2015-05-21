package org.delafer.xanderView.scale;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.gui.config.ApplConfiguration;


public class ScaleFactory  {


	public static final int SCALER_AWT_2D_FAST = 1;
	public static final int SCALER_OPENCL = 2;
	public static final int SCALER_QUALITY = 3;


	private ScalerHelper scaler;

	/**
	 * Gets the single instance of ResourcesDR.
	 * @return single instance of ResourcesDR
	 */
	public static ScaleFactory instance() {
		return Holder.INSTANCE;
	}

	private ScaleFactory() {
		ApplConfiguration cfg = ApplConfiguration.instance();
		scaler = new ScalerHelper(cfg.get(ApplConfiguration.SCALER));
//		System.out.println("Used scaler: "+scaler);
	}


	private IResizer getInstanceByType(int type) {
		if (true) return new ResizeJavaScaledInstance();
		switch (type) {
		case SCALER_QUALITY:
			//return new ResizerAdvQuality();
		case SCALER_OPENCL:
			return ResizeOpenCL.instance();
		case SCALER_AWT_2D_FAST:
		default:
//			return new ResizerFastAwt2D();
			return ResizeOpenCL.instance();
		}
	}

	public IResizer getInstanceBySize(ImageSize size) {
		return getInstanceByType(scaler.getTypeBySize(size));
	}


	public static IResizer instance(ImageSize size) {
		ScaleFactory factory = ScaleFactory.instance();

		IResizer r =  factory.getInstanceBySize(size);
		System.out.println("Resizer used: "+r.getClass().getSimpleName());
		return r;
	}


	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient ScaleFactory INSTANCE = new ScaleFactory();
	}


//	public BufferedImage resize(BufferedImage sourceImage, int outputWidth, int outputHeight) {
//		return scaler.resize(sourceImage, outputWidth, outputHeight);
//	}
}
