package org.delafer.xanderView.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;

import net.j7.commons.base.Equals;
import net.j7.commons.io.FilePath;
import net.j7.commons.io.FileUtils;

import org.delafer.xanderView.common.SimpleNameIncrementer;
import org.delafer.xanderView.file.entry.FileDirEntry;
import org.delafer.xanderView.file.entry.FileImageEntry;
import org.delafer.xanderView.file.entry.ImageEntry;
import org.delafer.xanderView.file.readers.FileReader;
import org.delafer.xanderView.general.State;
import org.delafer.xanderView.gui.config.ApplConfiguration;
import org.delafer.xanderView.interfaces.*;
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



	Set<FileDirEntry> images;
//	ListIterator<FileImageEntry> iterator;
	File pathFile;
	String pathTxt;
	FileReader reader;


	public int size() {
		return images.size();
	}

	public void init() {
		String locationArg = ApplConfiguration.instance().get(ApplConfiguration.CFG_COPY_DIR);
		String location = FilePath.as().dir(locationArg).div(true).forceExists().build();
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

	public State copy(ImageEntry<?> entry) {
		try {
			FileDirEntry fde = new FileDirEntry(null,null,0);
			fde.crc = entry.CRC();

			if (images.contains(fde)) {
				System.out.println("Already exists!!!");
				for (FileDirEntry next : images) {
					System.out.println(next.identifier+" "+next.name+" "+next.crc);
				}
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

			reader.initialize();
			readStructure();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void readStructure() throws Exception {
		List<ImageEntry<?>> toAdd = new ArrayList<ImageEntry<?>>();
		reader.read(toAdd);

		for (ImageEntry<?> next : toAdd) {
			images.add(FileDirEntry.as((FileImageEntry)next));
		}
	}

	public void test() {
		for (FileDirEntry fileDirEntry : images) {
			System.out.println(fileDirEntry);
		}
	}


//	public void onContainerContentChange() {
//	}

}
