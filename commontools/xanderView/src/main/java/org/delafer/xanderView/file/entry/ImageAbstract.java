package org.delafer.xanderView.file.entry;

import java.io.IOException;
import java.nio.ByteBuffer;

import net.j7.commons.base.Equals;
import net.j7.commons.io.FileUtils;
import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.gui.config.ApplInstance;
import org.delafer.xanderView.hash.Hasher;
import org.delafer.xanderView.interfaces.IAbstractReader;
import org.delafer.xanderView.interfaces.IImageEntry;

public abstract class ImageAbstract<E> implements IImageEntry<E> {

	public enum ImageType{JPEG, BMP, PNG, ENCRYPTED, UNKNOWN};

	protected String name;

	protected long size;
	protected Long crc;

	protected ImageSize imageSize;
	protected ImageType imageType;

	protected IAbstractReader parent;

	protected E identifier;

	public static ImageType getType(String name) {
		String ext = FileUtils.getExtension(name);
		ext = ext.trim().toLowerCase();
		ImageType tmp = types.get(ext);

		return tmp != null ? tmp : ImageType.UNKNOWN;
	}

	public Long CRC() {
		return crc;
	}

	/**
	 * @param crc the crc to set
	 */
	public void setCRC(Long crc) {
		this.crc = crc;
	}


	public String name() {
		return name;
	}

	protected static String shorten(String name) {
		if (StringUtils.isEmpty(name)) return name;
		if (name.length() <= 50) return name;
		StringBuilder sb = new StringBuilder();
		sb.append(name.subSequence(0, 24));
		sb.append("~");
		sb.append(name.subSequence(name.length()-24, name.length()));
		return sb.toString();
	}

	public String shortName() {
		return shorten(name());
	}

	public long size() {
		return size;
	}

	public E getIdentifier() {
		return identifier;
	}

	public ImageSize getImageSize() {
		return imageSize;
	}


	public ImageType getImageType() {
		if (null == this.imageType) {
			this.imageType = getType(name);
		}
		return imageType;
	}

	protected void calcCRC(ByteBuffer ret) {
		if (null == crc) {
			this.crc = Hasher.hash().calc(ret, this.size);
		}
	}

	protected abstract Buf rawData(E identifier, int size) throws IOException;

	protected abstract String lastEntryId(ImageAbstract<E> ia);

	public Buf content() {
		ApplInstance.lastEntry = lastEntryId(this);
		try {
			final Buf ret = rawData(identifier, X);
			calcCRC(ret.get());
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int hashCode() {
		int result = (int) (size ^ (size >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof ImageAbstract))  return false;
		final ImageAbstract<?> o = (ImageAbstract<?>) obj;

		if (size != o.size) return false;
		if (!Equals.equal(this.name, o.name)) return false;

		return true;
	}
	public String toString() {
		return String.format("ImageAbstract [name=%s, imageType=%s, size=%s, crc=%s]", name, imageType, size, crc);
	}

}
