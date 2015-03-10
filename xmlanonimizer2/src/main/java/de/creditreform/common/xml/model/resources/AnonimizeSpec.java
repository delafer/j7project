/**
 *
 */
package de.creditreform.common.xml.model.resources;

import java.util.Collections;
import java.util.Map;

import de.creditreform.common.helpers.RuleSet.IReplacement;
import de.creditreform.common.helpers.StringUtils;
import de.creditreform.common.xml.model.DocumentType;
import de.creditreform.common.xml.model.MetaTag;

/**
 * @author tavrovsa
 *
 */
public abstract class AnonimizeSpec implements IAnonimizeSpec {


	public ReplacementType DEFAULT_MODE = ReplacementType.OnlyText;

	public Map<String, MultiValue<String>> data;
	/**
	 *
	 */
	public AnonimizeSpec(Map<String, MultiValue<String>> data) {
		this.data = data;
	}

	public AnonimizeSpec() {
		this.data = Collections.emptyMap();
	}

	public abstract DocumentType getDocumentType();

	public abstract TagData[] getRelevantTags();

	public abstract MetaTag[] getDataTags();

	public abstract IReplacement getNewData(MetaTag tag, int at);

	public ReplacementType getDataReplacementMode(MetaTag tag) {
		return DEFAULT_MODE;
	}


	public String getData(MetaTag tag) {
		return getData(tag, 0);
	}

	public String getData(MetaTag tag, String defValue) {
		String str = getData(tag, 0);
		return !StringUtils.empty(str) ? str : defValue;
	}


	public String getData(MetaTag tag, int at) {
		MultiValue<String> mv = data.get(tag.name());
		return mv != null ? mv.getValue(at) : null;
	}

}
