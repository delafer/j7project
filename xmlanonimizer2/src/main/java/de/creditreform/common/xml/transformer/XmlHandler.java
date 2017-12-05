package de.creditreform.common.xml.transformer;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.creditreform.common.xml.model.*;
import de.creditreform.common.xml.model.resources.MultiValue;

/**
 * SAX2 event handler for parsing FictionBook files
 * http://fictionbook.org/index.php/Eng:FictionBook_description
 */
public class XmlHandler extends DefaultHandler {

	// As we read any XML element we will push that in this stack
	Stack<EntryXml> elementStack;

	XmlModel model;


	public XmlHandler() {
		this.elementStack = new Stack<EntryXml>();
		model = new XmlModel();
	}


	public XmlModel getModel() {
		return model;
	}


	private void putData(EntryXml xmlEntry, String value) {

//		if (xmlEntry.attributes() != null && !xmlEntry.attributes().isEmpty()) {
//
//			for (Map.Entry<String, String>  next: xmlEntry.attributes().entrySet()) {
//
//				if (next.getValue() == null || next.getValue().isEmpty()) continue;
//
//				MetaTag mt = xmlEntry.getMetaTagByAttrKey(next.getKey(), model.documentType);
//				if (null == mt || mt.name() == null) continue;
//
//				if (AnonimizeData.instance().isDataTag(model.documentType,mt.name())) {
//					putData(mt.name(), next.getValue().trim());
//					putField(mt.name(), xmlEntry);
//				}
//			}
//
//		}

		String key = xmlEntry.tagName;

		if (key==null || key.isEmpty()) return ;

		if (AnonimizeData.instance().isDataTag(model.documentType, key)) {
			putData(key, value.trim());
		}



		putField(key, xmlEntry);
	}


	private void putData(String key, String value) {
		MultiValue<String> mv = model.values.get(key);
		if (null == mv) {
			mv = new MultiValue<String>();
			model.values.put(key, mv);
		}
		mv.addValue(value);
	}


	private void putField(String tagKey, EntryXml xmlEntry) {

		MetaTag key = MetaTag.valueOf(tagKey);

		MultiValue<EntryXml> mv = model.anonimizeFields.get(key);
		if (null == mv) {
			mv = new MultiValue<EntryXml>();
			model.anonimizeFields.put(key, mv);
		}
		mv.addValue(xmlEntry);

	}


	/**
	 * This is called to get the tags value *
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (0 == length) return;
		EntryXml current = currentElement();
		if (null != current) {
			String toAdd = new String(ch, start, length);

			if (toAdd.length() == 0) return;
			current.addCleanValue(toAdd);
		}
	}

	/**
	 * Utility method for getting the current element in processing
	 */
	private EntryXml currentElement() {

		return this.elementStack.empty() ? null : this.elementStack.peek();
	}

	private DocumentType detectDocumentType(String qName) {
		DocumentType res = AnonimizeData.instance().getTypes(qName);
		return res != null ? res : DocumentType.TYPE_UNKNOWN;
	}

	/**
	 * This will be called when the tags of the XML end. *
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
//		System.out.println("<<<<<"+uri+"; "+localName+"; "+qName);
		EntryXml current = currentElement();

		if (null != current) {
			String toAdd = String.valueOf(current.getTextValue());
				putData(current, toAdd);
		}
		this.elementStack.pop(); // Remove last added element
	}

	/**
	 * This will be called when the tags of the XML starts. *
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

//		System.out.println(">>>>>"+uri+"; "+localName+"; "+qName);
		EntryXml parent = currentElement();
		EntryXml newNode = new EntryXml(qName, parent, EmptyObject.EMPTY_ATTR, model.documentType);
		if (null == parent) {
			model.documentType = detectDocumentType(qName);
			model.xmlModel = newNode;
			putData(MetaTag.valueOf("DocumentType").name(), model.documentType.tag());
		}
		this.elementStack.push(newNode);

		if (attributes.getLength() > 0) {
			for (int i = 0; i < attributes.getLength(); i++) {
				String keyName = IEntry.ATTR_PREFFIX+attributes.getQName(i);
				startElement("", "", keyName, EmptyObject.EMPTY_ATTR);

				char[] ch = attributes.getValue(i).toCharArray();

				characters(ch, 0, ch.length);

				endElement("", "", keyName);
			}
		}

	}
}
