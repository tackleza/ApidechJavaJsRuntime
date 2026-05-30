package com.apidech.lib.apidechjavajsruntime.js;

import org.graalvm.polyglot.PolyglotException;

import com.apidech.lib.apidechjavajsruntime.misc.MemberValue;

public class JsResult {

	private final boolean isSuccess;
	private final MemberValue result;
	private final Throwable exception;

	public JsResult(MemberValue result) {
		this.isSuccess = true;
		this.result = result;
		this.exception = null;
	}

	public JsResult(PolyglotException exception) {
		this((Throwable) exception);
	}

	public JsResult(Throwable exception) {
		this.isSuccess = false;
		this.result = null;
		this.exception = exception;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public MemberValue getResult() {
		return result;
	}

	/**
	 * @return the underlying failure, or {@code null} on success.
	 *         May be a {@link PolyglotException} or any other host-side throwable.
	 */
	public Throwable getException() {
		return exception;
	}
}
