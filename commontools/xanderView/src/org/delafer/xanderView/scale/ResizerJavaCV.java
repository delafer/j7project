package org.delafer.xanderView.scale;

import java.awt.image.*;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;



public class ResizerJavaCV implements IResizer {


	private ResizerJavaCV() {
		super();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient ResizerJavaCV INSTANCE = new ResizerJavaCV();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public static final ResizerJavaCV instance() {
		return Holder.INSTANCE;
	}

	// Convert image to Mat
	public Mat matify(BufferedImage im) {
	    // Convert INT to BYTE
	    //im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
	    // Convert bufferedimage to byte array
		Mat image = null;
		DataBuffer db= im.getRaster().getDataBuffer();
		System.out.println(db.getDataType());
		if (db instanceof DataBufferByte) {
			byte[] pixels = ((DataBufferByte) db).getData();

		    // Create a Matrix the same size of image
		    image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
		    // Fill Matrix with image values
		    image.put(0, 0, pixels);
		} else {
			int[] pixels = ((DataBufferInt)db).getData();
			image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_32SC3);
		    // Fill Matrix with image values
		    image.put(0, 0, pixels);
		}

	    return image;

	}

	public BufferedImage MatToBufferedImage(Mat matBGR){
	      long startTime = System.nanoTime();
	      int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;
	      byte[] sourcePixels = new byte[width * height * channels];
	      matBGR.get(0, 0, sourcePixels);
	      // create new image and get reference to backing data
	      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	      final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	      System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
	      long endTime = System.nanoTime();
	      System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));
	      return image;
	}

	@Override
	public BufferedImage resize(BufferedImage input, int width, int height) {
		Mat resize = new Mat();
		Size size = new Size(width, height);
		Imgproc.resize(matify(input), resize, size, 0f, 0f, Imgproc.INTER_CUBIC);
        BufferedImage resized = MatToBufferedImage(resize);
        return resized;
	}

	@Override
	public String name() {
		return this.getClass().getSimpleName();
	}

}
