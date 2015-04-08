package net.j7.commons.io.zip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.j7.commons.io.FileUtils;
import net.j7.commons.io.helpers.WildcardMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractFileProcessor.
 * @author Alexander Tawrowski
 */
public abstract class AbstractZipParser {

   /** The technical logger to use. */
   private static final Logger logger = LoggerFactory.getLogger(AbstractZipParser.class);

   /** The Constant ALL_FILES. */
   private final static String ALL_FILES = "*.*";

   /** The base path. */
   public InputStream rootPath;

   /** The processed file count. */
   private int processedFiles;

   /** The processed dirs. */
   private int processedDirs;

   /** The wildcard. */
   private WildcardMask wildcard;

   /** The flag stopped. */
   private boolean flagStopped;


   /** The stop on exception. */
   private boolean stopOnException = false;

   private ZipInputStream zipFile;
   private boolean zipFileOpen;

   /**
    * Instantiates a new abstract file processor.
    *
    * @param basePath the base path
    */
   public AbstractZipParser(InputStream basePath) {
      this(basePath, ALL_FILES);
   }

   /**
    * Instantiates a new abstract file processor.
    *
    * @param basePath the base path
    * @param wildcardMask the wildcard mask
    */
   public AbstractZipParser(InputStream basePath, String wildcardMask) {

      this.rootPath = basePath;


      this.wildcard = new WildcardMask(wildcardMask);
      this.flagStopped = false;
   }

   /**
    * Gets the files processed.
    *
    * @return Returns the processed.
    */
   public int getCountFilesProcessed() {
      return processedFiles;
   }

   /**
    * you could override this method in child classes
    * if this method returns false -> this file will be skipped / omited.
    *
    * @param entry the entry
    * @param fileData the file data
    * @return true, if successful
    */
   public boolean accept(ZipEntry entry, EntryInfo fileData) {
      return true;
   }


   /**
    * you could override this method in child classes
    * if this method returns true -> this file will be skipped / omited.
    *
    * @param entry the entry
    * @param fileData the file data
    * @return true, if successful
    */
   public boolean skip(ZipEntry entry, EntryInfo fileData) {
      return false;
   }

   /**
    * Stop processing.
    */
   public void stopProcessing() {
      this.flagStopped = true;
   }


   /**
    * Process a file.
    *
    * @param file the file
    * @param EntryInfo the file info
    * @throws Exception the exception
    * @see stopProcessing()
    */
   public abstract void processEntry(ZipEntry file, EntryInfo entryInfo) throws Exception;

   /**
    * Start.
    * @throws IOException
    */
   public void read() throws IOException {
      checkIfOpen();
      zipFile = new ZipInputStream(rootPath);
      zipFileOpen = true;
      listContents(zipFile);
   }

   public void close() {
      try {
		checkIfOpen();
	} catch (IOException e) {
		logger.warn("", e);
	}
   }

   private final void checkIfOpen() throws IOException {
      if (zipFileOpen && zipFile!=null) {
         zipFileOpen = false;
         zipFile.close();
         zipFile = null;
      }
   }

   /**
    * List contents.
    *
    * @param entry the entry
    * @param recurse the recurse
 * @throws IOException
    */
   private void listContents(ZipInputStream zipFile) throws IOException {

      if (flagStopped || zipFile==null) return ;



      ZipEntry entry = null;
      while ( (entry = zipFile.getNextEntry()) != null ) {
    	  if (entry.isDirectory()) {
              this.processedDirs++;
              continue;
           }

           doFileIntern(entry);
      }
      zipFile.close();

   }

   /**
    * process file internally.
    *
    * @param entry the entry
    */
   private void doFileIntern(ZipEntry entry) {

      final String fullName = entry.getName();

      if (!wildcard.accept(fullName)) return ;

      EntryInfo fileData = new EntryInfo(entry);
      if (skip(entry, fileData) || !accept(entry, fileData)) return ;

      try {
         processEntry(entry, fileData);
         processedFiles++;
      } catch (Exception e) {
         logger.error("Error processing file", e);
         if (stopOnException) stopProcessing();
      }


   }

   /**
    * The Class EntryInfo.
    */
   public class EntryInfo {

      /** The file. */
      private ZipEntry file;

      /** The full name. */
      private String fullName;

      String fileData;

      /**
       * Instantiates a new file info.
       *
       * @param file the file
       */
      public EntryInfo(ZipEntry file) {
         this.file = file;
         this.fullName = file.getName();
      }

//
//      public InputStream getDataInputStream() throws IOException {
//    	  zipFile.
//         InputStream is = zipFile.getInputStream(file);
//         return is;
//      }

      public void readFileData() throws IOException {



    	  ByteArrayOutputStream out=new ByteArrayOutputStream();
    	  StringBuilder line = new StringBuilder();
          byte[] buffer=new byte[2048];
          int size;
          while ((size=zipFile.read(buffer,0,buffer.length)) >= 0) {
            out.write(buffer,0,size);
          }
          out.flush();
          line.append(out.toString("UTF-8"));

          this.fileData = line.toString();


      }

      public String getFileData() throws IOException {
          return this.fileData;
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
       * @return extension of a filename.
       */
      public String getExtension() {
         return FileUtils.getExtension(fullName);
      }


      /**
       * Gets the file.
       *
       * @return Returns the file.
       */
      public ZipEntry getEntry() {
         return file;
      }

      /**
       * Gets the file size.
       * @return the file size
       */
      public long getFileSize() {
         return file.getSize();
      }

      /**
       *  Gets the name with extension (if exists) <br>
       *  plus the path from a full filename.
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
        return FileUtils.getFilePath(fullName, false);
      }


      /**
       * Gets the relative path (without base Path).
       *
       * @return the path relative
       */
      public String getPathRelative() {
          String fullPath = getPath();
          return fullPath;
       }

   }


   /**
    * The Class WildcardMask.
    */
   private static class WildcardMask {

      /** The wildcard. */
      private String wildcard;

      /** The no filter. */
      private boolean noFilter;

      /**
       * Instantiates a new wildcard mask.
       *
       * @param mask the mask
       */
      public WildcardMask(String mask) {
         if (mask==null || mask.isEmpty()) mask = ALL_FILES;
         this.wildcard = mask;
         this.noFilter = ALL_FILES.equals(mask) || "*".equals(mask);
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

      return processedDirs;
   }





}

