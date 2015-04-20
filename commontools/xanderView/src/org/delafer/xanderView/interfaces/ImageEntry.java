package org.delafer.xanderView.interfaces;

import org.delafer.xanderView.common.ImageSize;

public abstract class ImageEntry<E> implements IImageEntry<E> {


	protected String name;
	protected String fullpath;
	protected long size;
	protected ImageSize imageSize;

	public ImageEntry() {
	}


	public long CRC() {
		return 0l;
	}

	public String name() {
		return name;
	}

	public long size() {
		return size;
	}

	public ImageSize getImageSize() {
		return imageSize;
	}


}
