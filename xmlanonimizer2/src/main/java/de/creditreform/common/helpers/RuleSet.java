package de.creditreform.common.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.creditreform.common.xml.model.MetaTag;
import de.creditreform.common.xml.model.resources.MultiValue;

public class RuleSet {



//	private boolean isJs(String tag) {
//		char ch;
//
//		for (int i = 0; i < tag.length(); i++) {
//
//		}
//	}


	private Map<MetaTag, IReplacement> nvRules;

	{
		nvRules = new HashMap<MetaTag, IReplacement>();
	}


	private static final Pattern pJsFunction = Pattern.compile("\\$[a-zA-Z]+\\s*\\([^\\)]*\\)(\\.[^\\)]*\\))?");
	private static final Pattern pVariable = Pattern.compile("\\{[a-zA-Z]+\\}");

	public void addReplacementRule(MetaTag tag, String rule) {
		if (StringUtils.isEmpty(rule)) return ;
		if (nvRules.containsKey(tag)) return ;

		if (isJsRule(rule)) addJsRule(tag, rule);
		else
		if (isVariableRule(rule)) addVarRule(tag, rule);
		else
		addConstRule(tag, rule);
	}




	private void addConstRule(MetaTag tag, String rule) {
		nvRules.put(tag, new ReplacementConstant(rule));
	}


	private void addVarRule(MetaTag tag, String rule) {
		// TODO Auto-generated method stub

	}


	private void addJsRule(MetaTag tag, String rule) {
		// TODO Auto-generated method stub

	}


	public boolean isJsRule(String rule) {
		 Matcher matcher = pJsFunction.matcher(rule);
		 return matcher.find();
	}

	public boolean isVariableRule(String rule) {
		 Matcher matcher = pVariable.matcher(rule);
		 return matcher.find();
	}


		  public static void main(String[] argv) throws Exception {
//			 System.out.println( pVariable.matcher("$Alex	 ( $val $test({abc}) $testing(ab{AB}){}").replaceAll("[]") );
//			    Matcher matcher = pVariable.matcher("$Alex	 ( $val $test({abc}) $testing(ab{AB}){}");
//			    // Find all matches
//			    while (matcher.find()) {
//			      // Get the matching string
//			      String match = matcher.group();
//			      System.out.println(match);
//			    }

			  ReplacementVariable rv = new ReplacementVariable("{");
			  System.out.println(rv.toString());

			  }


		  static class ReplacementConstant implements IReplacement {

			  String staticValue;

			  public ReplacementConstant(String constant) {
				this.staticValue = constant;
			}


			public String getNewValue(int at, Map<String, MultiValue<String>> data) {
					return this.staticValue;
			  }

		  }

		  static class ReplacementVariable implements IReplacement {

			  List<IChunk> chunks = new ArrayList<IChunk>();


			  public ReplacementVariable(String str) {

				 init(str);

			}




			@Override
			public String toString() {
				StringBuilder sb = new StringBuilder();
				for (IChunk iChunk : chunks) {
					sb.append(iChunk.render());
				}
				return sb.toString();
			}




			private void init(String str) {
				char ch;
				boolean var = false;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < str.length(); i++) {
					 ch = str.charAt(i);
					 if (ch=='{') {
						 flash(sb, var, "");
						 var = true;
					 }
					 else
				     if (ch=='}') {
						 flash(sb, var, "");
						 var = false;
				     } else {
				    	 sb.append(ch);
				     }
				}
				flash(sb, var, "");
			}




			private void flash(StringBuilder sb, boolean isVar, String  s) {
				if (sb.length()>0) {
					 String txt = sb.toString();
					 chunks.add(isVar ? new ChunkVar(txt) : new ChunkText(txt));
					 sb.setLength(0);
				 } else {
					 if (isVar) {
						 chunks.add(new ChunkText("{}"));
					 } else {
						 chunks.add(new ChunkText(s));
					 }
				 }
			}




			public String getNewValue(int at, Map<String, MultiValue<String>> data) {
					return null;
				}
		  }

		  static class ReplacementJs implements IReplacement {

			public String getNewValue(int at, Map<String, MultiValue<String>> data) {
				return null;
			}

		  }

		  interface IReplacement {
			  public String getNewValue(int at, Map<String, MultiValue<String>> data);
		  }

		  interface IChunk {
			  public String render();
		  };

		  static class ChunkText implements IChunk {
			String text;
			public ChunkText(String text) {
				this.text = text;
			}


			public String render() {
				return text;
			}

		  };

		  static  class ChunkVar implements IChunk {
			 String varName;
				public ChunkVar(String varName) {
					this.varName = varName;
				}


				public String render() {
					return "$"+varName+"$";
				}

		  };

}
