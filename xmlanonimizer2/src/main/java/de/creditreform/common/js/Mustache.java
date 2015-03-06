package de.creditreform.common.js;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ScriptObject;
import de.creditreform.common.helpers.ResourceReader;

public class Mustache {

  public static void main(String... args) throws Throwable {
    ScriptEngineManager engineManager = new ScriptEngineManager();
    ScriptEngine engine = engineManager.getEngineByName("nashorn");
    Compilable compilingEngine = (Compilable)engine;

    String js = ResourceReader.resource("/js/jsLib.js");
//    System.out.println(js);
    CompiledScript cs = compilingEngine.compile(js);

    Bindings bindings = engine.createBindings();
    bindings.put("var1", "alex");
    Object a = cs.eval(bindings);
    ScriptObjectMirror scm = (ScriptObjectMirror) a;
    System.out.println(scm.get("CrefoNr"));
    System.out.println(scm.get("City"));


//    long t1 = System.currentTimeMillis();
//
//    for (int i = 0; i < 1000; i++) {
//    	 bindings.put("var1", ""+i+"x"+i);
//    	 a = cs.eval(bindings);
//  }
//
//    long t2 = System.currentTimeMillis();
//
//    System.out.println(t2-t1);

  }

}