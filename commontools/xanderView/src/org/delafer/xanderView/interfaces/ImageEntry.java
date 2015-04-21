package org.delafer.xanderView.interfaces;

import net.j7.commons.base.Equals;

import org.delafer.xanderView.common.ImageSize;

public abstract class ImageEntry<E> implements IImageEntry<E> {


	protected String name;
	protected long size;
	protected ImageSize imageSize;
	protected long crc;

	public ImageEntry() {
	}


	public long CRC() {
		return crc;
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


	@Override
	public int hashCode() {
		int result =  ((name == null) ? 0 : name.hashCode());
//		result = 31 * result + (int) (size ^ (size >>> 32));

		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		final ImageEntry<?> o = (ImageEntry<?>) obj;
		if (!Equals.equal(this.name, o.name)) return false;
		if (this.size != 0l && o.size != 0l && !Equals.equal(this.size, o.size)) return false;
		return true;
	}


}
