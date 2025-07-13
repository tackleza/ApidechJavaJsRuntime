package com.apidech.lib.apidechjavajsruntime.js;

import java.util.HashSet;
import java.util.Set;

import org.graalvm.polyglot.Value;

public class JsClassInstance extends JsClass {
	
	private Value instance;
	
	public JsClassInstance(JsClass jsClass, Value value, Value instance) {
		super(jsClass.getContext(), value);
		this.instance = instance;
	}
	
	public JsClassInstanceMethod getMethod(String methodName) {
		if(!instance.canInvokeMember(methodName)) {
			return null;
		}
		Value method = instance.getMember(methodName);
		return new JsClassInstanceMethod(this, method);
	}
	
	public Set<JsClassInstanceMethod> getMethods() {
		Set<String> methodNames = getMethodNames();
		HashSet<JsClassInstanceMethod> methods = new HashSet<>();
		for(String name : methodNames) {
			Value method = instance.getMember(name);
			methods.add(new JsClassInstanceMethod(this, method));
		}
		return methods;
	}
}
