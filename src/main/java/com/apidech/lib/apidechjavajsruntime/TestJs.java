package com.apidech.lib.apidechjavajsruntime;

public class TestJs {

	public static void main(String[] args) {
		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
		runtime.singleEval(new JsRunnable() {
			
			@Override
			public void run(JsResult result) {
				System.out.println(result.isSuccess);
				System.out.println(result.getResult().asInt());
			}
		}, "1+1");
		System.out.println("END!");
	}
}
