package org.delafer.xanderView.scale.test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.mortennobel.imagescaling.*;

public class MortenResizer extends ResizerBase {

	public static final ResizerBase instance = new MortenResizer(-1);

	static List<ResampleFilter> filters = new ArrayList<ResampleFilter>(9);

	static {
		filters.add(ResampleFilters.getBellFilter());
		filters.add(ResampleFilters.getBiCubicFilter());
		filters.add(ResampleFilters.getBiCubicHighFreqResponse());
		filters.add(ResampleFilters.getBoxFilter());
		filters.add(ResampleFilters.getBSplineFilter());
		filters.add(ResampleFilters.getHermiteFilter());
		filters.add(ResampleFilters.getLanczos3Filter());
		filters.add(ResampleFilters.getMitchellFilter());
		filters.add(ResampleFilters.getTriangleFilter());
	}

	private ResampleFilter filter;

	public MortenResizer as(int filterI) {
		return new MortenResizer(filterI);
	}

	public MortenResizer(int filterI) {
		if (filterI < 0) return ;
		this.filter = filters.get(filterI);
	}

	@Override
	public BufferedImage resize(BufferedImage src, int w, int h) {
		ResampleOp  resampleOp = new ResampleOp (w,h);
		resampleOp.setFilter(filter);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
		BufferedImage rescaledTomato = resampleOp.filter(src, null);
		return rescaledTomato;
	}

	@Override
	public String name() {
		return filter.getClass().getSimpleName();
	}

	public int getMaxFilters() {
		return 9;
	}

}
