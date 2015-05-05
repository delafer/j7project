package org.delafer.xanderView.interfaces;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public interface IAbstractReader {


	public enum FileEvent{Create, Delete, Modify, Other};

	public void initialize();

	public void read(List<ImageEntry<?>> entries);

	public void close();

	public void register(ContentChangeWatcher watcher);

	public <E extends ImageEntry<?>>E getEntryByIdentifier(Object id) throws IOException ;

	public Comparator<ImageEntry<?>> getComparator();

	public String getContainerPath();

	public Object getSingleEntry();
}
