package com.apidech.lib.apidechjavajsruntime;

import java.util.Map;
import java.util.Map.Entry;

import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Engine.Builder;

public class ApidechJavaJsRuntime {
	
	private Engine engine;
	
	public ApidechJavaJsRuntime() {
		this(null);
	}
	
	public ApidechJavaJsRuntime(Map<String, String> options) {
		Builder builder = Engine.newBuilder();
		if(options != null) {
			for(Entry<String, String> entry : options.entrySet()) {
				builder.option(entry.getKey(), entry.getValue());
			}
		}
		this.engine = builder.build();
	}
	
	
	//Create Context
	
	//Setup Context allow to define option for example .js lookup
	//Option to check for typescript; and tsc compile
	//Design another set of class. Js and Ts 
	
	//Function Lookup and tools
	
	//Create Source
}
