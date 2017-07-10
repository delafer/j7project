/**
 *
 */
package org.delafer.xanderView.file.entry;

import java.io.IOException;

import org.delafer.xanderView.file.entry.Encryptor.DecData;
import org.delafer.xanderView.hash.Hasher;

import net.j7.commons.strings.StringUtils;

/**
 * @author tavrovsa
 *
 */
public class ImageDec<E> extends ImageAbstract<E> {
	
	
	private ImageAbstract<E> si;
	private Buf buf;
	
	private boolean initialized;

	protected ImageDec(ImageAbstract<E> si) {
		this.si = si;
		this.parent = si.parent;
		this.identifier = si.identifier;
		if (si instanceof ImageFS) {
			try {
//				System.out.println("CALCULATION SIZE: "+this.si.identifier);
				this.size = si.rawData(si.getIdentifier(), 4).get().getInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			init();
		}
		
	}

	/**
	 * @param si
	 */
	private void init() {
//		new Throwable().printStackTrace();
		if (!initialized)
		try {
//			System.out.println("INITIALIZING: "+this.si.identifier);
			Buf buff =  si.rawData(si.getIdentifier(), X);
			DecData dd = Encryptor.decrypt(buff.get());
			this.name = dd.name();
			if (this.name != null) {
				this.size = dd.size();
				this.buf = new Buf(dd.get());	
			}
			buff.close();
			
			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ImageAbstract<E> getEncrypted() {
		if (null == si) return this;
		ImageAbstract<E> ret = (ImageAbstract<E>) si.cloneObj();
		ret.size = this.size();
		ret.setCRC(this.CRC());
		return ret;
	}

	public Long CRC() {

		if (this.crc == null && null != identifier)
		try {
			calcCRC(rawData(identifier, Hasher.HSIZE).get());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this.crc;
	}



	@Override
	public String name() {
		return StringUtils.isEmpty(this.name) ? si.name : this.name;
	}

	@Override
	public String shortName() {
		init();
		return super.shortName();
	}

	@Override
	public E getIdentifier() {
		init();
		return super.getIdentifier();
	}

	@Override
	public ImageType getImageType() {
		init();
		return super.getImageType();
	}

//	@Override
//	public Buf content() {
//		init();
//		return super.content();
//	}

	@Override
	protected Buf rawData(E identifier, int size) throws IOException {
		init();
		return buf;
	}

	@Override
	protected String lastEntryId(ImageAbstract<E> ia) {
		return si.lastEntryId(si);
	}
	
	@Override
	public boolean equals(Object obj) {
		return si.isEquals(this, obj);
	}
	
	public static ImageAbstract<?> getOriginal(ImageAbstract<?> inp) {
		if (null == inp || inp instanceof ImageEnc<?>) {
			return inp;
		}
		
		if (inp instanceof ImageDec<?>) {
			ImageDec<?> id = (ImageDec<?>) inp;
			return id.getEncrypted();
		}
		
		return inp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImageAbstract<E> cloneObj() {
		ImageDec<E> cloned = new ImageDec<E>(si != null ? (ImageAbstract<E>)si.cloneObj() : null);
		this.init();
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
