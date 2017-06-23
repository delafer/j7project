package net.j7.commons.io;

/*
 * @File: AbstractFileProcessor.java
 *
 *
 *
 * All rights reserved.
 *
 * @Author:  tavrovsa
 *
 * @Version $Revision: #1 $Date: $
 *
 *
 */
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.j7.commons.io.helpers.WildcardMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractFileProcessor.
 *
 * @author  tavrovsa
 */
public abstract class AbstractFileProcessor {

	/**
	 * The technical logger to use.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractFileProcessor.class);

	private final static String ALL_FILES = "*.*"; // The Constant ALL_FILES.

	public File rootPath;

	private int doneFiles;
	private int doneDirs;

	public enum Recurse {Flat, Recursiv, FNE};

	private WildcardMask wildcard;

	private Recurse mode;
	private boolean flagStopped;
	private boolean stopOnException;

	/**
	 * Instantiates a new abstract file processor.
	 *
	 * @param basePath the base path
	 */
	public AbstractFileProcessor(String basePath) {
		this(basePath, ALL_FILES);
	}

	/**
	 * Instantiates a new abstract file processor.
	 *
	 * @param basePath the base path
	 * @param wildcardMask the wildcard mask
	 */
	public AbstractFileProcessor(String basePath, String wildcardMask) {
		rootPath = new File(basePath);
		if (!rootPath.exists())
			rootPath = new File(FileUtils.correctPath(basePath));
		wildcard = new WildcardMask(wildcardMask);
		mode = Recurse.Recursiv;
		flagStopped = false;
	}

	/**
	 * Gets the files processed.
	 *
	 * @return Returns the processed.
	 */
	public int getCountFilesProcessed() {
		return doneFiles;
	}

	/**
	 * you could override this method in child classes if this method returns false -> this file will be skipped / omited.
	 *
	 * @param entry the entry
	 * @param fileData the file data
	 * @return true, if successful
	 */
	public boolean accept(File entry, FileInfo fileData) {
		return true;
	}

	/**
	 * you could override this method in child classes if this method returns true -> this file will be skipped / omited.
	 *
	 * @param entry the entry
	 * @param fileData the file data
	 * @return true, if successful
	 */
	public boolean skip(File entry, FileInfo fileData) {
		return false;
	}

	/**
	 * Stop processing.
	 */
	public void stopProcessing() {
		flagStopped = true;
	}

	/**
	 * Process a file.
	 *
	 * @param file the file
	 * @param fileInfo the file info
	 * @throws Exception the exception
	 * @see stopProcessing()
	 */
	public abstract void processFile(File file, FileInfo fileInfo) throws Exception;

	public void processDir(File file, FileInfo fileInfo) throws Exception {

	}

	/**
	 * Start.
	 */
	public void start() {
		try {
			onStart();
			listContents(rootPath, Recurse.FNE);
		} catch (IOException e) {
			logger.error("Error occured", e);
		} finally {
			onFinish();
		}
	}


	public void onStart() {};

	public abstract void onFinish();

	/**
	 * List contents.
	 *
	 * @param entry the entry
	 * @param recurse the recurse
	 * @throws IOException
	 */
	private void listContents(File entry, Recurse recurse) throws IOException {
		if (flagStopped || entry == null || !entry.exists()) return ;

		if (entry.isDirectory()) {

			if (Recurse.Flat.equals(recurse)) return;
			doDir(entry);

			String[] children = entry.list();
			if (null != children) {
				List<File> dirs = null;
				for (String element : children) {
					File record = new File(entry, element);
					if (record.isDirectory()) {
						if (null == dirs) dirs = new LinkedList<>();
						dirs.add(record);
					} else {
						doFile(record);
					}
				}

//				if (Recurse.FNE.equals(mode) && doneFiles > 0) {
//					flagStopped = true;
//				}

				if ((Recurse.Recursiv.equals(mode) || doneFiles == 0) && null != dirs) {
					for (File next : dirs) {
						listContents(next, mode);
					}
				}
			}

		} else
			doFile(entry);

	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(Recurse mode) {
		this.mode = mode;
	}

	private void doDir(File entry) throws IOException {
		FileInfo fileData = new FileInfo(entry);
		if (skip(entry, fileData) || !accept(entry, fileData))
			return;
		try {
			processDir(entry, fileData);
		} catch (Exception e) {
			logger.error("Error processing dir", e);
			if (stopOnException)
				stopProcessing();
		} finally {
			doneDirs++;
		}
	}

	/**
	 * process file internally.
	 *
	 * @param entry the entry
	 * @throws IOException
	 */
	private void doFile(File entry) throws IOException {
		final String fullName = entry.getCanonicalPath();
		if (!wildcard.accept(fullName)) return;
		FileInfo fileData = new FileInfo(entry);
		if (skip(entry, fileData) || !accept(entry, fileData)) return;
		try {
			processFile(entry, fileData);
		} catch (Exception e) {
			logger.error("Error processing file", e);
			if (stopOnException)
				stopProcessing();
		} finally {
			doneFiles++;
		}
	}

	/**
	 * The Class FileInfo.
	 */
	public static class FileInfo {

		/**
		 * The file.
		 */
		private final File file;

		/**
		 * The full name.
		 */
		private final String fullName;

		private String getFullPath(File file) {
			if (file == null)
				return "";
			try {
				return file.getCanonicalPath();
			} catch (IOException e) {
			}
			return file.getAbsolutePath();
		}


		/**
		 * Instantiates a new file info.
		 *
		 * @param file the file
		 * @throws IOException
		 */
		public FileInfo(File file) throws IOException {
			this.file = file;
			fullName = getFullPath(file);
		}

		/**
		 * Gets the name minus the path from a full filename.
		 *
		 * @return the file name
		 */
		public String getFileName() {
			return FileUtils.getFileName(fullName);
		}

		/**
		 * Gets the base name, minus the full path and extension, from a full filename.
		 *
		 * @return the file name base
		 */
		public String getFileNameBase() {
			return FileUtils.getBaseName(fullName);
		}

		/**
		 * Gets the extension of a filename.
		 *
		 * @return extension of a filename.
		 */
		public String getExtension() {
			return FileUtils.getExtension(fullName);
		}

		public String getPathWithBaseName() {
			return FileUtils.removeExtension(getNameWithPath());
		}

		/**
		 * Gets the file.
		 *
		 * @return Returns the file.
		 */
		public File getFile() {
			return file;
		}

		/**
		 * Gets the file size.
		 *
		 * @return the file size
		 */
		public long getFileSize() {
			return file.length();
		}

		/**
		 * Gets the name with extension (if exists) <br>
		 * plus the path from a full filename.
		 *
		 * @return Returns the fullName with path
		 */
		public String getNameWithPath() {
			return fullName;
		}

		/**
		 * Does the work of getting the path.
		 *
		 * @return the path
		 */
		public String getPath() {
			if (file.getParentFile() == null) return "";
			return file.getParentFile().getPath();
		}

		/**
		 * Gets the relative path (without base Path).
		 *
		 * @return the path relative
		 */
		public String getPathRelative(File rootPath) {
			String fullPath = getPath();
			String relativePath = "";
			try {
				relativePath = fullPath.startsWith(rootPath.getPath())
						? fullPath.substring(rootPath.getPath().length() + 1)
								: fullPath;
			} catch (Exception e) {
			}
			return relativePath;
		}

	}

	/**
	 * The Class WildcardMask.
	 */
	private static class WildcardMask {

		/**
		 * The wildcard.
		 */
		private final String wildcard;

		/**
		 * The no filter.
		 */
		private final boolean noFilter;

		/**
		 * Instantiates a new wildcard mask.
		 *
		 * @param mask the mask
		 */
		public WildcardMask(String mask) {
			if (mask == null || mask.isEmpty())
				mask = ALL_FILES;
			wildcard = mask;
			noFilter = ALL_FILES.equals(mask) || "*".equals(mask);
		}

		/**
		 * Accept.
		 *
		 * @param name the name
		 * @return true, if successful
		 */
		public boolean accept(String name) {
			return noFilter ? true : WildcardMatcher.wildcardMatch(name, wildcard);
		}

	}

	/**
	 * Checks if is recurse sub directories.
	 *
	 * @return Returns the recurseSubdirs.
	 */
	public boolean isRecurseSubDirectories() {
		return Recurse.Recursiv.equals(mode);
	}

	/**
	 * Default value is true (on).
	 *
	 * @param recurseSubdirs The recurseSubdirs to set.
	 */
	public void setRecurseSubDirectories(boolean recurseSubdirs) {
		this.mode = Recurse.Recursiv;
	}

	/**
	 * Checks if is stop on exception.
	 *
	 * @return Returns the stopOnException.
	 */
	public boolean isStopOnException() {
		return stopOnException;
	}

	/**
	 * Sets the stop on exception.
	 *
	 * @param stopOnException The stopOnException to set.
	 */
	public void setStopOnException(boolean stopOnException) {
		this.stopOnException = stopOnException;
	}

	/**
	 * @return Returns the processedDirs.
	 */
	public int getProcessedDirsCount() {
		return doneDirs;
	}
}
