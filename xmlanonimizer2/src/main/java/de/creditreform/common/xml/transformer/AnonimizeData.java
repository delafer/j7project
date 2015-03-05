package de.creditreform.common.xml.transformer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.creditreform.common.xml.model.DocumentType;
import de.creditreform.common.xml.model.MetaTag;
import de.creditreform.common.xml.model.resources.IAnonimizeSpec;
import de.creditreform.common.xml.model.resources.IAnonimizeSpec.TagData;
import de.creditreform.common.xml.model.resources.ReportResponse;

public final class AnonimizeData implements Serializable {


	private static final long serialVersionUID = -1420764592235927044L;


	/**  Lazy-loaded Singleton, by Bill Pugh **/
    private static class Holder {
        private final static AnonimizeData INSTANCE = new AnonimizeData();
    }

    public static AnonimizeData instance() {
        return Holder.INSTANCE;
    }


    private Map<String, DocumentType> types; //"ns2:reportResponse"->DocumentType.ReportResponse

	private Map<DocumentType, Map<String, MetaTag>> metaTags;//"ReportResponse"->["Lieferung/Header/SendeZeit" -> MetaTag.InquireySendTime]

	private Set<String> dataTags;

	private Map<DocumentType, IAnonimizeSpec> processors;

	private AnonimizeData() {
		init();
	}


	private IAnonimizeSpec[] formats;


	private void init() {


		formats = new IAnonimizeSpec[] {new ReportResponse()};

		// register xml tags
		types = new HashMap<String, DocumentType>();
		metaTags = new HashMap<DocumentType, Map<String, MetaTag>>();
		dataTags = new HashSet<String>();
		processors = new HashMap<DocumentType, IAnonimizeSpec>();

		for (IAnonimizeSpec next : formats) {

			processors.put(next.getDocumentType(), next);

			DocumentType dType = next.getDocumentType();
			types.put(dType.tag(), dType);
			Map<String, MetaTag> subMap = new HashMap<String, MetaTag>();
			metaTags.put(dType, subMap);
			fillTagsByType(next, subMap);

			fillDataTags(dataTags, next);

		}

	}


	private void fillDataTags(Set<String> dataTags, IAnonimizeSpec spec) {

		final MetaTag[] mta = spec.getDataTags();
		StringBuilder sb = new StringBuilder();

		for (MetaTag next : mta) {
		    sb.setLength(0);
			sb.append(spec.getDocumentType().name()).append(':').append(next.name());
			dataTags.add(sb.toString());
		}


	}


	private void fillTagsByType(IAnonimizeSpec spec, Map<String, MetaTag> subMap) {
		TagData[] td = spec.getRelevantTags();
		for (TagData next : td) {
			subMap.put(next.getPath(), next.getTag());
		}

	}


	public final Map<String, MetaTag> getMetaTags(DocumentType type) {
		return metaTags.get(type);
	}


	public DocumentType getTypes(String qName) {
		return types.get(qName);
	}

	public boolean isDataTag(DocumentType type, String tagName) {
		StringBuilder sb = new StringBuilder();
		sb.append(type.name()).append(':').append(tagName);
		return dataTags.contains(sb.toString());
	}

	public IAnonimizeSpec getProcessor(DocumentType type) {
		return this.processors.get(type);
	}

}
