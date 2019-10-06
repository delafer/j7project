package org.delafer.xanderView.file;

import net.j7.commons.collections.SortedLinkedList;
import net.j7.commons.utils.RandomUtil;

import org.delafer.xanderView.comparator.RandomComparator;
import org.delafer.xanderView.file.entry.ImageAbstract;
import org.delafer.xanderView.general.State;
import org.delafer.xanderView.gui.SplashWindow;
import org.delafer.xanderView.gui.helpers.MultiShell;

import java.io.IOException;
import java.util.Comparator;

public class CommonContainerExt extends CommonContainer{

	private boolean randomMode;
	private boolean onlyExisting;
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
		if (onlyExisting)
			return this.getPrevious();
		else
			return super.getPrevious10();
	}

	@Override
	public ImageAbstract<?> getNext10() {
		if (onlyExisting)
			return this.getNext();
		else
			return super.getNext10();
	}

	@Override
	public ImageAbstract<?> getNext() {
		if (!onlyExisting) {
			ImageAbstract<?> next =  super.getNext();
			checkStartReached();
			return next;
		}
		int s = this.size();
		ImageAbstract<?> ret = null;
		do {
			ret = super.getNext();
		} while (((s--)>0) && CopyService.exists(ret));
		return ret;
	}


	@Override
	public ImageAbstract<?> getPrevious() {
		if (!onlyExisting) {
			ImageAbstract<?> prev =  super.getPrevious();
			checkStartReached();
			return prev;
		}
		int s = this.size();
		ImageAbstract<?> ret = null;
		do {
			ret = super.getPrevious();
		} while (((s--)>0) && CopyService.exists(ret));

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
		if (!randomMode) {
			initImagesForComparator(null);
		} else {
			initImagesForComparator(new RandomComparator());
		}
	}

	public void switchOnlyExisting() {
		this.onlyExisting = !onlyExisting;
	}

	public boolean isOnlyExisting() {
		return onlyExisting;
	}

	public boolean isRandomMode() {
		return randomMode;
	}


}
