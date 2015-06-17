package org.delafer.xanderView.file;

import net.j7.commons.utils.RandomUtil;

import org.delafer.xanderView.file.entry.ImageEntry;
import org.delafer.xanderView.general.State;
import org.delafer.xanderView.gui.SplashWindow;
import org.delafer.xanderView.gui.helpers.MultiShell;

public class CommonContainerExt extends CommonContainer{

	private boolean randomMode;
	int startAt;

	public CommonContainerExt(String locationArg) {
		super(locationArg);
		randomMode = false;
		startAt = this.currentIndex();
//		ImageEntry e = this.getCurrent();
//		System.out.println(e);
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
			ImageEntry<?> next =  super.getNext();
			checkStartReached();
			return next;
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
			ImageEntry<?> prev =  super.getPrevious();
			checkStartReached();
			return prev;
		}
		int skip = RandomUtil.getRandomInt(1, size()-1);
		ImageEntry<?> ret = null;
		for (int j = skip; j > 0; j--) {
			ret = super.getPrevious();
		}
		return ret;
	}

	private void checkStartReached() {
		if (startAt == currentIndex()) {
//			SoundBeep.beep();
			new SplashWindow(MultiShell.shell.active(), State.Special1);
		}
	}

	public void switchRandomAccess() {
		this.randomMode = !randomMode;
	}

	public boolean isRandomMode() {
		return randomMode;
	}


}