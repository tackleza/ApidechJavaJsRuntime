package com.apidech.lib.apidechjavajsruntime.js;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public abstract class JsAbstract {
	
	private Context context;

	public JsAbstract(Context context) {
		this.context = context;
	}
	
	public Value getBinding() {
		return context.getBindings("js");
	}
	
	public Context getContext() {
		return context;
	}
}
