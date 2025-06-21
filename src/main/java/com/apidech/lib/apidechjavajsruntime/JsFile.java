package com.apidech.lib.apidechjavajsruntime;

import java.util.Set;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsFile {

	private Context context;
	private Value result;
	private Value binding;
	
	public JsFile(Context context, Value result) {
		this.context = context;
		this.result = result;
		this.binding = context.getBindings("js");
	}
	
	public Value getResult() {
		return result;
	}
	
	public Context getContext() {
		return context;
	}
	
	public JsFunction getFunction(String functionName) {
		return new JsFunction(binding.getMember(functionName));
	}
	
	public boolean hasFunction(String functionName) {
		return binding.canInvokeMember(functionName);
	}
	
	public Set<String> getFunctions() {
		return binding.getMemberKeys();
	}
	
	public Value getBinding() {
		return binding;
	}
	
}
