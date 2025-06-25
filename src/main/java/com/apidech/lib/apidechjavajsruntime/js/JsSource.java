package com.apidech.lib.apidechjavajsruntime.js;

import java.io.File;
import java.io.IOException;

import org.graalvm.polyglot.Source;

public class JsSource {
	
	private Source source;
	
	public JsSource(Source source) {
		this.source = source;
	}
	
	public Source getSource() {
		return source;
	}
	
	public static JsSource create(File jsFile) throws IOException {
		return new JsSource(Source.newBuilder("js", jsFile).build());
	}
	
	public static JsSource create(CharSequence jsCode) {
		try {
			return create(jsCode, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Should not be here at all, since no I/O
		return null;
	}
	
	public static JsSource create(CharSequence jsCode, String name) throws IOException {
		return new JsSource(Source.newBuilder("js", jsCode, name).build());
	}
}
