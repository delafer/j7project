package org.delafer.xanderView.gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.j7.commons.strings.Args;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.file.CommonContainer;
import org.delafer.xanderView.file.CopyService;
import org.delafer.xanderView.file.entry.ImageAbstract;
import org.delafer.xanderView.gui.config.OrientationStore;
import org.libjpegturbo.turbojpeg.TJ;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJScalingFactor;

public abstract class ImageLoader {

	public abstract Dimension getSize();
	public OrientationStore orientator = OrientationStore.instance();

	protected void loadImage(CommonContainer container, ImageAbstract<?> entry, ImageCanvas panel) {
		try {
			if (entry == null) return ;
			String info = Args.fill("%1 [%2/%3]", entry.shortName(),String.valueOf(container.currentIndex()),String.valueOf(container.size()));

			loadImage(entry, info, panel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void loadImage(ImageAbstract<?> entry, String text, ImageCanvas panel) {
		try {
			BufferedImage img = null;
			//TODO CRY
			switch (entry.getImageType()) {
			case JPEG:
				img = loadJpegImage(entry.content().getArray(true));
				break;
			case BMP:
			case PNG:
			default:
				img = loadCommonImage(entry.content().getArray(true));
				break;
			}
			if (img == null) return ;
//			GammaFilter filter = new GammaFilter(0.4f);
//			img = filter.filter(img, null);

			if (CopyService.exists(entry)) text += "*";

			if (panel.setImage(img, text, orientator.getImageData(entry.CRC()))) {
				panel.showImage();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private BufferedImage loadCommonImage(byte[] content) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(content));
	}

	public abstract ImageSize displaySize();


	protected int getDenom(ImageSize si) {
		ImageSize sm = displaySize();
		float k1 = min(si.width, si.height, true) / min(sm.width, sm.height, true);
		float k2 = min(si.width, si.height, false) / min(sm.width, sm.height, false);
		float k = k1 < k2 ? k1 : k2;
		if (k <= 1f) return 1;

		if (k >= 7.9f) return 8;
		if (k >= 3.95f) return 4;
		if (k >= 2f) return 2;
		return 1;
	}
	private float min(int a, int b, boolean min) {
		return (float) ( a < b ?  (min ? a : b) : (min ? b : a) );
	}


	protected BufferedImage loadJpegImage(byte[] bytes) throws Exception {
		TJDecompressor tjd = null;
			try {
				tjd = new TJDecompressor(bytes);
				ImageSize size = new ImageSize(tjd.getWidth(), tjd.getHeight());
				TJScalingFactor sf =  new TJScalingFactor(1, getDenom(size));
				int width = sf.getScaled(size.width);
				int height = sf.getScaled(size.height);
				int flags = bytes.length >= 1750000 ? TJ.FLAG_FASTUPSAMPLE | TJ.FLAG_FASTDCT  : 0;
				BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_3BYTE_BGR, flags);
//				BufferedImage img = tjd.decompress(width, height, BufferedImage.TYPE_INT_RGB, 0);
				return img;
			} catch (Exception e) {
				return loadCommonImage(bytes);
			} finally {
				if (null != tjd) tjd.close();
			}


	}

}
