/**
 *
 */
package org.delafer.xanderView.file.entry;

import java.io.IOException;

import org.delafer.xanderView.file.entry.Encryptor.DecData;

/**
 * @author tavrovsa
 *
 */
public class ImageDec<E> extends ImageAbstract<E> {

	ImageAbstract<E> si;
	Buf buf;

	protected ImageDec(ImageAbstract<E> si) {
		super();
		this.si = si;
		this.parent = si.parent;
		this.crc = si.crc;
		init(si);
	}

	/**
	 * @param si
	 */
	private void init(ImageAbstract<E> si) {
		try {
			Buf buff =  si.rawData(si.getIdentifier(), X);
			DecData dd = Encryptor.decrypt(buff.get());
			this.name = dd.name();
			this.size = dd.get().remaining() - (dd.name().length()+3);
			this.buf = new Buf(dd.get());
			buff.close();

		} catch (IOException e) {
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
		return buf;
	}

	@Override
	protected String lastEntryId(ImageAbstract<E> ia) {
		return si.lastEntryId(this);
	}

}
