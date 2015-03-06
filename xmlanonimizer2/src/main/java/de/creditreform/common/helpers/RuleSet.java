package de.creditreform.common.helpers;

import java.util.regex.Pattern;

import de.creditreform.common.xml.model.MetaTag;

public class RuleSet {



//	private boolean isJs(String tag) {
//		char ch;
//
//		for (int i = 0; i < tag.length(); i++) {
//
//		}
//	}


	private static final Pattern pJsFunction = Pattern.compile("\\$[a-zA-Z]+\\s*\\([^\\)]*\\)(\\.[^\\)]*\\))?");
	private static final Pattern pVariable = Pattern.compile("\\{[a-zA-Z]+\\}");

	public void addReplacementRule(MetaTag tag, String rule) {
		if (StringUtils.isEmpty(rule)) return ;
	}



		  public static void main(String[] argv) throws Exception {

			 System.out.println( pVariable.matcher("$Alex	 ( $val $test({abc}) $testing(ab{AB}){}").replaceAll("[]") );
//			    Matcher matcher = pVariable.matcher("$Alex	 ( $val $test({abc}) $testing(ab{AB}){}");
//			    // Find all matches
//			    while (matcher.find()) {
//			      // Get the matching string
//			      String match = matcher.group();
//			      System.out.println(match);
//			    }
//			  }

		  }


		  class ReplacementConstant implements IReplacement {

		  }

		  class ReplacementVariable implements IReplacement {

		  }

		  class ReplacementJs implements IReplacement {

		  }

		  interface IReplacement {

		  }

}
