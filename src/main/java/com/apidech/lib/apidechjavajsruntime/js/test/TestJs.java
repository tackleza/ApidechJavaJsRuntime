package com.apidech.lib.apidechjavajsruntime.js.test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.apidech.lib.apidechjavajsruntime.ApidechJavaJsRuntime;
import com.apidech.lib.apidechjavajsruntime.js.JsClass;
import com.apidech.lib.apidechjavajsruntime.js.JsClassInstance;
import com.apidech.lib.apidechjavajsruntime.js.JsClassInstanceMethod;
import com.apidech.lib.apidechjavajsruntime.js.JsFile;
import com.apidech.lib.apidechjavajsruntime.js.JsFunction;
import com.apidech.lib.apidechjavajsruntime.js.JsSource;
import com.apidech.lib.apidechjavajsruntime.js.JsWorkingSpace;

public class TestJs {

	public static void main(String[] args) {
		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
		JsWorkingSpace working = runtime.createWorkingSpace();
//		testJsFile(working);
		testJsClass(working);
	}

	private static void testJsClass(JsWorkingSpace working) {
		try {
			JsFile jsFile = working.compile(JsSource.create(new File("testjs/animal.js")));
			Set<JsClass> classes = jsFile.getClasses();
			System.out.println("== List of class");
			for(JsClass clazz : classes) {
//				System.out.println("- "+clazz.getName()+" ("+clazz.getExtendedClassName()+")");
//				System.out.println("- Is extended? "+clazz.isExtendedClass());
//				System.out.println("Create new instance of it");
//				JsClassInstance instance = clazz.tryNewInstance();
//				if(instance == null) {
//					System.out.println("instance null; skipped!");
//					continue;
//				}
//				System.out.println("Class instance name: "+instance.getName());
//				System.out.println("Methods::");
//				for(String method : instance.getMethodNames()) {
//					System.out.println("- "+method);
//				}
			}
			System.out.println("== End List of class");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void testJsFile(JsWorkingSpace working) {
		JsFile jsFile = null;
		try {
			jsFile = working.compile(JsSource.create(new File("testjs/hello.js")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("== List of functions");
		for(JsFunction fn : jsFile.getFunctions()) {
			System.out.println("- "+fn.getName() + " ("+fn.getArgumentCount()+")");
			for(String name : fn.getArgumentNames()) {
				System.out.println("-- "+name);
			}
			
			System.out.println("-- Execute: "+fn.getName());
			fn.execute("Apidech");
			System.out.println("-- End of Execute");
		}
		System.out.println("== End List of functions");
	}
}
