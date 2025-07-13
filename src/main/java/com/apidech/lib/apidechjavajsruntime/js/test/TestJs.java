package com.apidech.lib.apidechjavajsruntime.js.test;

public class TestJs {

	public static void main(String[] args) {
//		ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
//		JsWorkingSpace working = runtime.createWorkingSpace();
//		testGlobalVar(working);
//		testGlobalFunction(working);
//		testClass(working);
//		testClassMember(working);
	}

//	private static void testClassMember(JsWorkingSpace working) {
//		try {
//			System.out.println("testClassMember()");
//			JsFile jsFile = working.compile(JsSource.create(new File("testjs/testclass.js")));
//			JsClass catClass = jsFile.getClass("Cat");
//			JsClassInstance myCat = catClass.newInstance("Lord");
//			myCat.getMethod("walk").execute();
//			Set<JsClassInstanceMethod> methods = myCat.getMethods();
//			for(JsClassInstanceMethod method : methods) {
//				System.out.println(method.getName());
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private static void testClass(JsWorkingSpace working) {
//		try {
//			JsFile jsFile = working.compile(JsSource.create(new File("testjs/testclass.js")));
//			Set<JsClass> classes = jsFile.getClasses();
//			for(JsClass clazz : classes) {
//				System.out.println("- "+clazz.getName()+" | Is Extended: "+clazz.isExtendedClass()+ " | Extended: "+clazz.getExtendedClassName());
//				
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	//Success!
//	private static void testGlobalVar(JsWorkingSpace working) {
//		try {
//			JsFile jsFile = working.compile(JsSource.create(new File("testjs/globalvar.js")));
//			Set<MemberValue> vars = jsFile.getVars();
//			for(MemberValue var : vars) {
//				System.out.println(var.getName() + ">> "+var.asInt());
//			}
//			MemberValue value = jsFile.getVarSafe("c");
//			System.out.println("Value to c is "+value.asInt());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	//Success!
//	private static void testGlobalFunction(JsWorkingSpace working) {
//		try {
//			JsFile jsFile = working.compile(JsSource.create(new File("testjs/globalfunction.js")));
//			Set<JsFunction> functions = jsFile.getFunctions();
//			System.out.println("Functions: (Total Found "+functions.size()+")");
//			for(JsFunction function : functions) {
//				System.out.println("- "+function.getName());
//				System.out.println("With args Count -> "+function.getArgumentCount());
//				for(String arg : function.getArgumentNames()) {
//					System.out.println("---> "+arg);
//				}
//			}
//			jsFile.getFunction("helloToConsole").execute("Tackle");
//			int test = jsFile.getFunction("calculatorPlus").execute(1, 3).getResult().asInt();
//			System.out.println("CalculatorPlus: "+test);
//			String testName = jsFile.getFunction("returnHelloMessage").execute("Tackle").getResult().asString();
//			System.out.println("Hello Message Test -> "+testName);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
