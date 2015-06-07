package org.delafer.xanderView.scale;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;



public class ResizerOpenCV extends ResizerBase {


	@Override
	public int getMaxFilters() {
		return 5;
	}

	private ResizerOpenCV() {
		super();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient ResizerOpenCV INSTANCE = new ResizerOpenCV();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public static final ResizerOpenCV instance() {
		return Holder.INSTANCE;
	}

	int current;

	private ResizerOpenCV(int current) {
		this.current = current;
	}

	private static int types[] = new int[] {Imgproc.INTER_NEAREST, Imgproc.INTER_AREA, Imgproc.INTER_LINEAR, Imgproc.INTER_CUBIC, Imgproc.INTER_LANCZOS4 };
	private static String names[] = new String[] {"CV_NEAREST", "CV_AREA", "CV_LINEAR", "CV_CUBIC", "CV_LANCZOS4" };

	// Convert image to Mat
	public Mat matify(BufferedImage im) {
	    // Convert INT to BYTE
	    //im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
	    // Convert bufferedimage to byte array
		Mat image = null;
		DataBuffer db= im.getRaster().getDataBuffer();
//		System.out.println(db.getDataType());
		if (db instanceof DataBufferByte) {
			byte[] pixels = ((DataBufferByte) db).getData();

		    // Create a Matrix the same size of image
		    image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
		    // Fill Matrix with image values
		    image.put(0, 0, pixels);
		} else
		if (db instanceof DataBufferInt) {
			int[] pixels = ((DataBufferInt)db).getData();
			image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_32SC3);
		    // Fill Matrix with image values
		    image.put(0, 0, pixels);
		}

	    return image;

	}

	public BufferedImage MatToBufferedImage(Mat matBGR){
//	      long startTime = System.nanoTime();
	      int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;
	      byte[] sourcePixels = new byte[width * height * channels];
	      matBGR.get(0, 0, sourcePixels);
	      // create new image and get reference to backing data
	      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	      final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	      System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
//	      long endTime = System.nanoTime();
//	      System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));
	      return image;
	}

	@Override
	public BufferedImage resize(BufferedImage input, int width, int height) {
		Mat resize = new Mat();
		Size size = new Size(width, height);
		Mat mt = matify(input);

		if (mt == null) {
			//workaround
			return new ResizerNobel(4).resize(input, width, height);
		}

		Imgproc.resize(mt, resize, size, 0f, 0f, types[current]);
        BufferedImage resized = MatToBufferedImage(resize);
        return resized;
	}

	@Override
	public String name() {
		return names[current];
	}

	@Override
	public ResizerBase as(int filterI) {
		return new ResizerOpenCV(filterI);
	}

	@Override
	public int current() {
		return current;
	}

	public static void main(String[] args) {
		int max = ResizerOpenCV.instance().getMaxFilters();
		for (int i = 0; i < max; i++) {
			ResizerBase rs = new ResizerOpenCV(i);
			System.out.println("["+i+"]="+rs.name());
		}

	}

}
