package org.delafer.xanderView.file;

import net.j7.commons.utils.RandomUtil;

import org.delafer.xanderView.file.entry.ImageEntry;

public class CommonContainerExt extends CommonContainer{

	private boolean randomMode;

	public CommonContainerExt(String locationArg) {
		super(locationArg);
		randomMode = false;
	}

	@Override
	public ImageEntry<?> getPrevious10() {
		if (randomMode)
			return this.getPrevious();
		else
			return super.getPrevious10();
	}

	@Override
	public ImageEntry<?> getNext10() {
		if (randomMode)
			return this.getNext();
		else
			return super.getNext10();
	}

	@Override
	public ImageEntry<?> getNext() {
		if (!randomMode) {
			return super.getNext();
		}
		int skip = RandomUtil.getRandomInt(1, size()-1);
//		System.out.println(skip);
		ImageEntry<?> ret = null;
		for (int j = skip; j > 0; j--) {
			ret = super.getNext();
		}
		return ret;
	}


	@Override
	public ImageEntry<?> getPrevious() {
		if (!randomMode) {
			return super.getPrevious();
		}
		int skip = RandomUtil.getRandomInt(1, size()-1);
		ImageEntry<?> ret = null;
		for (int j = skip; j > 0; j--) {
			ret = super.getPrevious();
		}
		return ret;
	}

	public void switchRandomAccess() {
		this.randomMode = !randomMode;
	}

	public boolean isRandomMode() {
		return randomMode;
	}


}
