package de.creditreform.common.helpers;

import java.util.Map;

import de.creditreform.common.xml.model.resources.MultiValue;

public class ValuesHelper {

	public static String getData(Map<String, MultiValue<String>> data, String tagName, int at) {
		MultiValue<String> mv = data.get(tagName);
		String ret = null;
		if (mv != null) {
			ret = mv.getValue(at);
			if (ret == null) ret = mv.getValue();
		}
		return ret;
	}



//	static Map<String, >

//	public static String getBuildInValue(String tagName, int at) {
//		String commonName;
//		if ("randomFirstName".equals(tagName) || "randomLastName".equals(tagName))
//		    commonName = "randomName";
//		else
//			commonName = tagName;
//
//	}

	private static boolean isBuildInVariable(String var) {
		return (var.toLowerCase().startsWith("random"));

	}

}
