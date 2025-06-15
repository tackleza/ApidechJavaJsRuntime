package com.apidech.lib.apidechjavajsruntime;

public class TestJs {

	public static void main(String[] args) {
		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
		runtime.singleEval("efe.log('test')");
		System.out.println("END!");
	}
}
