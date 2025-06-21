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
		for(String name : jsFile.getFunction("hello").getMembers()) {
			System.out.println("- "+name);
		}
		
//		System.out.println(jsFile.getFunction("hello").getName());
		
//		try {
//			JsClass jsClass = working.runCompileClass(JsSource.create(new File("testjs/animal.js")));
//			for(String name : jsClass.getFunctions()) {
//				System.out.println(name);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
