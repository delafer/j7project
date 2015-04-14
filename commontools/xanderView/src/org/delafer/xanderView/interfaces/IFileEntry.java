package org.delafer.xanderView.interfaces;

public interface IFileEntry {


	public long fastCRC();

	public byte[] CRC();

	public String name();

	public byte[] data();

	public long size();

}
