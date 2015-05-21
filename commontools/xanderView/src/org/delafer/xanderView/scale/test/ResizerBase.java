package org.delafer.xanderView.scale.test;

import org.delafer.xanderView.scale.IResizer;

public abstract class ResizerBase implements IResizer {


	public int getMaxFilters() {
		return 1;
	}

	public abstract ResizerBase as(int filterI);


}
