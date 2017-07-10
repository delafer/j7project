/**
 *
 */
package org.delafer.xanderView.file.entry;

import java.io.IOException;

import net.j7.commons.io.FileUtils;

import org.delafer.xanderView.file.entry.Encryptor.DecData;
import org.delafer.xanderView.gui.config.ApplConfiguration;

/**
 * @author tavrovsa
 *
 */
public class ImageEnc<E> extends ImageAbstract<E> {

	ImageAbstract<E> si;
	Buf buf;
	
//	protected String name;
//
//	protected long size;
//	protected Long crc;
//
//	protected ImageSize imageSize;
//	protected ImageType imageType;
//
//	protected IAbstractReader parent;
//
//	protected E identifier;

	public ImageEnc(ImageAbstract<E> si) {
		this.si = si;
		this.parent = si.parent;
		this.crc = si.crc;
		this.identifier = si.identifier;
		this.size = si.size();
		this.name = EncodeName.encrypt(FileUtils.getBaseName(si.name()))+".cry";
		init(si);
	}

	/**
	 * @param si
	 */
	private void init(ImageAbstract<E> si) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/* (non-Javadoc)
	 * @see org.delafer.xanderView.file.entry.ImageAbstract#getIdentifier()
	 */
	@Override
	public E getIdentifier() {
		return si.getIdentifier();
	}


	@Override
	protected Buf rawData(E identifier, int size) throws IOException {
		Buf buf = new Buf(
				Encryptor.encrypt(new DecData(si.rawData(identifier, size).get(), si.name(), (int)si.size()))
		);
		return buf;
	}

	@Override
	public long size() {
		return si.size();
	}

	@Override
	protected String lastEntryId(ImageAbstract<E> ia) {
		return si.lastEntryId(si);
	}
	
	public boolean equals(Object obj) {
		return si.isEquals(this, obj);
	}

	public static ImageAbstract<?> getEncrypted(ImageAbstract<?> inp) {
		if (null == inp || inp instanceof ImageEnc<?>) {
			return inp;
		}
		
		if (inp instanceof ImageDec<?>) {
			ImageDec<?> id = (ImageDec<?>) inp;
			return id.getEncrypted();
		}
		
		return ApplConfiguration.instance().hasPwd() ? new ImageEnc<>(inp) : inp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImageAbstract<E> cloneObj() {
		ImageEnc<E> cloned = new ImageEnc<E>(si != null ? (ImageAbstract<E>)si.cloneObj() : null);
		cloned.buf = this.buf;
		cloned.parent = this.parent;
		cloned.crc = this.crc;
		cloned.size = this.size;
		cloned.identifier = this.identifier;
		cloned.name = this.name;
		cloned.imageType = this.imageType;
		return cloned;
	}

}
