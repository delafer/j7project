package de.creditreform.common.xml.model;

import org.apache.commons.lang3.StringEscapeUtils;



public class EntryText implements IEntry {

	private static final long serialVersionUID = 2L;

	public enum Content {EmptyChars, Text};

	private StringBuilder text;

	public EntryText(Content content) {
		this.content = content;
	}

	private Content content;

	public void addText(CharSequence toAdd) {
		if (toAdd == null || toAdd.length() == 0) return;

		if (null == this.text) { text = new StringBuilder(); }
		text.append(toAdd);
	}

	public Type getType() {
		return Type.XmlText;
	}

	public CharSequence render() {
		return null != text ? isTagStructure() ? text: StringEscapeUtils.escapeXml10(text.toString()) : "";
	}

	public boolean isTagXml() {
		return Type.XmlTag.equals(getType());
	}



	public Content getContentType() {
		return content;
	}

	public boolean isTagTxt() {
		return Type.XmlText.equals(getType());
	}

	public final static boolean isNotEmpty(final String s) {
		 int len;
		 if (null != s && (len = s.length())!=0)
		 while (len > 0) if (s.charAt(--len)>' ') return true;
		 return false;
		}

	public boolean isTagStructure() {
		return Content.EmptyChars.equals(content);
	}

	public boolean isAttribute() {
		return false;
	}
}
