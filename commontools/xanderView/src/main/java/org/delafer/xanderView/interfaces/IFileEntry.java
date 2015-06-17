package org.delafer.xanderView.interfaces;

public interface IFileEntry<E> {


	public E getIdentifier();

	public long CRC();

	public String name();

	public long size();

	public byte[] content();

}
