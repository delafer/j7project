package org.delafer.xanderView.orientation;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import no.nixx.opencl.ImageRotator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Rotator2D extends CommonRotator {

	//http://mindprod.com/jgloss/affinetransform.html
	//http://www.developer.com/net/vb/article.php/626051/Java-2D-Graphics-Simple-Affine-Transforms.htm

	public Rotator2D() {
	}

	@Override
	public BufferedImage flipVertical(BufferedImage image) {
		// Flip the image vertically
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -image.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
		return image;
	}

	@Override
	public BufferedImage flipHorizontal(BufferedImage image) {
		// Flip the image horizontally
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
		return image;
	}


	private void affineOperation(AffineTransform tx, BufferedImage src, BufferedImage dst, int type) {
		if (type > 0) {
	        AffineTransformOp op = new AffineTransformOp(tx, type);
	        op.filter(src, dst);
		} else {
	        Graphics2D g = dst.createGraphics();
	        g.drawImage(src, tx, null);
	        g.dispose();
		}
	}

	@Override
	public BufferedImage rotateRight(final BufferedImage inputImage) {
		System.out.println("rr");
		int w = inputImage.getWidth();
		int h = inputImage.getHeight();
		AffineTransform tx = AffineTransform.getQuadrantRotateInstance(1, w / 2, h / 2);
        BufferedImage dest = new BufferedImage(w, h, inputImage.getType());

        int op = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
        affineOperation(tx, inputImage, dest, op);

        return dest;
	}

	@Override
	public BufferedImage rotateLeft(BufferedImage inputImage) {
		throw new NotImplementedException();
	}

	@Override
	public BufferedImage turnAbout(BufferedImage image ) {

		// Flip the image vertically and horizontally; equivalent to rotating the image 180 degrees
		AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
		tx.translate(-image.getWidth(null), -image.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image, null);
		return image;
	}

}
