package org.delafer.xanderView.scale;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.scale.deprecated.ResizeOpenCL;


public class ScaleFactory  {


	public static final int SCALER_AWT_2D_NEAREST 		= 1;
	public static final int SCALER_AWT_2D_BILLINEAR 	= 2;
	public static final int SCALER_AWT_2D_BICUBIC 		= 3; //skip
	public static final int SCALER_CV_LINEAR 			= 4;
	public static final int SCALER_CV_AREA 				= 5;
	public static final int SCALER_CV_BICUBIC			= 6;
	public static final int SCALER_CV_LANCZOS4			= 7;
	public static final int SCALER_NOBEL_HERMITE		= 8;
	public static final int SCALER_NOBEL_MITCHEL		= 9;
	public static final int SCALER_NOBEL_BICUBIC_HF		= 10;
	public static final int SCALER_NOBEL_LANCZOS3		= 11;




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
		switch (type) {
		case SCALER_CV_LINEAR:
			return ResizerOpenCV.instance().as(2);
		case SCALER_CV_AREA:
			return ResizerOpenCV.instance().as(1);
		case SCALER_CV_BICUBIC:
			return ResizerOpenCV.instance().as(3);
		case SCALER_CV_LANCZOS4:
			return ResizerOpenCV.instance().as(4);
		case SCALER_NOBEL_LANCZOS3:
			return new ResizerNobel(0);
		case SCALER_NOBEL_BICUBIC_HF:
			return new ResizerNobel(1);
		case SCALER_NOBEL_MITCHEL:
			return new ResizerNobel(3);
		case SCALER_NOBEL_HERMITE:
			return new ResizerNobel(4);
		case SCALER_AWT_2D_BICUBIC:
			return new ResizerJava2D(2);
		case SCALER_AWT_2D_BILLINEAR:
			return new ResizerJava2D(1);
		case SCALER_AWT_2D_NEAREST:
		default:
			return new ResizerJava2D(0);
		}
	}

	public IResizer getInstanceBySize(ImageSize size) {
		IResizer r =  getInstanceByType(scaler.getTypeBySize(size));
		return r;
	}


	public static IResizer instance(ImageSize size) {
		ScaleFactory factory = ScaleFactory.instance();
		return  factory.getInstanceBySize(size);
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
