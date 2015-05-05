package org.delafer.xanderView.interfaces;

import java.io.IOException;

import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;

public interface ContentChangeWatcher {
	void onEvent(FileEvent type, Object identifier) throws IOException;
}