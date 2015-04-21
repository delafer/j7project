package org.delafer.xanderView.interfaces;

import java.util.List;

public interface IAbstractReader {


	public void read(String location, List<ImageEntry<?>> entries);

	public void close();

}
