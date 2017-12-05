package de.creditreform.common.xml.transformer;

public class EmptyObject {


	public final static org.xml.sax.Attributes EMPTY_ATTR = new EmptyAttr();

	public static class EmptyAttr implements org.xml.sax.Attributes {


		public int getLength() {
			return 0;
		}


		public String getURI(int index) {
			return null;
		}


		public String getLocalName(int index) {
			return null;
		}


		public String getQName(int index) {
			return null;
		}


		public String getType(int index) {
			return null;
		}


		public String getValue(int index) {
			return null;
		}


		public int getIndex(String uri, String localName) {
			return 0;
		}


		public int getIndex(String qName) {
			return 0;
		}


		public String getType(String uri, String localName) {
			return null;
		}


		public String getType(String qName) {
			return null;
		}


		public String getValue(String uri, String localName) {
			return null;
		}


		public String getValue(String qName) {
			return null;
		}

	}

}
