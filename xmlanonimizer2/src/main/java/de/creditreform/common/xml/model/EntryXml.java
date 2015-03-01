package de.creditreform.common.xml.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.Attributes;

import de.creditreform.common.helpers.StringTokenizer;
import de.creditreform.common.helpers.StringUtils;
import de.creditreform.common.xml.model.EntryText.Content;
import de.creditreform.common.xml.model.resources.IAnonimizeSpec.ReplacementType;
import de.creditreform.common.xml.transformer.SaxTransformer;

public class EntryXml implements IEntry {

	private static final long serialVersionUID = 1L;


	private static final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+StringUtils.LF_DOS;

	public String qName;
	String path;
//	public boolean isSkipped;
	public String tagName;
	Map<String, String> attrs;
	EntryXml parent;
	private boolean ignored;

	private LinkedList<IEntry> childs = new LinkedList<IEntry>();

	public EntryXml(String name, EntryXml parentEntity, Attributes attrs, DocumentType docType) {
		this.qName = name;
		this.parent = parentEntity;
		if (parent != null) {
			parent.addNode(this);
		}
		this.attrs = getAttributes(attrs);
		this.path = parent == null ? name : parent.path + '/' + name;
		this.checkIrrelevantBlock(attrs, docType);
//		if (parent != null && parent.metaInfo != null) {
//			this.metaInfo = parent.metaInfo + '.' + name;
//		}
	}



	public void setValue(ReplacementType type, String value) {
		switch (type) {
		case ReplaceAll:
			childs.clear();
			this.addValue(value, Content.Text);
			break ;
		case OnlyText:
			LinkedList<IEntry> newList = new LinkedList<IEntry>();
			for (IEntry next : childs) {
				if (next.isTagStructure() ) newList.add(next);
			}
			EntryText txt = new EntryText(Content.Text);
			txt.addText(value);
			newList.addFirst(txt);
			childs = newList;
			break ;
		case TextRecursive:
			for (IEntry next : childs) {
				if (next.isTagXml()) {
					((EntryXml)next).setValue(ReplacementType.TextRecursive, value);
				}
			}
			this.setValue(ReplacementType.OnlyText, value);
			break ;
		case Ignore:
		default:
			break;
		}

	}



	protected void addNode(EntryXml childNode) {
		this.childs.add(childNode);
	}


	public void addCleanValue(String toAdd) {
		StringTokenizer.tokenize(this, toAdd);
	}


	public void addValue(String toAdd, Content cnt) {
		if (null == toAdd || toAdd.length() == 0) return;
//		System.out.println("["+toAdd+"]");
		getTextNode(cnt).addText(toAdd);
	}

	private Map<String, String> getAttributes(Attributes attrs) {
		int len;
		if (attrs == null || (len = attrs.getLength()) == 0) return null;

		HashMap<String, String> ret = new HashMap<String, String>(len);

		for (int i = 0; i < len; i++) {
			ret.put(attrs.getQName(i), attrs.getValue(i));
		}
		return ret;
	}

	private EntryText getTextNode(Content cnt) {
		IEntry ret = this.childs.size() > 0 ? this.childs.getLast() : null;
		if (null != ret && ret.isTagTxt() && ((EntryText)ret).equals(cnt))
			return (EntryText) ret;
		EntryText txt = new EntryText(cnt);
		this.childs.add(txt);
		return txt;
	}

	public Type getType() {
		return Type.XmlTag;
	}

	public CharSequence getTextValue() {
		StringBuilder s = new StringBuilder();
		if (childs.size()!=0)
		for (IEntry entity : childs) {
			if (Type.XmlText.equals(entity.getType())) s.append(entity.render());
		}
		return s;
	}

	public CharSequence render() {


		if (isIgnored()) return "";

		StringBuilder s = new StringBuilder();

		if (this.parent == null) s.append(header);

		s.append('<').append(qName);

		if (null != attrs)
		for (Map.Entry<String, String> next : attrs.entrySet()) {
			s.append(' ').append(next.getKey()).append("=\"").append(StringEscapeUtils.escapeXml10(next.getValue())).append('"');
		}


		if (this.childs.size() != 0) {

			s.append('>');

			for (IEntry node : this.childs) {
				s.append(node.render());
			}

			s.append("</");
			s.append(this.qName);
			s.append('>');

		} else {
			s.append("/>");
		}


		return s;
	}

	public String toString() {
		return String.format("Node [qName=%s, path=%s, meta=%s]", this.qName, this.path, this.tagName);
	}


    private void checkIrrelevantBlock(Attributes attrs, DocumentType docType) {
    	if (docType == null) return ;
        MetaTag tag = SaxTransformer.getTagByPath(path, docType);
        if (null == tag && null != parent) {
           tag = SaxTransformer.getTagByPath(qName, docType);
        }
        if (null != tag) {
           tagName = tag.name();
        }
     }



	public boolean isTagXml() {
		return Type.XmlTag.equals(getType());
	}



	public boolean isTagTxt() {
		return Type.XmlText.equals(getType());
	}



	public boolean isTagStructure() {
		return true;
	}



	/**
	 * @return the ignored
	 */
	public boolean isIgnored() {
		return ignored;
	}



	/**
	 * @param ignored the ignored t o set
	 */
	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

};
