package org.delafer.xanderView.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import net.j7.commons.base.Equals;
import net.j7.commons.io.FilePath;
import net.j7.commons.io.FileUtils;

import org.delafer.xanderView.common.SimpleNameIncrementer;
import org.delafer.xanderView.file.entry.*;
import org.delafer.xanderView.file.entry.ImageEntry.ImageType;
import org.delafer.xanderView.file.readers.FileReader;
import org.delafer.xanderView.general.State;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;

public class CopyService {


	/**
	 * Lazy-loaded Singleton, by Bill Pugh *.
	 */
	private static final class Holder {
		/** The Constant INSTANCE. */
		private final static transient CopyService INSTANCE = new CopyService();
	}

	/**
	 * Gets the single instance of ResourcesDR.
	 *
	 * @return single instance of ResourcesDR
	 */
	public final static CopyService instance() {
		return Holder.INSTANCE;
	}


	private CopyService() {
		init();
	}


    private transient Object lockObj = new Object();

	Set<FileDirEntry> images;
//	ListIterator<FileImageEntry> iterator;
	File pathFile;
	String pathTxt;
	FileReader reader;
	Boolean initialized = null;
	private Queue<Runnable> queue;

	public int size() {
		return images.size();
	}

	public void init() {
		String locationArg = ApplConfiguration.instance().get(ApplConfiguration.CFG_COPY_DIR);
		String location = FilePath.as().dir(locationArg).div(true).forceExists().build();
		queue = new LinkedList<Runnable>();
		initializeByPath(location);

	}

	private void initializeByPath(String locationArg) {
		this.pathTxt = locationArg;
		this.pathFile = new File(locationArg);
		this.reader = new FileReader(pathFile);
		this.images = new HashSet<FileDirEntry>();
		initializeInternal();

	}

	private FileDirEntry findByName(String name) {
		for (FileDirEntry next : images) {
			if (Equals.equal(next.getIdentifier(), name)) return next;
		}
		return null;

	}


	public void checkInitialized() {
		try {
			synchronized (lockObj) {
				while (null == initialized) lockObj.wait();
			}
		} catch (InterruptedException e) {}
	}

	public State copy(ImageEntry<?> entry) {
			Thread worker = new Thread("copyService") {

				@Override
				public void run(){
					checkInitialized();
			        while ( true ) {
			            try {
			                Runnable work = null;
			                synchronized ( queue ) {
			                    while ( queue.isEmpty() ) queue.wait();

			                    // Get the next work item off of the queue
			                    work = queue.remove();
			                }

			                // Process the work item
			                work.run();
			            }
			            catch ( InterruptedException ie ) {
			                break;  // Terminate
			            }
			        }
			    }

			};
			worker.setDaemon(true);
			worker.setPriority(Thread.NORM_PRIORITY-2);
			worker.start();

			synchronized (queue) {
				queue.add(new Runnable() {

					@Override
					public void run() {
						CopyService.this.copySync(entry);
					}
				});
				 queue.notify();
			}

			return State.Ignore;
	}


	public State copySync(ImageEntry<?> entry) {
		try {

			FileDirEntry fde = new FileDirEntry(null,null,0);
			fde.crc = entry.CRC();

			if (images.contains(fde)) {
//				System.out.println("Already exists!!!");
//				for (FileDirEntry next : images) {
//					System.out.println(next.identifier+" "+next.name+" "+next.crc);
//				}
				return State.Ignore;
			}

			String fileName = entry.name;
			String baseName = FileUtils.getBaseName(fileName);
			String ext = FileUtils.getExtension(fileName);

			SimpleNameIncrementer nameInc = SimpleNameIncrementer.instance(baseName);

			File aFile = null;
			while ((aFile = getFile(ext, nameInc)).exists()) {
				nameInc.increment();
			}

			nioTransferCopy(entry, aFile);


			FileDirEntry newEntry = new FileDirEntry(FileUtils.extractFullPathName(aFile), fileName, entry.size, entry.crc);

			FileDirEntry removeIt = CopyService.this.findByName(newEntry.getIdentifier());
			images.remove(removeIt);

			addImage(newEntry);
			return State.Success;

		} catch (Throwable e) {
			return State.Error;
		}


	}

	private void addImage(FileDirEntry add) {
		if (add == null) return ;
		if (images.size()>0) {
			if (images.contains(add)) images.remove(add);
		}
		images.add(add);


	}

	private static void close(Closeable closable) {
	    if (closable != null) {
	        try {
	            closable.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

    private static void nioTransferCopy(ImageEntry<?> entry, File target) throws IOException {
        FileChannel out = null;

        try {
            out = new FileOutputStream(target).getChannel();
            out.write(ByteBuffer.wrap(entry.content()));
            out.force(true);
        } finally {
            close(out);
        }
    }


	private File getFile(String ext, SimpleNameIncrementer nameInc) {
		StringBuilder sb = new StringBuilder();
		sb.append(pathTxt);
		sb.append(nameInc.build());
		sb.append('.');
		sb.append(ext);
		File aFile = new File(sb.toString());
		return aFile;
	}

	private void initializeInternal() {

		try {
			reader.register(new ContentChangeWatcher() {

				@Override
				public void onEvent(FileEvent type, Object id)throws IOException {
					switch (type) {
					case Create:
						String fileName = id.toString();
						File aFile = new File(fileName);
						if (aFile.isDirectory()) return ;

						ImageType imgType = ImageEntry.getType(fileName);
						if (ImageType.UNKNOWN.equals(imgType)) return ;

						FileImageEntry entryNew = reader.getEntryByIdentifier(id);
						addImage(FileDirEntry.as(entryNew));
						break;
					case Delete:
						FileImageEntry entryDel = reader.getEntryByIdentifier(id);
						FileDirEntry fde = CopyService.this.findByName(entryDel.getIdentifier());
						images.remove(fde);
						break;
					default:
						System.out.println("Other type");
					}

				}

			});


			readStructure();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure() throws Exception {
		Thread th = new Thread("idxDstDir") {

			public void run() {
				synchronized (lockObj) {

				List<ImageEntry<?>> toAdd = new ArrayList<ImageEntry<?>>();
				reader.read(toAdd);

				for (ImageEntry<?> next : toAdd) {
					images.add(FileDirEntry.as((FileImageEntry)next));
				}
				reader.initialize();

				initialized = Boolean.TRUE;
				lockObj.notify();
				}
			}

		};
		th.setDaemon(true);
		th.start();

	}

//	public void test() {
//		for (FileDirEntry fileDirEntry : images) {
//			System.out.println(fileDirEntry);
//		}
//	}


//	public void onContainerContentChange() {
//	}

	public static void main(String[] args) {
		Queue<String> a = new LinkedList<String>();
		a.add("b");
		a.add("x");
		a.add("a");
		System.out.println(a.remove());
		System.out.println(a.remove());
		System.out.println(a.remove());

	}

}
