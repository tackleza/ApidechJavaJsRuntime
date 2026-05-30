package com.apidech.lib.apidechjavajsruntime.js;

import java.io.File;
import java.io.IOException;

import org.graalvm.polyglot.Source;

public class JsSource {

	private static final String MIME_MODULE = "application/javascript+module";
	private static final String MIME_SCRIPT = "application/javascript";

	private Source source;

	public JsSource(Source source) {
		this.source = source;
	}

	public Source getSource() {
		return source;
	}

	/**
	 * Loads a JS file. Module vs script is inferred from the extension:
	 * .mjs is treated as an ES module, everything else as a classic script.
	 * Use {@link #create(File, boolean)} to override.
	 */
	public static JsSource create(File jsFile) throws IOException {
		return create(jsFile, isModuleByExtension(jsFile.getName()));
	}

	/**
	 * Loads a JS file as ES module or classic script depending on {@code asModule}.
	 */
	public static JsSource create(File jsFile, boolean asModule) throws IOException {
		String mime = asModule ? MIME_MODULE : MIME_SCRIPT;
		Source src = Source.newBuilder("js", jsFile)
		                   .mimeType(mime)
		                   .uri(jsFile.toURI())
		                   .build();
		return new JsSource(src);
	}

	public static JsSource create(CharSequence jsCode) {
		return create(jsCode, "unnamed.js");
	}

	public static JsSource create(CharSequence jsCode, String name) {
		// Source.newBuilder(String, CharSequence, String) does no I/O, so the
		// IOException declared by build() is unreachable here.
		try {
			return new JsSource(Source.newBuilder("js", jsCode, name).build());
		} catch (IOException e) {
			throw new IllegalStateException("unexpected I/O failure building in-memory JS source", e);
		}
	}

	private static boolean isModuleByExtension(String fileName) {
		String lower = fileName.toLowerCase();
		return lower.endsWith(".mjs");
	}
}
