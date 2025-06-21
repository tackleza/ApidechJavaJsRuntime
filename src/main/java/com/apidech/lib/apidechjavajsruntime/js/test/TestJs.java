package com.apidech.lib.apidechjavajsruntime.js.test;

import java.io.File;
import java.io.IOException;

import com.apidech.lib.apidechjavajsruntime.ApidechJavaJsRuntime;
import com.apidech.lib.apidechjavajsruntime.js.JsClass;
import com.apidech.lib.apidechjavajsruntime.js.JsFile;
import com.apidech.lib.apidechjavajsruntime.js.JsSource;
import com.apidech.lib.apidechjavajsruntime.js.JsWorkingSpace;

public class TestJs {

	public static void main(String[] args) {
		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
		JsWorkingSpace working = runtime.createWorkingSpace();
		testJsFile(working);
		testJsClass(working);
		
	}

	private static void testJsClass(JsWorkingSpace working) {
		try {
			JsClass jsClass = working.runCompileClass(JsSource.create(new File("testjs/animal.js")));
			for(String className : jsClass.getClasses()) {
				System.out.println(className);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void testJsFile(JsWorkingSpace working) {
		JsFile jsFile = null;
		try {
			jsFile = working.runCompileSource(JsSource.create(new File("testjs/hello.js")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String name : jsFile.getFunction("hello").getMembers()) {
			System.out.println("- "+name);
		}
	}
}
