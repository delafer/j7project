package de.creditreform.common.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.creditreform.common.xml.model.MetaTag;
import de.creditreform.common.xml.model.resources.MultiValue;

public class RuleSet {



	public Map<MetaTag, IReplacement> getRules() {
		return nvRules;
	}


	public Set<String> getVariables() {
		return variables;
	}

	private Map<MetaTag, IReplacement> nvRules;
	private Set<String> variables;

	{
		nvRules = new HashMap<MetaTag, IReplacement>();
		variables = new HashSet<String>();
	}


	private static final Pattern pJsFunction = Pattern.compile("\\$[a-zA-Z]+\\s*\\([^\\)]*\\)(\\.[^\\)]*\\))?");
	private static final Pattern pVariable = Pattern.compile("\\{[a-zA-Z_]+\\}");

	public void addReplacementRule(MetaTag tag, String rule) throws ScriptException {
		if (StringUtils.isEmpty(rule)) return ;
		if (nvRules.containsKey(tag)) return ;

		if (isJsRule(rule)) addJsRule(tag, rule, variables);
		else
		if (isVariableRule(rule)) addVarRule(tag, rule, variables);
		else
		addConstRule(tag, rule);
	}




	private void addConstRule(MetaTag tag, String rule) {
		nvRules.put(tag, new ReplacementConstant(rule));
	}


	private void addVarRule(MetaTag tag, String rule, Set<String> variablesGlobal) {
		nvRules.put(tag, new ReplacementVariable(rule, variablesGlobal));
	}


	private void addJsRule(MetaTag tag, String rule, Set<String> variables) throws ScriptException {
			nvRules.put(tag, new ReplacementJs(rule, variables));
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
//
			  ReplacementJs rv = new ReplacementJs("$revert({alex})+'_2'", new HashSet<String>());

			  MultiValue<String> mv = new MultiValue<String>();
			  mv.addValue("abc");
			  mv.addValue("123");

			  MultiValue<String> mv2 = new MultiValue<String>();
			  mv2.addValue("cde");
			  mv2.addValue("uuu");


			  Map<String, MultiValue<String>> mp = new HashMap<String, MultiValue<String>>();
			  mp.put("alex", mv);
			  mp.put("sascha", mv2);


			  System.out.println(rv.getNewValue(0, mp));

//			  String str = "{alex}{}{alex}{alex}{sasha}{a}{alex}aaa{alex}{aaa}";
//			  String ret = str;
//			    Matcher matcher = pVariable.matcher(str);
//			    while (matcher.find()) {
//			    	String found = matcher.group();
//			      ret = ret.replaceFirst(Pattern.quote(found), found.substring(1, found.length()-1));
//			    }
//			    System.out.println(ret);


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

			  private List<String> vars;
			  private String rawStr;

			  public ReplacementVariable(String str, Set<String> variablesGlobal) {
				 this.rawStr = str;
				 init(str, variablesGlobal);

			}


			private void init(String str, Set<String> variablesGlobal) {

				vars = new ArrayList<String>();
			    Matcher matcher = pVariable.matcher(str);
			    while (matcher.find()) {
			      String found = matcher.group();
			      String varName = found.substring(1, found.length()-1);
			      vars.add(varName);
			      variablesGlobal.add(varName);
			    }
			}



			@Override
			public String toString() {
				return String.format("ReplacementVariable [vars=%s]", vars);
			}


			public String getNewValue(int at, Map<String, MultiValue<String>> data) {
					String ret = rawStr;
					for (String next : vars) {
						ret = ret.replaceFirst(Pattern.quote("{"+next+"}"), getData(data, next, at));
					}
					return ret;

				}

			public String getData(Map<String, MultiValue<String>> data, String tagName, int at) {
				MultiValue<String> mv = data.get(tagName);
				return mv != null ? mv.getValue(at) : null;
			}

		  }

		  static class ReplacementJs implements IReplacement {


		static  ScriptEngineManager engineManager = new ScriptEngineManager();
		static ScriptEngine engine = engineManager.getEngineByName("JavaScript");
		private  CompiledScript skript;
		private List<String> vars;

		 public ReplacementJs(String str, Set<String> variablesGlobal) throws ScriptException {
			 init(str, variablesGlobal);
		 }


		private void init(String str, Set<String> variablesGlobal) throws ScriptException {

			    String ret = str;
			    Matcher matcher = pVariable.matcher(str);
			    vars = new ArrayList<String>();

			    while (matcher.find()) {
			      String found = matcher.group();
			      String varName = found.substring(1, found.length()-1);
			      vars.add(varName);
			      variablesGlobal.add(varName);
			      ret = ret.replaceFirst(Pattern.quote(found), varName);
			    }


			    StringBuilder skriptTxt = new StringBuilder();
			    skriptTxt.append(ResourceReader.resource("/js/jsLib.js"));
			    skriptTxt.append(StringUtils.LF);
			    skriptTxt.append("result=").append(ret).append(';');
			    skript = compilingEngine().compile(skriptTxt.toString());


		}

			private Compilable compilingEngine() {
			return (Compilable)engine;
		}


			public String getNewValue(int at, Map<String, MultiValue<String>> data) {

				Bindings bindings = engine.createBindings();

				for (String next : vars) {
					bindings.put(next, getData(data, next, at));
				}
				Object res = "";
				try {
					res = skript.eval(bindings);
				} catch (ScriptException e) {e.printStackTrace();}
				return res.toString();
			}


			public String getData(Map<String, MultiValue<String>> data, String tagName, int at) {
				MultiValue<String> mv = data.get(tagName);
				return mv != null ? mv.getValue(at) : null;
			}

		  }

		  public interface IReplacement {
			  public String getNewValue(int at, Map<String, MultiValue<String>> data);
		  }


}
