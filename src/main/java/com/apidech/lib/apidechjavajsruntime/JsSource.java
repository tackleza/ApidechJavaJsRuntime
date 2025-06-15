package com.apidech.lib.apidechjavajsruntime;

import org.graalvm.polyglot.Source;

public class JsSource {
	
	private Source source;
	
	public JsSource(Source source) {
		this.source = source;
	}
	
	public Source getSource() {
		return source;
	}
}
