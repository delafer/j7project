package de.creditreform.common.db;

import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

public class DbTools {

	   private static final String ENCODING = "UTF-8";

	   public static byte[] blobToBytes(Blob blob) throws SQLException {
		      if (null == blob) return null;
		      long size  = blob.length();
		      byte[] dataInp = size > 0l ? blob.getBytes(1, (int)size) : null;
		      return dataInp;
		   }



		   public static String zippedBlobToStr(Blob clobObject) throws SQLException, IOException {
		      if (null == clobObject) return null;
		      final int BUFFER = 2048;
		      long size  = clobObject.length();
		      byte[] dataInp = size > 0l ? clobObject.getBytes(1, (int)size) : new byte[0];

		      try {

		         ByteArrayOutputStream dest = null;

		         ByteArrayInputStream fis = new ByteArrayInputStream(dataInp);

		         GZIPInputStream zis = new GZIPInputStream(new BufferedInputStream(fis));

		            System.out.println("Extracting: " + zis);
		            int count;
		            byte data[] = new byte[BUFFER];
		            // write the files to the disk
		            dest = new ByteArrayOutputStream();

		            while ((count = zis.read(data, 0, BUFFER)) != -1) {
		               dest.write(data, 0, count);
		            }
		            dest.flush();
		            dest.close();
		         zis.close();

		         return new String(dest.toByteArray(), ENCODING);
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		      return null;
		   }

		   /**
		    * Clob to str.
		    *
		    * @param clobObject the clob object
		    * @return the string
		    * @throws SQLException the sQL exception
		    * @throws IOException Signals that an I/O exception has occurred.
		    */
		   public static String clobToStr(Clob clobObject) throws SQLException, IOException {
		      if (null == clobObject) return null;
		      InputStream in = clobObject.getAsciiStream();
		      Reader read = new InputStreamReader(in);
		      StringWriter write = new StringWriter();

		      int c = -1;
		      while ((c = read.read()) != -1)
		      {
		          write.write(c);
		      }
		      write.flush();
		      String s = write.toString();
		      //TODO Wrap the InputStreamReader and StringWriter with BufferedReader and BufferedWriter respectively for better performance.
		      return s;
		   }

}
