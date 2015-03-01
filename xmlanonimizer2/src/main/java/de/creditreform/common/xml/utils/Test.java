package de.creditreform.common.xml.utils;

import java.io.IOException;

import de.creditreform.common.helpers.Args;
import de.creditreform.common.helpers.TextFileUtils;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		try {
			TextFileUtils.AbrstractFileParser reader =
			new TextFileUtils.AbrstractFileParser("e:\\aaaa.txt") {

				@Override
				public void processLine(String s, int lineNumber) throws Exception {
					s = s.trim();

					String name = s.endsWith("/text") ? s.substring(0, s.length()-5) + "text" : s;
					name = name.endsWith("/activetext") ? name.substring(0, name.length()-11) + "activetext" : name;
					name = name.endsWith("/stafftext") ? name.substring(0, name.length()-10) + "text" : name;
					name = name.endsWith("/turnovertext") ? name.substring(0, name.length()-13) + "text" : name;
					name = name.endsWith("/companyname") ? name.substring(0, name.length()-12) + "companyname" : name;

					int idx = name.lastIndexOf('/');
					name = name.substring(idx+1);
					name = ""+Character.toUpperCase(name.charAt(0)) + name.substring(1);
					name = name.replaceAll("text", "Txt");
					name = name.replaceAll("active", "Active");
					name = name.replaceAll("group", "Group");
					name = name.replaceAll("info", "Info");
					name = name.replaceAll("industry", "Industry");
					name = name.replaceAll("header", "Header");
					name = name.replaceAll("sheet", "Sheet");
					name = name.replaceAll("purpose", "Purpose");
					name = name.replaceAll("develop", "Develop");
					name = name.replaceAll("profession", "Profession");
					name = name.replaceAll("data", "Data");
					name = name.replaceAll("export", "Export");
					name = name.replaceAll("area", "Area");
					name = name.replaceAll("mode", "Mode");
					name = name.replaceAll("opinion", "Opinion");
					name = name.replaceAll("facts", "Fact");
					name = name.replaceAll("location", "Location");
					name = name.replaceAll("disclaimer", "Disclaimer");
					name = name.replaceAll("behaviour", "Behaviour");
					name = name.replaceAll("range", "Range");
					name = name.replaceAll("company", "Company");
					name = name.replaceAll("statement", "Statement");
					name = name.replaceAll("solvency", "Solvency");
					name = name.replaceAll("finance", "Finance");
					name = name.replaceAll("status", "Status");
					name = name.replaceAll("privat", "Privat");
					name = name.replaceAll("person", "Person");
					name = name.replaceAll("figures", "Figures");
					name = name.replaceAll("hints", "Hints");
					name = name.replaceAll("note", "Note");
					name = name.replaceAll("onofProfe", "onOfProfe");
					name = name.replaceAll("capital", "Capital");
					name = name.replaceAll("shareholder", "Shareholder");


					if (s.contains("shareholdercapital")) name = "Sh_"+name;
					if (s.contains("participationscompany")) name = "Pc_"+name;

					//System.out.println(Args.fill("\t%1,//new", name, s));

					//System.out.println(Args.fill("TagData.as(\"ns2:\", \"%1\", MetaTag.%2),", s, name));
					System.out.println(Args.fill("\tcase %1:", name));

				}

			};
			reader.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
