package de.creditreform.common.xml.model.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;

import de.creditreform.common.helpers.StringUtils;
import de.creditreform.common.xml.model.DocumentType;
import de.creditreform.common.xml.model.MetaTag;

public interface IAnonimizeSpec {


	public enum ReplacementType {OnlyText, TextRecursive, ReplaceAll, Ignore, RemoveBlock;

	public static ReplacementType RT_UNKNOWN = ReplacementType.Ignore;
	public static ReplacementType RT_DEFAULT = ReplacementType.OnlyText;
	ReplacementType() {
		Values.values.put(this.name().toLowerCase(), this);
	}

	public static ReplacementType valueBy(String name, ReplacementType defValue) {
		ReplacementType ret = Values.values.get(name.toLowerCase());
		return ret != null ? ret : defValue;
	}

	private static class Values {
		static final Map<String, ReplacementType> values;
		static {
			values = new HashMap<String, ReplacementType>();
		}
		private Values() {};
	}


	};

	public DocumentType getDocumentType();

	public TagData[] getRelevantTags();

	public MetaTag[] getDataTags();

	public String getNewData(MetaTag tag, int at);

	public ReplacementType getDataReplacementMode(MetaTag tag);


	public String getData(MetaTag tag);

	public String getData(MetaTag tag, int at);


	public IAnonimizeSpec getNewInstance(Map<String, MultiValue<String>> data);

	public static class TagData {
		public String path;
		public MetaTag tag;

		private static TreeSet<String> d = new TreeSet<String>();

		public TagData(String path, MetaTag tag) {

			if (d.contains(path)) {
				System.out.println(path+" = "+tag);
				System.exit(-1);
			}
			d.add(path);

			this.path = path;
			this.tag = tag;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public MetaTag getTag() {
			return tag;
		}

		public void setTag(MetaTag tag) {
			this.tag = tag;
		}

		@Override
		public String toString() {
			return String.format("TagData [%s<-%s]", tag, path);
		}

		public static TagData as(String path, MetaTag tag) {
			return new TagData(path, tag);

		}

		public static TagData as(String prefix, String path, MetaTag tag) {

			return !StringUtils.empty(prefix) ? new TagData(build(prefix, path), tag) : new TagData(path, tag);

		}

		private static String SEPR = Pattern.quote("/");

		public static String build(String pref, String path) {
			String[] chunks = path.split(SEPR);
			StringBuilder sb = new StringBuilder(path.length()+(pref.length()*chunks.length));
			for (String next : chunks) {
				if (sb.length()>0) sb.append('/');
				sb.append(pref).append(next);
			}
			return sb.toString();
		}


	}
}
