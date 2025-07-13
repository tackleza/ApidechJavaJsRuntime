package com.apidech.lib.apidechjavajsruntime.js;

import org.graalvm.polyglot.PolyglotException;

import com.apidech.lib.apidechjavajsruntime.misc.MemberValue;

public class JsResult {
	
	private boolean isSuccess;
	private MemberValue result;
	private PolyglotException exception;
	
	public JsResult(MemberValue result) {
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
	
	public MemberValue getResult() {
		return result;
	}
	
	public PolyglotException getException() {
		return exception;
	}
}
