package org.delafer.xanderView.file.readers;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.delafer.xanderView.comparator.BasicFileComparator;
import org.delafer.xanderView.comparator.ComparatorFactory;
import org.delafer.xanderView.file.ContentChangeWatcher;
import org.delafer.xanderView.file.entry.ImageFS;
import org.delafer.xanderView.file.entry.ImageAbstract;
import org.delafer.xanderView.file.entry.ImageAbstract.ImageType;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.IAbstractReader;

import com.sun.nio.file.ExtendedWatchEventModifier;

import net.j7.commons.base.Equals;
import net.j7.commons.io.AbstractFileProcessor;
import net.j7.commons.io.AbstractFileProcessor.FileInfo;
import net.j7.commons.io.AbstractFileProcessor.Recurse;
import net.j7.commons.io.FileUtils;

public class FileReader implements IAbstractReader {

	ContentChangeWatcher watcher;
	private WatchService watchService;
	private File sourceFile;
	private Recurse mode;
	private LinkedList<SevenZipReader> subReaders;

	public FileReader(File sourceFile, Recurse mode) {
		super();
		this.sourceFile = sourceFile;
		this.mode = mode;
		this.subReaders = new LinkedList<>();
	}

	public void read(final List<ImageAbstract<?>> entries) {
		try {

			AbstractFileProcessor scanner = new AbstractFileProcessor(getContainerPath()) {

				public boolean accept(FileInfo fileInfo, boolean isArc, String ext) {
					if (isArc && Recurse.Recursiv.equals(FileReader.this.mode)) return true;
					ImageType imageType = ImageAbstract.getTypeByExt(ext);
					if (ImageType.UNKNOWN.equals(imageType)) return false;
					if (ImageType.ENCRYPTED.equals(imageType) && !ApplConfiguration.instance().hasPwd()) return false;
					return true;
				}

				@Override
				public void processFile(File file, FileInfo fileInfo) throws Exception {
					String ext = fileInfo.getExtension();
					boolean isArc = ArcTypes.isArcType(ext);
					if (!accept(fileInfo, isArc, ext)) return;
					if (isArc) {
						if (Recurse.Recursiv.equals(FileReader.this.mode)) {
							SevenZipReader szr = new SevenZipReader(file);
							szr.read(entries);
							FileReader.this.subReaders.add(szr);
						}
					} else {
						ImageAbstract<?> entry = ImageFS.getInstance(FileReader.this, fileInfo.getNameWithPath(), fileInfo.getFileName(), fileInfo.getFileSize());
						entries.add(entry);
					}
				}

				public void onFinish() {
					// TODO Auto-generated method stub

				}
			};
			scanner.setMode(mode);
			scanner.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close() {
		try {
			watchService.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			subReaders.stream().parallel().forEach(SevenZipReader::close);
		}
	}

	@Override
	public void register(ContentChangeWatcher watcher) {
		this.watcher = watcher;

	}

	@Override
	public void initialize() {

		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					Path path = Paths.get(getContainerPath());
					watchService = path.getFileSystem().newWatchService();

					path.register(watchService,new Kind[] {ENTRY_CREATE,ENTRY_DELETE}, ExtendedWatchEventModifier.FILE_TREE);
					WatchKey watchKey;
					boolean notDone = true;
					while(notDone){
						try{
							watchKey = watchService.poll(1,TimeUnit.SECONDS);
							List<WatchEvent<?>> events =watchKey != null ?  watchKey.pollEvents() : Collections.EMPTY_LIST;
							for(WatchEvent<?> event : events){
								Kind<?> kind = event.kind();
			                    if (kind == StandardWatchEventKinds.OVERFLOW) {
			                        continue;
			                    }
//								Path watchedPath = (Path) watchKey.watchable();
								Path target = (Path)event.context();
								Path fullpath = path.resolve(target);
								if (null != watcher) { watcher.onEvent(asEnum(kind), fullpath.toString());}
							}
							if(watchKey != null && !watchKey.reset()){
								notDone = false;
								break;
							}
						}catch(InterruptedException e){
							Thread.currentThread().interrupt();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
		t.setDaemon(true);
		t.start();



	}

	private FileEvent asEnum(Kind<?> kind) {
		if (StandardWatchEventKinds.ENTRY_CREATE == kind) return FileEvent.Create;
		if (StandardWatchEventKinds.ENTRY_DELETE == kind) return FileEvent.Delete;
		if (StandardWatchEventKinds.ENTRY_MODIFY == kind) return FileEvent.Modify;
		return FileEvent.Other;
	}

//	public static void main(String[] args) {
//		FileReader r = new FileReader();
//		r.initialize("C:\\work\\cc\\");
//	}
	
	@SuppressWarnings("unchecked")
	public ImageAbstract<?> getEntryByIdentifier(Object id) throws IOException {
		String filePath = (String) id;



		FileInfo fileInfo = new FileInfo(new File(filePath));
		ImageAbstract<?> entry = ImageFS.getInstance(this, fileInfo.getNameWithPath(), fileInfo.getFileName(), fileInfo.getFileSize());

		return entry;
	}

	@Override
	public Comparator<ImageAbstract<?>> getComparator() {
		return ComparatorFactory.getComparator();
	}

	@Override
	public String getContainerPath() {
		File aFile = sourceFile;
		if (aFile.isFile()) {
			aFile = aFile.getParentFile();
		}

		return aFile.isDirectory() && aFile.exists() ? FileUtils.extractFullPathName(aFile) : "";
	}

	@Override
	public Object getSingleEntry() {
		return sourceFile.isFile() ? FileUtils.extractFullPathName(sourceFile) : null ;
	}

//	public static void main(String[] args) {
//		FileReader r = new FileReader();
//		System.out.println("x"+r.getContainerPath(new File("C:\\work\\workspaces\\abovo\\abovo.7z")));
//	}

}
