package org.delafer.xanderView.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import net.j7.commons.streams.Streams;
import org.delafer.xanderView.common.SimpleNameIncrementer;
import org.delafer.xanderView.file.entry.Buf;
import org.delafer.xanderView.file.entry.ImageAbstract;
import org.delafer.xanderView.file.entry.ImageAbstract.ImageType;
import org.delafer.xanderView.file.entry.ImageFS;
import org.delafer.xanderView.file.readers.FileReader;
import org.delafer.xanderView.general.State;
import org.delafer.xanderView.gui.ImageCanvas;
import org.delafer.xanderView.gui.SplashWindow;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.gui.config.ApplInstance;
import org.delafer.xanderView.gui.helpers.MultiShell;
import org.delafer.xanderView.gui.helpers.UIHelpers;
import org.delafer.xanderView.interfaces.IAbstractReader.FileEvent;
import org.eclipse.swt.widgets.Display;

import net.j7.commons.base.Equals;
import net.j7.commons.io.AbstractFileProcessor.Recurse;
import net.j7.commons.io.FilePath;
import net.j7.commons.io.FileUtils;

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

	Set<ImageAbstract<?>> images;
//	ListIterator<ImageFS> iterator;
	File pathFile;
	String pathTxt;
	FileReader reader;
	Boolean initialized = null;
	private Queue<Runnable> queue;

	public int size() {
		return images.size();
	}



	public static boolean exists(ImageAbstract<?> img) {
//		ImageAbstract<String> e = ImageFS.getInstance(UIHelpers.asString(img.getIdentifier()), img.name(), img.size());
//		e.setCRC(img.CRC());
		
		Set<ImageAbstract<?>> imgs = CopyService.instance().images;
		boolean found = false;
		for (ImageAbstract<?> next : imgs) {
			if (img.size()==next.size()) {
				next.CRC();
				found = true;
			}
		}
		if (!found) return false;
		img.CRC();
		
		return found && CopyService.instance().images.contains(img);
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
		this.reader = new FileReader(pathFile, Recurse.Flat);
		this.images = new HashSet<>();
		initializeInternal();

	}

	private ImageAbstract<?>  findByIdentifier(String ident) {
		for (ImageAbstract<?> next : images) {
			if (Equals.equal(next.getIdentifier(), ident)) return next;
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

	public void copy(final ImageAbstract<?> entry, final CopyObserver observer) {
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
			                ApplInstance.openTasks.decrementAndGet();
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
						observer.done(CopyService.this.copySync(entry));
					}
				});
				ApplInstance.openTasks.incrementAndGet();
				queue.notify();
			}

//			return State.Ignore;
	}


	public State copySync(ImageAbstract<?> entry) {
		try {

//			ImageFS fde = new ImageFS(null,null,entry.size());
//			fde.setCRC(entry.CRC());

			if (exists(entry)) {
//				System.out.println("Already exists!!!");
//				for (ImageDir next : images) {
//					System.out.println(next.identifier+" "+next.name+" "+next.crc);
//				}
				return State.Ignore;
			}

			String fileName = entry.name();
			String baseName = FileUtils.getBaseName(fileName);
			String ext = FileUtils.getExtension(fileName);

			SimpleNameIncrementer nameInc = SimpleNameIncrementer.instance(baseName);

			File aFile = null;
			while ((aFile = getFile(ext, nameInc)).exists()) {
				nameInc.increment();
			}

			nioTransferCopy(entry, aFile);

			ImageFS newEntry = new ImageFS(FileUtils.extractFullPathName(aFile), fileName, entry.size());
			newEntry.setCRC(entry.CRC());

			ImageAbstract<?>  removeIt = CopyService.this.findByIdentifier(newEntry.getIdentifier());
			
			removeImage(removeIt);
			addImage(newEntry, false);
			
			return State.Success;

		} catch (Throwable e) {
			e.printStackTrace();
			return State.Error;
		}


	}

	private void addImage(ImageAbstract<?> add, boolean update) {
		if (add == null) return ;
		if (update && images.size()>0) {
			if (exists(add)) removeImage(add);
		}
		images.add(add);
		
		//debug
		boolean ok = false;
		for (ImageAbstract<?> next : images) {
			if (next == add) {
				ok =true;
				break;
			}
		}
		if (!ok) {
			System.out.println("Can't add image!");
		}
	}
	
	private void removeImage(ImageAbstract<?> add) {
		if (add == null) return ;
		int size = images.size();
		images.remove(add);
		if (images.size() == size) {
			System.out.println("Can't remove image: "+add);
		}
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

    private static void nioTransferCopy(ImageAbstract<?> entry, File target) throws IOException {
        FileChannel out = null;
	    Buf content = null;
	    try (FileOutputStream fos = new FileOutputStream(target)) {
            out = fos.getChannel();
            //out.write(ByteBuffer.wrap(entry.content()));
	        content = entry.content();
            out.write(content.get());
            out.force(false);
        } finally {
            //close(out);
		    Streams.closeSilently(content);
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

	@SuppressWarnings("unchecked")
	private void initializeInternal() {

		try {
			reader.register(new ContentChangeWatcher() {

				@Override
				public void onEvent(FileEvent type, Object id)throws IOException {
					switch (type) {
					case Create:
						//if (1==1) return ;
						String fileName = id.toString();
						File aFile = new File(fileName);
						if (aFile.isDirectory()) return ;
						ImageType imgType = ImageAbstract.getType(fileName);
						if (ImageType.UNKNOWN.equals(imgType)) return ;
						UIHelpers.sleep(1000);
						ImageAbstract<String> entryNew = (ImageAbstract<String>)reader.getEntryByIdentifier(id);
						addImage(entryNew, true);
						break;
					case Delete:
						ImageAbstract<?>  entryDel = findByIdentifier(""+id);
//						ImageAbstract<?> fde = CopyService.this.findByIdentifier(entryDel.getIdentifier());
						removeImage(entryDel);
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

				List<ImageAbstract<?>> toAdd = new ArrayList<ImageAbstract<?>>();
				reader.read(toAdd);

				for (ImageAbstract<?> next : toAdd) {
					addImage(((ImageAbstract<?>)next), false);
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
//		for (ImageDir fileDirEntry : images) {
//			System.out.println(fileDirEntry);
//		}
//	}


//	public void onContainerContentChange() {
//	}

//	public static void main(String[] args) {
//		Queue<String> a = new LinkedList<String>();
//		a.add("b");
//		a.add("x");
//		a.add("a");
//		System.out.println(a.remove());
//		System.out.println(a.remove());
//		System.out.println(a.remove());
//
//	}

	public static class CopyObserver {

		private MultiShell shell;
		private ImageCanvas panel;
		public CopyObserver(MultiShell shell, ImageCanvas canv) {
			this.shell = shell;
			this.panel = canv;
		}

		public void done(final State state) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					new SplashWindow(shell.active(), state);

					if (State.Success.equals(state)) {
						panel.setExist(true);
						panel.showImage();
					}

				}

			});
		}

	}

}
