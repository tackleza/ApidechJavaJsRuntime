package com.apidech.lib.apidechjavajsruntime.ts.test;

import java.io.File;
import java.io.IOException;

import com.apidech.lib.apidechjavajsruntime.ApidechJavaJsRuntime;
import com.apidech.lib.apidechjavajsruntime.ts.TypeScriptCompileResult;

public class TestTsRunnable {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Starting");
		TypeScriptCompileResult result = ApidechJavaJsRuntime.compileTypeScript(new File("testts/testlistener"));
		System.out.println("Compile Successfully");
		System.out.println("Result: "+result);
		//Runnable
		
//		File jsRoot = new File("testts/testlistener/dist");
//		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
//		JsWorkingSpace workspace = runtime.createWorkingSpace(jsRoot);
//		JsClass jsClass = workspace.runCompileClass(JsSource.create(new File(jsRoot, "index.js")));
	}
}
