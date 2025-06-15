package com.apidech.lib.apidechjavajsruntime;

import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

public class JsResult {
	
	protected boolean isSuccess;
	protected Value result;
	protected PolyglotException exception;
	
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
