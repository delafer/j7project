package de.creditreform.common.xml.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.creditreform.common.helpers.StringUtils;

public final class MetaTag implements Serializable, Comparable<MetaTag> {

	private static final long serialVersionUID = -6090589192904945882L;


	public static final boolean CASE_SENSITIVE = false;


	private String name;
	private int ordinal;

	private MetaTag(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(MetaTag o) {
		return this.name.compareTo(o.name);
	}

	public static MetaTag valueOf(String name) {
		String iName = intName(name);
		MetaTag mt;
		if (( mt = TagsCache.cache.get(iName))==null) {
			mt = new MetaTag(iName);
			mt.ordinal = TagsCache.cache.size();
			TagsCache.cache.put(iName, mt);
		}
		return mt;
	}

	public int ordinal() {
		return ordinal;
	}

	public String name() {
		return this.name;
	}

	public MetaTag[] values() {
		MetaTag[] ret = new MetaTag[TagsCache.cache.size()];
		TagsCache.cache.values().toArray(ret);
		return ret;
	}

	private final static String intName(String name) {
		return CASE_SENSITIVE ? name : StringUtils.toLowerCase(name);
	}


    private static class TagsCache {
        static final Map<String, MetaTag> cache;

        static {
            cache = Collections.synchronizedMap(new HashMap<String, MetaTag>());
        }

        private TagsCache() {}
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
		if (null == obj || !(obj instanceof MetaTag)) return false;

		final MetaTag o = (MetaTag) obj;
		return name==o.name ? true : name != null ? name.equals(o.name) : false;

	}

}
