package org.delafer.xanderView.file.entry;

import java.util.HashMap;
import java.util.Map;

import net.j7.commons.base.Equals;
import net.j7.commons.io.FileUtils;
import net.j7.commons.strings.StringUtils;

import org.delafer.xanderView.common.ImageSize;
import org.delafer.xanderView.interfaces.IAbstractReader;
import org.delafer.xanderView.interfaces.IImageEntry;

public abstract class ImageEntry<E> implements IImageEntry<E> {



	public enum ImageType{JPEG, BMP, PNG, UNKNOWN};

	public String name;
	public long size;
	public ImageSize imageSize;
	public Long crc;
	public ImageType imageType;

	protected IAbstractReader parent;

	static Map<String, ImageType> types;

	static {
		types = new HashMap<String, ImageEntry.ImageType>();
		types.put("jpg", ImageType.JPEG);
		types.put("jpeg", ImageType.JPEG);
		types.put("jpe", ImageType.JPEG);
		types.put("jfif", ImageType.JPEG);
		types.put("jif", ImageType.JPEG);
		types.put("jfi", ImageType.JPEG);
//		types.put("", ImageType.JPEG);
		types.put("bmp", ImageType.BMP);
		types.put("rle", ImageType.BMP);
		types.put("dib", ImageType.BMP);
		types.put("png", ImageType.PNG);
	}


	public static ImageType getType(String name) {
		String ext = FileUtils.getExtension(name);
//		if (StringUtils.empty(ext)) ext = "jpg";
		ext = ext.trim().toLowerCase();
		ImageType tmp = types.get(ext);

		return tmp != null ? tmp : ImageType.UNKNOWN;
	}

	public ImageEntry() {
	}


	public Long CRC() {
		return crc;
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

//	public static void main(String[] args) {
//		System.out.println(ImageEntry.shorten("1234567890ab"));
//	}

	public String shortName() {
		return shorten(name());
	}

	public long size() {
		return size;
	}

	public ImageSize getImageSize() {
		return imageSize;
	}


	@Override
	public int hashCode() {
//		int result =  ((name == null) ? 0 : name.hashCode());
		int result = (int) (size ^ (size >>> 32));

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


	public ImageType getImageType() {
		return imageType;
	}

	@Override
	public String toString() {
		return String.format("ImageEntry [name=%s, imageType=%s, size=%s, crc=%s]", name, imageType, size, crc);
	}


}
