package net.sf.sevenzipjbinding.impl;

import net.sf.sevenzipjbinding.ArchiveFormat;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.ICryptoGetTextPassword;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.PropertyInfo;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.impl.SimpleInArchiveImpl;

/**
 * Implementation of {@link ISevenZipInArchive}.
 * 
 * @author Boris Brodski
 * @version 4.65-1
 * 
 */
public class InArchiveImpl implements ISevenZipInArchive {
	private static class ExtractSlowCallback implements IArchiveExtractCallback {
		ISequentialOutStream sequentialOutStreamParam;
		private ExtractOperationResult extractOperationResult;

		ExtractSlowCallback(ISequentialOutStream sequentialOutStream) {
			this.sequentialOutStreamParam = sequentialOutStream;
		}

		/**
		 * {@inheritDoc}
		 */
		public void setTotal(long total) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void setCompleted(long completeValue) {
		}

		/**
		 * {@inheritDoc}
		 */
		public void setOperationResult(ExtractOperationResult extractOperationResult) {
			this.extractOperationResult = extractOperationResult;
		}

		/**
		 * {@inheritDoc}
		 */
		public void prepareOperation(ExtractAskMode extractAskMode) {
		}

		/**
		 * {@inheritDoc}
		 */
		public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) {
			return extractAskMode.equals(ExtractAskMode.EXTRACT) ? sequentialOutStreamParam : null;
		}

		ExtractOperationResult getExtractOperationResult() {
			return extractOperationResult;
		}
	}

	private static final class ExtractSlowCryptoCallback extends ExtractSlowCallback implements ICryptoGetTextPassword {

		private String password;

		public ExtractSlowCryptoCallback(ISequentialOutStream sequentialOutStream, String password) {
			super(sequentialOutStream);
			this.password = password;
		}

		public String cryptoGetTextPassword() throws SevenZipException {
			return password;
		}
	}

	@SuppressWarnings("unused")
	private long sevenZipArchiveInstance;

	@SuppressWarnings("unused")
	private long sevenZipArchiveInStreamInstance;

	private int numberOfItems = -1;

	private ArchiveFormat archiveFormat;

	/**
	 * {@inheritDoc}
	 */
	public void extract(int[] indices, boolean testMode, IArchiveExtractCallback extractCallback)
			throws SevenZipException {

		nativeExtract(indices, testMode, extractCallback);
	}

	/**
	 * {@inheritDoc}
	 */
	public ExtractOperationResult extractSlow(int index, ISequentialOutStream outStream) throws SevenZipException {
		ExtractSlowCallback extractCallback = new ExtractSlowCallback(outStream);
		nativeExtract(new int[] { index }, false, extractCallback);
		return extractCallback.getExtractOperationResult();
	}

	/**
	 * {@inheritDoc}
	 */

	public ExtractOperationResult extractSlow(int index, ISequentialOutStream outStream, String password)
			throws SevenZipException {
		ExtractSlowCryptoCallback extractCallback = new ExtractSlowCryptoCallback(outStream, password);
		nativeExtract(new int[] { index }, false, extractCallback);
		return extractCallback.getExtractOperationResult();
	}

	private native void nativeExtract(int[] indices, boolean testMode, IArchiveExtractCallback extractCallback)
			throws SevenZipException;

	private native Object nativeGetArchiveProperty(int propID) throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public Object getArchiveProperty(PropID propID) throws SevenZipException {

		return nativeGetArchiveProperty(propID.getPropIDIndex());
	}

	private native String nativeGetStringArchiveProperty(int propID) throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public String getStringArchiveProperty(PropID propID) throws SevenZipException {
		return nativeGetStringArchiveProperty(propID.getPropIDIndex());
	}

	private native PropertyInfo nativeGetArchivePropertyInfo(int index);

	/**
	 * {@inheritDoc}
	 */
	public PropertyInfo getArchivePropertyInfo(PropID propID) throws SevenZipException {
		return nativeGetArchivePropertyInfo(propID.getPropIDIndex());
	}

	private native int nativeGetNumberOfArchiveProperties() throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public int getNumberOfArchiveProperties() throws SevenZipException {
		return nativeGetNumberOfArchiveProperties();
	}

	private native int nativeGetNumberOfProperties() throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public int getNumberOfProperties() throws SevenZipException {
		return nativeGetNumberOfProperties();
	}

	private native PropertyInfo nativeGetPropertyInfo(int index) throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public PropertyInfo getPropertyInfo(PropID propID) throws SevenZipException {
		return nativeGetPropertyInfo(propID.getPropIDIndex());
	}

	private native void nativeClose() throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public void close() throws SevenZipException {
		nativeClose();
	}

	private native int nativeGetNumberOfItems() throws SevenZipException;

	/**
	 * {@inheritDoc}
	 */
	public int getNumberOfItems() throws SevenZipException {
		if (numberOfItems == -1) {
			numberOfItems = nativeGetNumberOfItems();
		}
		return numberOfItems;
	}

	private native Object nativeGetProperty(int index, int propID);

	/**
	 * {@inheritDoc}
	 */
	public Object getProperty(int index, PropID propID) throws SevenZipException {
		if (index < 0 || index >= getNumberOfItems()) {
			throw new SevenZipException("Index out of range. Index: " + index + ", NumberOfItems: "
					+ getNumberOfItems());
		}
		// Correct some returned values
		Object returnValue = nativeGetProperty(index, propID.getPropIDIndex());
		switch (propID) {
		case SIZE:
		case PACKED_SIZE:
			// ARJ archive returns sized as Integer (32 bit).
			// It isn't particular good idea, since every other archive returns Long (64 bit).
			// So it will be corrected here.
			if (returnValue instanceof Integer) {
				return Long.valueOf(((Integer) returnValue).longValue());
			}

			if (returnValue == null && archiveFormat != null && archiveFormat == ArchiveFormat.NSIS) {
				return Long.valueOf(0);
			}
			break;

		case IS_FOLDER:
			// Some stream archive formats doesn't set this property.
			if (returnValue == null) {
				return Boolean.FALSE;
			}

		case ENCRYPTED:
			// Some stream archive formats doesn't set this property.
			if (returnValue == null) {
				return Boolean.FALSE;
			}
		}
		return returnValue;
	}

	private native String nativeGetStringProperty(int index, int propID);

	/**
	 * {@inheritDoc}
	 */
	public String getStringProperty(int index, PropID propID) throws SevenZipException {
		if (index < 0 || index >= getNumberOfItems()) {
			throw new SevenZipException("Index out of range. Index: " + index + ", NumberOfItems: "
					+ getNumberOfItems());
		}
		return nativeGetStringProperty(index, propID.getPropIDIndex());
	}

	/**
	 * {@inheritDoc}
	 */
	public ISimpleInArchive getSimpleInterface() {
		return new SimpleInArchiveImpl(this);
	}

	/**
	 * ${@inheritDoc}
	 */
	public ArchiveFormat getArchiveFormat() {
		return archiveFormat;
	}

	/**
	 * Set archive format of the opened archive. This method should be called only through JNI.
	 * 
	 * @param archiveFormat
	 *            format of the opened archive
	 */
	@SuppressWarnings("unused")
	private void setArchiveFormat(String archiveFormatString) {
		for (ArchiveFormat archiveFormat : ArchiveFormat.values()) {
			if (archiveFormat.getMethodName().equalsIgnoreCase(archiveFormatString)) {
				this.archiveFormat = archiveFormat;
				return;
			}
		}
	}
}
