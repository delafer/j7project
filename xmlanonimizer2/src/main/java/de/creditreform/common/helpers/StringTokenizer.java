package de.creditreform.common.helpers;

import de.creditreform.common.xml.model.DocumentType;
import de.creditreform.common.xml.model.EntryText.Content;
import de.creditreform.common.xml.model.EntryXml;

public class StringTokenizer {

	public StringTokenizer() {
	}

	public static void tokenize(EntryXml xml, String s) {
//			 System.out.println("{"+s+"}");
			 int r = -1;

		     int j;
			 if (null != s && (j = s.length())!=0)
			 while (j > 0) if (s.charAt(--j)>' ') {
				 r = j;
				 break;
			 };

			 if (r>=0) {

				 int l = -1;
				 int len = s.length();
				 j = 0;
				 while (j < len) if (s.charAt(j++)>' ') {
					 l = j;
					 break;
				 };

				 l--;
				 r++;


				 if (l>0) {
					 xml.addValue(s.substring(0, l), Content.EmptyChars);
				 }

				 if (l>0 || r<len)
					 xml.addValue(s.substring(l,r), Content.Text);
				 else
					 xml.addValue(s, Content.Text);

				 if (r<len) {
					 xml.addValue(s.substring(r), Content.EmptyChars);
				 }


			 } else {
				xml.addValue(s, Content.EmptyChars);
			 }


	}


	public static void main(String[] args) {
		EntryXml x = new EntryXml("test",null,null, DocumentType.valueOf("ReportResponse"));
		tokenize(x, "                   ");
		System.out.println(x.render());
//		tokenize(x, null);
//		tokenize(x, "");
//		tokenize(x, " ");
//		tokenize(x, "\r\n");
//		tokenize(x, "  ab ");
//		tokenize(x, " a ");
//		tokenize(x, "a");
//		tokenize(x, "ab");
//		tokenize(x, "abcde");
//		tokenize(x, "abcde ");
//		tokenize(x, " abcde");
//		tokenize(x, "                                              ");
	}


}
