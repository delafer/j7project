/**
 *
 */
package org.delafer.xanderView.file.entry;

import java.io.IOException;

import net.j7.commons.io.FileUtils;

import org.delafer.xanderView.file.entry.Encryptor.DecData;

/**
 * @author tavrovsa
 *
 */
public class ImageEnc<E> extends ImageAbstract<E> {

	ImageAbstract<E> si;
	Buf buf;

	public ImageEnc(ImageAbstract<E> si) {
		super();
		this.si = si;
		this.parent = si.parent;
		this.crc = si.crc;
		this.identifier = si.identifier;
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
				Encryptor.encrypt(new DecData(si.rawData(identifier, size).get(), si.name()))
		);
		return buf;
	}

	@Override
	protected String lastEntryId(ImageAbstract<E> ia) {
		return si.lastEntryId(si);
	}

}
