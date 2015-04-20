package org.delafer.xanderView.common;

public final class ImageSize {

	public int width;
	public int height;

	public ImageSize() {
	}

	public ImageSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public final static ImageSize as(int width, int height) {
		return new ImageSize(width, height);
	}

	public int width() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	public int height() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	@Override
	public int hashCode() {
		int result =  width;
		return 31 * result + height;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ImageSize)) return false;

		final ImageSize o = (ImageSize) obj;
		return width == o.width && height == o.height;
	}

	@Override
	public String toString() {
		return "ImageSize [width=" + width + ", height=" + height + "]";
	}


}
