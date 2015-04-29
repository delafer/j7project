package org.delafer.xanderView.interfaces;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.delafer.xanderView.interfaces.CommonContainer.ContentChangeWatcher;

public interface IAbstractReader {


	public enum FileEvent{Create, Delete, Modify, Other};

	public void initialize(String location);

	public void read(String location, List<ImageEntry<?>> entries);

	public void close();

	public void register(ContentChangeWatcher watcher);

	public <E extends ImageEntry<?>>E getEntryByIdentifier(Object id) throws IOException ;

	public Comparator<ImageEntry<?>> getComparator();
}
