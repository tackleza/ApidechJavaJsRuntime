package com.apidech.lib.apidechjavajsruntime.js;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsFile extends JsAbstract {

	private Value result;
	private Value binding;
	
	public JsFile(Context context, Value result) {
		super(context);
		this.result = result;
		this.binding = context.getBindings("js");
	}
	
	public Value getResult() {
		return result;
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
	
	/**
     * @return all top-level JS constructors
     */
    public Set<JsClass> getClasses() throws IOException {
    	Set<JsClass> found = new HashSet<>();
		for(String memberKey : binding.getMemberKeys()) {
			Value value = binding.getMember(memberKey);
			if(value.canInstantiate()) {
				found.add(getClass(memberKey));
			}
		}
		return found;
    }
    
    public JsClass getClass(String className) {
    	Value clazz = binding.getMember(className);
    	if(!clazz.canInstantiate()) {
    		throw new RuntimeException("[JS] "+className+" is not a class");
    	}
    	return new JsClass(getContext(), clazz);
    }
}
