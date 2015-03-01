package de.creditreform.common.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;

import de.creditreform.common.pdf.resources.ResourcesDR;

public class Renderer {


	/** The logger to use. */
	/** The logger to use. */
	   private static final Logger logger
	         = LoggerFactory.getLogger(Renderer.class);


	private static final String EMPTY = "";

	private static final String TEMPLATE_KEY_BASE_CSS 			= "BASE_CSS";
	private static final String TEMPLATE_KEY_AGENCY_INQUIRY 	= "AGENCY_INQUIRY";
	private static final String TEMPLATE_KEY_SEND_DATE 			= "SEND_DATE";
	private static final String TEMPLATE_KEY_CREFO 				= "CREFO";
	private static final String TEMPLATE_KEY_UUID 				= "UUID";


//	public void createXml(OutputStream os, String inquiry) throws IOException{
//        try {
//        	String template = ResourcesDR.getTemplate("template.xml");
//        	Map<String, String> toFill = new HashMap<String, String>();
//        	toFill.put(TEMPLATE_KEY_UUID, EMPTY);
//        	toFill.put(TEMPLATE_KEY_CREFO, EMPTY);
//        	toFill.put(TEMPLATE_KEY_SEND_DATE, EMPTY);
//        	toFill.put(TEMPLATE_KEY_AGENCY_INQUIRY, StringEscapeUtils.escapeXml11(inquiry));
//        	String filledTemplate = ResourcesDR.fillTemplate(template, toFill);
//
//        	stringToStream(os, filledTemplate);
//
//        } catch (Exception e) {
//        	String errorMsg = "Can't generate xml: ";
//            logger.error(errorMsg,e);
//            throw new IOException(errorMsg, e);
//        }
//	}
//
//	public void createHtml(OutputStream os, String inquiry) throws IOException{
//        try {
//        	String template = ResourcesDR.getTemplate("template.html");
//        	Map<String, String> toFill = new HashMap<String, String>();
//        	toFill.put(TEMPLATE_KEY_BASE_CSS, EMPTY);
//        	toFill.put(TEMPLATE_KEY_AGENCY_INQUIRY, StringEscapeUtils.escapeHtml4(inquiry));
//        	String filledTemplate = ResourcesDR.fillTemplate(template, toFill);
//
//        	stringToStream(os, filledTemplate);
//
//        } catch (Exception e) {
//        	String errorMsg = "Can't generate html: ";
//            logger.error(errorMsg,e);
//            throw new IOException(errorMsg, e);
//        }
//	}
//
//	public void createText(OutputStream os, String inquiry)throws IOException {
//		try {
//		stringToStream(os, inquiry);
//		} catch (IOException e) {
//			String errorMsg = "Can't generate txt: ";
//            logger.error(errorMsg,e);
//            throw new IOException(errorMsg, e);
//		}
//	}

	/**
	 * @param os
	 * @param inquiry
	 * @throws IOException
	 */
	private void stringToStream(OutputStream os, String inquiry) throws IOException {
			os.write(inquiry.getBytes(Charset.forName(ResourcesDR.DEFAULT_ENCODING)));
			os.flush();
	}



	public static byte[] createPdf(String inquiry) throws IOException {
		if (inquiry!=null)
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			createPdf(outputStream, inquiry);
			return outputStream.toByteArray();
		} catch (Exception e) {
			logger.info("Error generating pdf: ",e);
		}
		return new byte[0];

	}


	public static void createPdf(OutputStream os, String inquiry) throws IOException {

        try {
        	String template = ResourcesDR.getTemplate("template.html");
        	Map<String, String> toFill = new HashMap<String, String>();
        	toFill.put(TEMPLATE_KEY_BASE_CSS, ResourcesDR.getTemplate("pdf.css"));
        	toFill.put(TEMPLATE_KEY_AGENCY_INQUIRY, StringEscapeUtils.escapeXml11(inquiry));
        	String filledTemplate = ResourcesDR.fillTemplate(template, toFill);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(filledTemplate);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
        	String errorMsg = "Can't generate pdf: ";
            logger.error(errorMsg,e);
            throw new IOException(errorMsg, e);
        } finally {
            if(os != null)
                try {
                    os.flush();
                } catch (IOException e) {/*ignore*/}
        }
	}

}
