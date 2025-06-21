package com.apidech.lib.apidechjavajsruntime.js;

import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

public class JsResult {
	
	private boolean isSuccess;
	private Value result;
	private PolyglotException exception;
	
	public JsResult(Value result) {
		this.isSuccess = true;
		this.result = result;
	}
	
	public JsResult(PolyglotException exception) {
		this.isSuccess = false;
		this.exception = exception;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public Value getResult() {
		return result;
	}
	
	public PolyglotException getException() {
		return exception;
	}
}
