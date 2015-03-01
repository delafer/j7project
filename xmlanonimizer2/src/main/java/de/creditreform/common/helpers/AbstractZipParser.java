package de.creditreform.common.helpers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
         return noFilter ? true : FileUtils.wildcardMatch(name, wildcard);
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


   /**
    * Class FileUtils.
    * This version is generally based on
    * apache IO FileName class.
    * It's a reduced and optimized version
    * containing only most commonly used methods
    *
    * @version $Revision: #1 $
    */
   public static final class FileUtils {

      /** The technical logger to use. */
      private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

      /**
       * The extension separator character.
       */
      public static final char EXT_SEPARATOR = '.';
      /**
       * The extension separator String.
       */
      public static final String EXT_SEPARATOR_STR = Character.toString(EXT_SEPARATOR);

      private static final String QUEST = "?".intern();

      private static final String STAR = "*".intern();

      /**
       * The system separator character.
       */
      private static final char SYSTEM_SEPARATOR = File.separatorChar;

      /**
       * The Unix separator character.
       */
      private static final char UNIX_SEPARATOR = '/';

      /**
       * The Windows separator character.
       */
      private static final char WINDOWS_SEPARATOR = '\\';

   //   /**
//       * The separator character that is the opposite of the system separator.
//       */
   //   private static final char OTHER_SEPARATOR;
   //   static {
//          if (isWinFileSystem()) {
//              OTHER_SEPARATOR = UNIX_SEPARATOR;
//          } else {
//              OTHER_SEPARATOR = WINDOWS_SEPARATOR;
//          }
   //   }

      /**
       * Converts all separators to the system separator.
       *
       * @param path  the path to be changed, null ignored
       * @return the updated path
       */
      public static String convertToSystemPath(String path) {
          if (path == null)
            return null;
          if (isWinFileSystem())
            return convertToWindowsPath(path);
         else
            return convertToUnixPath(path);
      }

      /**
       * Converts all separators to the Unix separator of forward slash.
       *
       * @param path  the path to be changed, null ignored
       * @return the updated path
       */
      public static String convertToUnixPath(String path) {
          if (path == null || path.indexOf(WINDOWS_SEPARATOR) == -1)
            return path;
          return path.replace(WINDOWS_SEPARATOR, UNIX_SEPARATOR);
      }

      /**
       * Converts all separators to the Windows separator of backslash.
       *
       * @param path  the path to be changed, null ignored
       * @return the updated path
       */
      public static String convertToWindowsPath(String path) {
          if (path == null || path.indexOf(UNIX_SEPARATOR) == -1)
            return path;
          return path.replace(UNIX_SEPARATOR, WINDOWS_SEPARATOR);
      }

      /**
       * Gets the base name, minus the full path and extension, from a full filename.
       * <p>
       * This method will handle a file in either Unix or Windows format.
       * The text after the last forward or backslash and before the last dot is returned.
       * <pre>
       * a/b/c.txt --> c
       * a.txt     --> a
       * a/b/c     --> c
       * a/b/c/    --> ""
       * </pre>
       * <p>
       * The output will be the same irrespective of the machine that the code is running on.
       *
       * @param filename  the filename to query, null returns null
       * @return the name of the file without the path, or an empty string if none exists
       */
      public static String getBaseName(String filename) {
          return removeExtension(getFileName(filename));
      }

      /**
       * Gets the extension of a filename.
       * <p>
       * This method returns the textual part of the filename after the last dot.
       * There must be no directory separator after the dot.
       * <pre>
       * foo.txt      --> "txt"
       * a/b/c.jpg    --> "jpg"
       * a/b.txt/c    --> ""
       * a/b/c        --> ""
       * </pre>
       * <p>
       * The output will be the same irrespective of the machine that the code is running on.
       *
       * @param filename the filename to retrieve the extension of.
       * @return the extension of the file or an empty string if none exists or {@code null}
       * if the filename is {@code null}.
       */
      public static String getExtension(String filename) {
          if (filename == null)
            return null;
          int index = indexOfExtension(filename);
          if (index == -1)
            return "";
         else
            return filename.substring(index + 1);
      }

      /**
       * Gets the name minus the path from a full filename.
       * <p>
       * This method will handle a file in either Unix or Windows format.
       * The text after the last forward or backslash is returned.
       * <pre>
       * a/b/c.txt --> c.txt
       * a.txt     --> a.txt
       * a/b/c     --> c
       * a/b/c/    --> ""
       * </pre>
       * <p>
       * The output will be the same irrespective of the machine that the code is running on.
       *
       * @param filename  the filename to query, null returns null
       * @return the name of the file without the path, or an empty string if none exists
       */
      public static String getFileName(String filename) {
          if (filename == null)  return null;

          int index = indexOfLastSeparator(filename);
          return filename.substring(index + 1);
      }

      /**
       * Does the work of getting the path.
       *
       * @param filename  the filename
       * @param addSeparator  0 to omit the end separator, 1 to return it
       * @return the path
       */
      public static String getFilePath(String filename, boolean includeSeparator) {
         if (filename == null) return null;

        int prefix = prefixLength(filename);
        if (prefix < 0)
         return null;
        if (prefix >= filename.length()) {
            if (includeSeparator)
               return getPrefix(filename);  // add end slash if necessary
            else
               return filename;
        }
        int index = indexOfLastSeparator(filename);
        if (index < 0)
         return filename.substring(0, prefix);
        int end = index + (includeSeparator ?  1 : 0);
        if (end == 0) {
            end++;
        }
        return filename.substring(0, end);
      }


      /**
       * Checks whether the extension of the filename is that specified.
       * <p>
       * This method obtains the extension as the textual part of the filename
       * after the last dot. There must be no directory separator after the dot.
       * The extension check is case-sensitive on all platforms.
       *
       * @param filename  the filename to query, null returns false
       * @param extension  the extension to check for, null or empty checks for no extension
       * @return true if the filename has the specified extension
       */
      public static boolean hasExtension(String filename, String extension) {
          if (filename == null) return false;
          if (extension==null) return true;

          if (extension.length() == 0)
            return indexOfExtension(filename) == -1;

          extension = extension.toLowerCase();
          if (!extension.startsWith(".")) {
            extension = "." + extension;
         }
          return filename.toLowerCase().endsWith(extension);

      }


      /**
       * Removes the extension from a filename.
       * <p>
       * This method returns the textual part of the filename before the last dot.
       * There must be no directory separator after the dot.
       * <pre>
       * foo.txt    --> foo
       * a\b\c.jpg  --> a\b\c
       * a\b\c      --> a\b\c
       * a.b\c      --> a.b\c
       * </pre>
       * <p>
       * The output will be the same irrespective of the machine that the code is running on.
       *
       * @param filename  the filename to query, null returns null
       * @return the filename minus the extension
       */
      public static String removeExtension(String filename) {
          if (filename == null)
            return null;
          int index = indexOfExtension(filename);
          if (index == -1)
            return filename;
         else
            return filename.substring(0, index);
      }


      public static boolean wildcardMatch(String filename, String wildcardMatcher) {
         return wildcardMatch(filename, wildcardMatcher, false);
      }

      /**
       * Checks a filename to see if it matches the specified wildcard matcher
       * allowing control over case-sensitivity.
       * <p>
       * The wildcard matcher uses the characters '?' and '*' to represent a
       * single or multiple wildcard characters.
       * N.B. the sequence "*?" does not work properly at present in match strings.
       *
       *
       * @param filename  the filename to match on
       * @param wildcard  the wildcard string to match against
       * @param caseSensitivity  what case sensitivity rule to use, null means case-sensitive
       * @return true if the filename matches the wilcard string
       *
       */
      public static boolean wildcardMatch(String filename, String wildcard, boolean caseSensitive) {

         if (filename == null && wildcard == null) return true;
         if (filename == null || wildcard == null) return false;

         String[] wcs = tokenSplitter(wildcard);
         boolean anyChars = false;
         int textIdx = 0;
         int wcsIdx = 0;
         Stack<int[]> backtrack = new Stack<int[]>();

         // loop around a backtrack stack, to handle complex * matching
         do {
            if (backtrack.size() > 0) {
               int[] array = backtrack.pop();
               wcsIdx = array[0];
               textIdx = array[1];
               anyChars = true;
            }

            // loop whilst tokens and text left to process
            while (wcsIdx < wcs.length) {

               if (wcs[wcsIdx].equals(QUEST)) {
                  // ? so move to next text char
                  textIdx++;
                  if (textIdx > filename.length()) {
                     break;
                  }
                  anyChars = false;

               } else if (wcs[wcsIdx].equals(STAR)) {
                  // set any chars status
                  anyChars = true;
                  if (wcsIdx == wcs.length - 1) {
                     textIdx = filename.length();
                  }

               } else {
                  // matching text token
                  if (anyChars) {
                     //if any chars -> try to locate text token
                     textIdx = checkIndexOf(filename, textIdx, wcs[wcsIdx], caseSensitive);
                     if (textIdx == -1) {
                        // token not found
                        break;
                     }
                     int repeat = checkIndexOf(filename, textIdx + 1, wcs[wcsIdx], caseSensitive);
                     if (repeat >= 0) {
                        backtrack.push(new int[] {wcsIdx, repeat});
                     }
                  } else // matching from current position
                     if (!isRegionMatches(filename, textIdx, wcs[wcsIdx], caseSensitive)) {
                        // couldn't match token
                        break;
                     }

                  // matched text token, move text index to end of matched token
                  textIdx += wcs[wcsIdx].length();
                  anyChars = false;
               }

               wcsIdx++;
            }

            // full match
            if (wcsIdx == wcs.length && textIdx == filename.length())
               return true;

         } while (backtrack.size() > 0);

         return false;
      }


      public static boolean wildcardMatchOnSystem(String filename, String wildcardMatcher) {
         return wildcardMatch(filename, wildcardMatcher, isWinFileSystem() ? false : true);
     }


      public static String correctPath(String path) {
         if (path==null || path.isEmpty()) return path;
         try {
            String ret = new File(path).toURI().toURL().getFile();
            if (ret.startsWith("/")) ret = ret.substring(1);
            return FileUtils.convertToSystemPath(ret);
         } catch (Exception e) {
            // ignore exception //
         }
         return FileUtils.convertToSystemPath(path);
      }

      /**
       * Determines if Windows file system is in use.
       *
       * @return true if the system is Windows
       */
      static boolean isWinFileSystem() {
          return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
      }

      /**
       * Checks if one string contains another starting at a specific index using the
       * case-sensitivity rule.
       * <p>
       * This method mimics parts of {@link String#indexOf(String, int)}
       * but takes case-sensitivity into account.
       *
       * @param str  the string to check, not null
       * @param strStartIndex  the index to start at in str
       * @param search  the start to search for, not null
       * @return the first index of the search String,
       *  -1 if no match or {@code null} string input
       * @throws NullPointerException if either string is null
       */
      private final static int checkIndexOf(String str, int strStartIndex, String search, boolean sensitive) {
         int endIndex = str.length() - search.length();
         if (endIndex >= strStartIndex) {
            for (int i = strStartIndex; i <= endIndex; i++)
               if (isRegionMatches(str, i, search, sensitive))
                  return i;
         }
         return -1;
      }

      /**
       * Gets the prefix from a full filename,
       */
      private final static String getPrefix(String filename) {
         if (filename == null)
            return null;
         int len = prefixLength(filename);
         if (len < 0)
            return null;
         if (len > filename.length())
            return filename + UNIX_SEPARATOR;  // we know this only happens for unix
         return filename.substring(0, len);
     }

      /**
       * Returns the index of the last extension separator character, which is a dot.
       * <p>
       * This method also checks that there is no directory separator after the last dot.
       * To do this it uses {@link #indexOfLastSeparator(String)} which will
       * handle a file in either Unix or Windows format.
       * <p>
       * The output will be the same irrespective of the machine that the code is running on.
       *
       * @param filename  the filename to find the last path separator in, null returns -1
       * @return the index of the last separator character, or -1 if there
       * is no such character
       */
      private static int indexOfExtension(String filename) {
          if (filename == null)
            return -1;
          int extensionPos = filename.lastIndexOf(EXT_SEPARATOR);
          int lastSeparator = indexOfLastSeparator(filename);
          return lastSeparator > extensionPos ? -1 : extensionPos;
      }

      /**
       * Returns the index of the last directory separator character.
       * <p>
       * This method will handle a file in either Unix or Windows format.
       * The position of the last forward or backslash is returned.
       * <p>
       * The output will be the same irrespective of the machine that the code is running on.
       *
       * @param filename  the filename to find the last path separator in, null returns -1
       * @return the index of the last separator character, or -1 if there
       * is no such character
       */
      private static int indexOfLastSeparator(String filename) {
          if (filename == null)
            return -1;
          int lastUnixPos = filename.lastIndexOf(UNIX_SEPARATOR);
          int lastWindowsPos = filename.lastIndexOf(WINDOWS_SEPARATOR);
          return Math.max(lastUnixPos, lastWindowsPos);
      }

      /**
       * Checks if one string contains another at a specific index using the case-sensitivity rule.
       * <p>
       * This method mimics parts of {@link String#regionMatches(boolean, int, String, int, int)}
       * but takes case-sensitivity into account.
       *
       * @param str  the string to check, not null
       * @param strStartIndex  the index to start at in str
       * @param search  the start to search for, not null
       * @return true if equal using the case rules
       * @throws NullPointerException if either string is null
       */
      private final static boolean isRegionMatches(String str, int strStartIndex, String search, boolean sensitive) {
         return str.regionMatches(!sensitive, strStartIndex, search, 0, search.length());
      }

      /**
       * Checks if the character is a separator.
       *
       * @param ch  the character to check
       * @return true if it is a separator character
       */
      private static boolean isSeparator(char ch) {
          return ch == UNIX_SEPARATOR || ch == WINDOWS_SEPARATOR;
      }

      private final static int prefixLength(String filename) {
         if (filename == null)
            return -1;
         int len = filename.length();
         if (len == 0)
            return 0;
         char ch0 = filename.charAt(0);
         if (ch0 == ':')
            return -1;
         if (len == 1) {
             if (ch0 == '~')
               return 2;  // return a length greater than the input
             return isSeparator(ch0) ? 1 : 0;
         } else {
             if (ch0 == '~') {
                 int posUnix = filename.indexOf(UNIX_SEPARATOR, 1);
                 int posWin = filename.indexOf(WINDOWS_SEPARATOR, 1);
                 if (posUnix == -1 && posWin == -1)
                  return len + 1;  // return a length greater than the input
                 posUnix = posUnix == -1 ? posWin : posUnix;
                 posWin = posWin == -1 ? posUnix : posWin;
                 return Math.min(posUnix, posWin) + 1;
             }
             char ch1 = filename.charAt(1);
             if (ch1 == ':') {
                 ch0 = Character.toUpperCase(ch0);
                 if (ch0 >= 'A' && ch0 <= 'Z') {
                     if (len == 2 || isSeparator(filename.charAt(2)) == false)
                        return 2;
                     return 3;
                 }
                 return -1;

             } else if (isSeparator(ch0) && isSeparator(ch1)) {
                 int posUnix = filename.indexOf(UNIX_SEPARATOR, 2);
                 int posWin = filename.indexOf(WINDOWS_SEPARATOR, 2);
                 if (posUnix == -1 && posWin == -1 || posUnix == 2 || posWin == 2)
                  return -1;
                 posUnix = posUnix == -1 ? posWin : posUnix;
                 posWin = posWin == -1 ? posUnix : posWin;
                 return Math.min(posUnix, posWin) + 1;
             } else
               return isSeparator(ch0) ? 1 : 0;
         }
     }

      /**
       * Splits a string into a number of tokens.
       * The text is split by '?' and '*'.
       * Where multiple '*' occur consecutively they are collapsed into a single '*'.
       *
       * @param txt  the text to split
       * @return the array of tokens, never null
       */
      private static final String[] tokenSplitter(String txt) {
         // used by wildcardMatch
         // package level so a unit test may run on this

         if (txt.indexOf('?') == -1 && txt.indexOf('*') == -1)
            return new String[] { txt };

         char[] array = txt.toCharArray();
         ArrayList<String> list = new ArrayList<String>();
         StringBuilder sb = new StringBuilder(txt.length());

         for (int i = 0; i < array.length; i++)
            if (array[i] == '?' || array[i] == '*') {
               if (sb.length() != 0) {
                  list.add(sb.toString());
                  sb.setLength(0);
               }
               if (array[i] == '?') {
                  list.add(QUEST);
               } else
               if (list.isEmpty() || i > 0 && list.get(list.size() - 1).equals(STAR) == false) {
                  list.add(STAR);
               }
            } else {
               sb.append(array[i]);
            }
         if (sb.length() != 0) {
            list.add(sb.toString());
         }

         return list.toArray( new String[ list.size() ] );
      }

   }


}

