package de.creditreform.common.js;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

public class TestJS {

	public TestJS() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		final ScriptEngineManager manager = new ScriptEngineManager();
		for (final ScriptEngineFactory scriptEngine : manager.getEngineFactories())
		{
		   System.out.println(
		         scriptEngine.getEngineName() + " ("
		       + scriptEngine.getEngineVersion() + ")" );
		   System.out.println(
		         "\tLanguage: " + scriptEngine.getLanguageName() + "("
		       + scriptEngine.getLanguageVersion() + ")" );
		   System.out.println("\tCommon Names/Aliases: ");
		   for (final String engineAlias : scriptEngine.getNames())
		   {
		      System.out.println(engineAlias + " ");
		   }
		}
	}

}
