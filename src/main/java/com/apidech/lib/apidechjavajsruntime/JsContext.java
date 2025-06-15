package com.apidech.lib.apidechjavajsruntime;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsContext {
	
	private Context context;
	
	public JsContext(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	public Value eval(JsSource source) {
		return context.eval(source.getSource());
	}
	
	public Object executeFunction(String functionName) {
		return null;
	}
	
	public boolean isFunctionExists() {
		return false;
	}
	
	public void close() {
		context.close();
	}
	
	public void close(boolean cancelIfExecuting) {
		context.close(cancelIfExecuting);
	}
}
