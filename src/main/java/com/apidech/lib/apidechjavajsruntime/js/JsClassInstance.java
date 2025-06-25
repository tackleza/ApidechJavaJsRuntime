package com.apidech.lib.apidechjavajsruntime.js;

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
	
//	public boolean hasMedthod(String methodName) {
//		return value.canInvokeMember(methodName);
//	}
//	
//	public HashSet<JsClassInstanceMethod> getMethods() {
//		System.out.println("getMethods()");
//		HashSet<JsClassInstanceMethod> methods = new HashSet<>();
//		for(String key : value.getMemberKeys()) {
//			Value method = value.getMember(key);
//			if(method.canInvokeMember(key)){
//				methods.add(getMethod(key));
//			}
//		}
//		System.out.println("=== getMethods()");
//		return methods;
//	}
//	
//	public String getName() {
//		return getJsClass().getName();
//	}
}
