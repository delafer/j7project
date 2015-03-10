package de.creditreform.common.xml.model.resources;

import java.util.Map;

import de.creditreform.common.helpers.RuleSet.IReplacement;
import de.creditreform.common.xml.model.DocumentType;
import de.creditreform.common.xml.model.MetaTag;

public class CommonSpec extends AnonimizeSpec {



	private DocumentType docType;
	private TagData[] relevantTags;
	private MetaTag[] dataTags;
	private Map<MetaTag, IReplacement> newValues;
	private Map<MetaTag, ReplacementType> replMode;
	private ReplacementType defaultReplMode;
	private boolean prettyPrintXml;

	public CommonSpec(Map<String, MultiValue<String>> data) {
		super(data);
	}

	public CommonSpec() {
	}

	@Override
	public IAnonimizeSpec getNewInstance(Map<String, MultiValue<String>> data) {
		CommonSpec cs = new CommonSpec();
		cs.docType = this.docType;
		cs.relevantTags = this.relevantTags;
		cs.dataTags = this.dataTags;
		cs.newValues = this.newValues;
		cs.replMode = this.replMode;
		cs.defaultReplMode = this.defaultReplMode;
		cs.data = data;
		return cs;
	}

	public void setNewValues(Map<MetaTag, IReplacement> newValues) {
		this.newValues = newValues;
	}

	public void setReplMode(Map<MetaTag, ReplacementType> replMode) {
		this.replMode = replMode;
	}

	public void setDefaultReplMode(ReplacementType defaultReplMode) {
		this.defaultReplMode = defaultReplMode;
	}

	public DocumentType getDocumentType() {
		return docType;
	}

	public void setDocType(DocumentType docType) {
		this.docType = docType;
	}

	public void setRelevantTags(TagData[] relevantTags) {
		this.relevantTags = relevantTags;
	}

	public TagData[] getRelevantTags() {
		return relevantTags;
	}


	public MetaTag[] getDataTags() {
		return dataTags;
	}

	public void setDataTags(MetaTag[] dataTags) {
		this.dataTags = dataTags;
	}

	@Override
	public ReplacementType getDataReplacementMode(MetaTag tag) {

		ReplacementType type = replMode.get(tag);

		return type != null ? type : defaultReplMode;
	}

	public IReplacement getNewData(MetaTag tag, int at) {
		return newValues.get(tag);

	}

	public boolean isPrettyPrintXml() {
		return prettyPrintXml;
	}

	public void setPrettyPrintXml(boolean prettyPrintXml) {
		this.prettyPrintXml = prettyPrintXml;
	}


}
