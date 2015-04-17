package org.delafer.xanderView.interfaces;

import java.awt.Dimension;

public class ImageEntry implements IImageEntry {


	protected String name;
	protected String fullpath;
	protected long size;
	protected Dimension imageSize;

	public ImageEntry() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public long quickCRC() {

		return 0;
	}

	@Override
	public byte[] CRC() {

		return null;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public byte[] data() {

		return null;
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public Dimension getImageSize() {
		return null;
	}

	@Override
	public long uuid() {
		return 0;
	}

}
