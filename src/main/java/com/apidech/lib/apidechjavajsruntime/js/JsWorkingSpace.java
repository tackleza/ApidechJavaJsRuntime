package com.apidech.lib.apidechjavajsruntime.js;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class JsWorkingSpace {
	
	private Context context;
	
	public JsWorkingSpace(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	public Value eval(JsSource source) {
		return context.eval(source.getSource());
	}
	
	public JsFile compile(JsSource source) {
		return new JsFile(context, context.eval(source.getSource()));
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
