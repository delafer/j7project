package de.creditreform.common.xml.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.creditreform.common.helpers.StringUtils;

public final class DocumentType implements Serializable, Comparable<DocumentType> {

	private static final long serialVersionUID = -6090589192904945882L;


	public static final boolean CASE_SENSITIVE = false;


	public static final DocumentType TYPE_UNKNOWN = DocumentType.valueOf("Unknown");

	private String name;
	private int ordinal;

	private DocumentType(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(DocumentType o) {
		return this.name.compareTo(o.name);
	}

	public static DocumentType valueOf(String name) {
		String iName = intName(name);
		DocumentType mt;
		if (( mt = TypesCache.cache.get(iName))==null) {
			mt = new DocumentType(iName);
			mt.ordinal = TypesCache.cache.size();
			TypesCache.cache.put(iName, mt);
		}
		return mt;
	}

	public int ordinal() {
		return ordinal;
	}

	public String name() {
		return this.name;
	}

	public String tag() {
		return this.name;
	}

	public DocumentType[] values() {
		DocumentType[] ret = new DocumentType[TypesCache.cache.size()];
		TypesCache.cache.values().toArray(ret);
		return ret;
	}

	private final static String intName(String name) {
		return CASE_SENSITIVE ? name : StringUtils.toLowerCase(name);
	}


    private static class TypesCache {
        static final Map<String, DocumentType> cache;

        static {
            cache = Collections.synchronizedMap(new HashMap<String, DocumentType>());
        }

        private TypesCache() {}
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (name == null) ? 0 : name.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (null == obj || !(obj instanceof DocumentType)) return false;

		final DocumentType o = (DocumentType) obj;
		return name==o.name ? true : name != null ? name.equals(o.name) : false;

	}

}
