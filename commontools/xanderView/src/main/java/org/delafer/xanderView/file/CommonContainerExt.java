package org.delafer.xanderView.file;

import net.j7.commons.utils.RandomUtil;

import org.delafer.xanderView.file.entry.ImageAbstract;
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
//		ImageAbstract e = this.getCurrent();
//		System.out.println(e);
	}

	@Override
	public ImageAbstract<?> getPrevious10() {
		if (randomMode)
			return this.getPrevious();
		else
			return super.getPrevious10();
	}

	@Override
	public ImageAbstract<?> getNext10() {
		if (randomMode)
			return this.getNext();
		else
			return super.getNext10();
	}

	@Override
	public ImageAbstract<?> getNext() {
		if (!randomMode) {
			ImageAbstract<?> next =  super.getNext();
			checkStartReached();
			return next;
		}
		int skip = RandomUtil.getRandomInt(1, size()-1);
//		System.out.println(skip);
		ImageAbstract<?> ret = null;
		for (int j = skip; j > 0; j--) {
			ret = super.getNext();
		}
		return ret;
	}


	@Override
	public ImageAbstract<?> getPrevious() {
		if (!randomMode) {
			ImageAbstract<?> prev =  super.getPrevious();
			checkStartReached();
			return prev;
		}
		int skip = RandomUtil.getRandomInt(1, size()-1);
		ImageAbstract<?> ret = null;
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
