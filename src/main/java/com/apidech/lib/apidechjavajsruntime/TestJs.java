package com.apidech.lib.apidechjavajsruntime;

import java.io.File;
import java.io.IOException;

public class TestJs {

	public static void main(String[] args) {
		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
		JsWorkingSpace working = runtime.createWorkingSpace();
		JsFile jsFile = null;
		try {
			jsFile = working.runCompileSource(JsSource.create(new File("testjs/hello.js")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jsFile.getFunction("hello").executeVoid("Tackle");
	}
}
