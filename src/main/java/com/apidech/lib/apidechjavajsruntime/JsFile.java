package com.apidech.lib.apidechjavajsruntime;

import java.util.HashSet;
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
		Value value = binding.getMember(functionName);
		if(!value.canExecute()) {
			return null;
		}
		return new JsFunction(value);
	}
	
	public boolean hasFunction(String functionName) {
		return getFunction(functionName) != null;
	}
	
	public Set<String> getFunctionNames() {
		Set<String> found = new HashSet<>();
		for(String memberKey : binding.getMemberKeys()) {
			Value value = binding.getMember(memberKey);
			if(value.canExecute()) {
				found.add(memberKey);
			}
		}
		return found;
	}
	
	public Set<JsFunction> getFunctions() {
		Set<JsFunction> found = new HashSet<>();
		for(String memberKey : binding.getMemberKeys()) {
			Value value = binding.getMember(memberKey);
			if(value.canExecute()) {
				found.add(getFunction(memberKey));
			}
		}
		return found;
	}
	
	public Value getBinding() {
		return binding;
	}
	
}
