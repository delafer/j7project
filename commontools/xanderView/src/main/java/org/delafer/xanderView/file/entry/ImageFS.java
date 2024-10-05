package org.delafer.xanderView.file.entry;

import java.io.IOException;
import java.util.Objects;

import org.delafer.xanderView.hash.Hasher;
import org.delafer.xanderView.interfaces.IAbstractReader;

public class ImageFS extends ImageAbstract<String> {

	public static ImageAbstract<String> getInstance(String fullPath, String name, long size) {
		return getInstance(null, fullPath, name, size);
	}

	public static ImageAbstract<String> getInstance(IAbstractReader parent, String fullPath, String name, long size) {
		ImageFS fs = new ImageFS(parent, fullPath, name, size);
		if (ImageType.ENCRYPTED.equals(fs.getImageType())) {
			return new ImageDec<String>(fs);
		}
		return fs;
	}

	public ImageFS(String fullPath, String name, long size) {
		this(null, fullPath, name, size);
	}


	public ImageFS(IAbstractReader parent, String fullPath, String name, long size) {
		this.parent = parent;
		this.name = name;
		this.size = size;
		this.identifier = fullPath;
	}

	public Long CRC() {

		if (this.crc == null && null != identifier)
		try {
			calcSafeCRC(rawData(identifier, Hasher.HSIZE));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.crc;
	}
	
	@Override
	public boolean isEquals(ImageAbstract<?> thisObj, Object obj) {
		if (thisObj == obj) return true;
		if (!(obj instanceof ImageAbstract))  return false;
		final ImageAbstract<?> o = (ImageAbstract<?>) obj;

		if (thisObj.size != o.size) return false;

		final Long tcrc = thisObj.CRC(), ocrc = o.CRC();

		if (tcrc != null && ocrc != null) {
			return Objects.equals(tcrc, ocrc);
		}
		
		if (!Objects.equals(thisObj.getIdentifier(), o.getIdentifier())) return false;

		return true;		
	}

	
	public boolean equals(Object obj) {
		return isEquals(this, obj);
	}


	public Buf rawData(String identifier, int size) throws IOException {
		return HelperFS.readDataBuf(identifier, size);
	}

	@Override
	protected String lastEntryId(ImageAbstract<String> ia) {
		return ia.identifier;
	}

	@Override
	public ImageAbstract<?> cloneObj() {
		ImageFS cloned = new ImageFS(parent, identifier, name, size);
		cloned.crc = this.crc;
		cloned.imageSize = this.imageSize;
		cloned.imageType = this.imageType;
		return cloned;
	}

//	protected String lastEntryId(ImageAbstract<String> ia) {
//		return ia.identifier;
//	}


}
