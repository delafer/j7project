package de.creditreform.common.xml.transformer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.creditreform.common.xml.model.IEntry.DocumentType;
import de.creditreform.common.xml.model.MetaTag;
import de.creditreform.common.xml.model.XmlModel;

/**
 * The SAX2 XmlParser . simple SAX2 class to parse reportResponse,
 * mailboxentryResponse & Lieferung formats
 *
 * @author Alexander Tavrovsky
 */
public class SaxTransformer {

	/**
	 * The technical logger to use.
	 */
	private static final Logger logger = LoggerFactory.getLogger(SaxTransformer.class);

	private static final String UNKNOWN_FORMAT_FILE = "Unknown/not xml formats file";

//	public static Map<String, DocumentType> types; //"ns2:reportResponse"->DocumentType.ReportResponse

//	private static Map<DocumentType, Map<String, MetaTag>> metaTags;//"ReportResponse"->["Lieferung/Header/SendeZeit" -> MetaTag.InquireySendTime]

	static SAXParser parser;

	static {
		// register xml tags
//		types = new HashMap<>();
//		metaTags = new HashMap<>();
//		for (DocumentType next : DocumentType.values()) {
//			types.put(next.tag, next);
//			Map<String, MetaTag> subMap = new HashMap<>();
//			metaTags.put(next, subMap);
//			XmlFormatTypes.fillTagsByType(next, subMap);
//		}
		// register SAX2 parser factory
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(false);

		try {
			// Creates a SAXParser and its thread safe so best to initialize it once to save creation cost at the time of call
			parser = factory.newSAXParser();

		} catch (ParserConfigurationException e) {
			logger.error("can't create a SAX2 Parser", e);
		} catch (SAXException e2) {
			logger.error("can't create a SAX2 Parser", e2);
		}
	}

	/**
	 * a default method to be called for text extraction
	 *
	 * @param is
	 * @return extracted text
	 * @throws IOException
	 */
	public XmlModel readXmlModel(InputStream is) throws IOException {

		if (null == is)
			return null;


		try {
			return parseRawXML(is);
		} catch (IOException e) {
			logger.error(UNKNOWN_FORMAT_FILE, e);
		} catch (SAXException e2) {
			logger.error(UNKNOWN_FORMAT_FILE, e2);
		}

		return null;
	}

	private XmlModel parseRawXML(InputStream is) throws SAXException, IOException {
		if (null != is) {
			XmlHandler handler = null;
			try {
				XMLReader reader = parser.getXMLReader();

				handler = new XmlHandler();
				reader.setContentHandler(handler);
				InputSource source= new InputSource(is);

				reader.parse(source);

				return handler.getModel();

			} catch (SAXException e) {
				logger.info("Sax Parsing exception: ",e);
			} finally {
				is.close();
//				System.out.println(handler.getModel().getXmlModel().render());
			}
		}
		return null;
	}


	public static MetaTag getTagByPath(String qName, DocumentType docType) {

		if (!DocumentType.Unknown.equals(docType)) {
			Map<String, MetaTag> metaTag = AnonimizeData.instance().getMetaTags(docType);
			return metaTag.get(qName);
		}
		return null;
	}

	/**
	 * Checks if it is an irrelevant (ignored) block. If (true) such block is
	 * ignored If (false) text information is extracted
	 *
	 * @param tag
	 *            the tag
	 * @param attributes
	 *            the attributes
	 * @return true, if is irrelevant block
	 */
	private static boolean isIrrelevantBlock(MetaTag tag, Attributes attributes) {
		return false;
	}

}
