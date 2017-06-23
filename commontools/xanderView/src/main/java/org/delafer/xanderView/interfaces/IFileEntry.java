package org.delafer.xanderView.interfaces;

public interface IFileEntry<E> {


	public E getIdentifier();

	public Long CRC();

	public String name();

	public long size();

	public byte[] content();

}
