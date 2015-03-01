package de.creditreform.common.xml.model.resources;

import java.util.Map;

import de.creditreform.common.xml.model.IEntry.DocumentType;
import de.creditreform.common.xml.model.MetaTag;

public class CommonSpec extends AnonimizeSpec {

	public CommonSpec(Map<String, MultiValue<String>> data) {
		super(data);
	}

	public CommonSpec() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IAnonimizeSpec getNewInstance(Map<String, MultiValue<String>> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentType getDocumentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TagData[] getRelevantTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MetaTag[] getDataTags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNewData(MetaTag tag, int at) {
		// TODO Auto-generated method stub
		return null;
	}

}
