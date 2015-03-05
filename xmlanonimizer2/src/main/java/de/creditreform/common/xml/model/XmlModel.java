package de.creditreform.common.xml.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.creditreform.common.xml.model.resources.MultiValue;

public class XmlModel implements Serializable {

	private static final long serialVersionUID = 1969200216361689361L;

	public DocumentType documentType;
	public EntryXml xmlModel;
	public Map<String, MultiValue<String>> values;
	public Map<MetaTag, MultiValue<EntryXml>> anonimizeFields;

	public XmlModel() {
		values = new HashMap<String, MultiValue<String>>();
		anonimizeFields = new HashMap<MetaTag, MultiValue<EntryXml>>();
	}



	public Map<String, MultiValue<String>> getValues() {
		return values;
	}

	public void setValues(Map<String, MultiValue<String>> values) {
		this.values = values;
	}

	public EntryXml getXmlModel() {
		return xmlModel;
	}

	public void setXmlModel(EntryXml xmlModel) {
		this.xmlModel = xmlModel;
	}


	public Map<MetaTag, MultiValue<EntryXml>> getAnonimizeFields() {
		return anonimizeFields;
	}


}
