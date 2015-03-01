package de.creditreform.common.pdf.resources;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ResourcesDR.
 */
public class ResourcesDR {

	   /** The Constant TEMPLATE_ENCODING. */
   	   public static final String DEFAULT_ENCODING = "UTF-8";

	/** The logger to use. */
	   private static final Logger logger
	         = LoggerFactory.getLogger(ResourcesDR.class);

		/** The cached. */
		private Map<String, String> cached;

	    /**
    	 * Instantiates a new resources dr.
    	 */
    	private ResourcesDR() {
	    	cached = new HashMap<String, String>();
		}

		/**
		 * Lazy-loaded Singleton, by Bill Pugh *.
		 */
	    private static class Holder {

        	/** The Constant INSTANCE. */
        	private final static ResourcesDR INSTANCE = new ResourcesDR();
	    }

	    /**
    	 * Gets the single instance of ResourcesDR.
    	 *
    	 * @return single instance of ResourcesDR
    	 */
    	public static ResourcesDR getInstance() {
	        return Holder.INSTANCE;
	    }


	   /**
	    * Convert stream to string.
	    *
	    * @param is the is
	    * @param encoding the encoding
	    * @return the string
	    * @throws IOException Signals that an I/O exception has occurred.
	    */
	   private String convertStreamToString(InputStream is, String encoding) throws IOException {
	     /*
	      * To convert the InputStream to String we use the
	      * Reader.read(char[] buffer) method. We iterate until the
	      * Reader return -1 which means there's no more data to
	      * read. We use the StringWriter class to produce the string.
	      */
	     if (is != null) {
	         Writer writer = new StringWriter();

	         char[] buffer = new char[512];
	         Reader reader = null;
	         try {
	             InputStreamReader isReader =  encoding != null ? new InputStreamReader(is, encoding) : new InputStreamReader(is);
	             reader = new BufferedReader(isReader);
	             int n;
	             while ((n = reader.read(buffer)) > 0) {
	                 writer.write(buffer, 0, n);
	             }
	         } finally {
	             reader.close();
	         }
	         return writer.toString();
	     } else {
	         return "";
	     }
	 }

	   /**
   	 * Gets the resource.
   	 *
   	 * @param name the name
   	 * @return the resource
   	 */
   	private synchronized String getResource(String name) {

		  String ret = cached.get(name);

		  if (null == ret) {
			  try {
				  InputStream stream = this.getClass().getResourceAsStream("/template/"+name);
				  ret = convertStreamToString(stream, DEFAULT_ENCODING);
			  } catch (IOException e) {
				  logger.error("",e);
				  ret = "";
			  }
			  cached.put(name, ret);
		  }

	      return ret;
	   }


	   /**
   	 * Gets the template.
   	 *
   	 * @param name the name
   	 * @return the template
   	 */
   	public static String getTemplate(String name) {
		   return ResourcesDR.getInstance().getResource(name);
	 }


   	/**
   	 * this method is about 2 times faster
   	 * replaced with quick and dirty java code.
   	 * @param template
   	 * @param toFill
   	 * @return
   	 */
   	public static String fillTemplate(String template, Map<String, String> toFill) {
   		if (toFill == null) return template;
		for (Map.Entry<String, String> tmp : toFill.entrySet()) {
			template = template.replace("["+tmp.getKey()+"]", tmp.getValue());
		}
		return template;
   	}


   	/**
   	 * @param template
   	 * @param toFill
   	 * @return
   	 */
   	public static String fillTemplateCustom(String template, Map<String, String> toFill) {
   		StringBuilder sb = new StringBuilder(template.length()+64);
   		Set<String> keys = toFill.keySet();


   		StringBuilder buff = new StringBuilder();

   		char ch;
   		boolean opened = false;
   		for (int j = template.length(), i = 0; i < j; i++) {
			ch = template.charAt(i);
			switch (ch) {
			case '[':
				if (opened) {
					sb.append('[');
					flush(sb, buff);
				} else {
					opened = true;
				}

				break;
			case ']':

				if (opened) {
				String strBuff = buff.toString();
				if (buff.length()>0 && keys.contains(strBuff)) {
						String toAdd = toFill.get(strBuff);
						if (null != toAdd) sb.append(toAdd);
						buff.setLength(0);
				} else {
					sb.append('[').append(buff).append(']');
					buff.setLength(0);
				}
				opened = false;
				} else {
					sb.append(ch);
				}
				break;
			default:
				if (opened)
					buff.append(ch);
				else
					sb.append(ch);

				break;

			}
		}
   		if (buff.length()>0) {
   			sb.append('[');
   			flush(sb, buff);
   		}

   		return sb.toString();

   	}


	private static void flush(StringBuilder sb, StringBuilder buff) {
		if (buff.length()>0) sb.append(buff);
		buff.setLength(0);

	}

}
