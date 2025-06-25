package com.apidech.lib.apidechjavajsruntime.js;

import org.graalvm.polyglot.Value;

public class JsClassInstanceMethod {
	
	private JsClassInstance jsClassInstance;
	private Value method;
	
	public JsClassInstanceMethod(JsClassInstance jsClassInstance, Value method) {
		this.jsClassInstance = jsClassInstance;
	}
	
	public JsClassInstance getJsClassInstance() {
		return jsClassInstance;
	}
	
	public Value execute(Object... arguments) {
		return method.execute(arguments);
	}
	
	public void executeVoid(Object... arguments) {
		method.executeVoid(arguments);
	}

	public String getName() {
		return method.getMember("name").asString();
	}
}
