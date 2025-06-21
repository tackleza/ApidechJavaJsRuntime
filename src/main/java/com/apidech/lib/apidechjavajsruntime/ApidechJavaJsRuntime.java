package com.apidech.lib.apidechjavajsruntime;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Engine.Builder;
import org.graalvm.polyglot.PolyglotException;

public class ApidechJavaJsRuntime {
	
	private Engine engine;
	
	public ApidechJavaJsRuntime() {
		this(null);
	}
	
	public ApidechJavaJsRuntime(Map<String, String> options) {
		Builder builder = Engine.newBuilder();
		if(options != null) {
			for(Entry<String, String> entry : options.entrySet()) {
				builder.option(entry.getKey(), entry.getValue());
			}
		}
		this.engine = builder.build();
	}
	
	//Create Context
	public JsWorkingSpace createWorkingSpace(File codeDirectory) {
		return new JsWorkingSpace(Context.newBuilder("js")
				.engine(engine)
                .allowAllAccess(true)
                .option("js.commonjs-require", "true")
                .option("js.commonjs-require-cwd", codeDirectory.getAbsolutePath())
                .build());
	}
	
	public JsWorkingSpace createWorkingSpace() {
		return new JsWorkingSpace(Context.newBuilder("js")
				.engine(engine)
                .build());
	}
	
	public void singleEval(JsRunnable runnable, File jsCode) throws IOException {
		JsSource source = JsSource.create(jsCode);
		singleEval(runnable, source);
	}
	
	public void singleEval(JsRunnable runnable, CharSequence jsCode) {
		JsSource source = null;
		try {
			source = JsSource.create(jsCode);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// Need to find better way to return result since it's need to close context.
		singleEval(runnable, source);
	}
	
	public void singleEval(JsRunnable runnable, JsSource source) {
		JsWorkingSpace context = createWorkingSpace();
		JsResult jsResult = new JsResult();
		try {
			jsResult.result = context.eval(source);
			jsResult.isSuccess = true;
		}
		catch (PolyglotException e) {
			jsResult.exception = e;
		}
		
		try {
			runnable.run(jsResult);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			context.close();
		}
	}
	
	//Setup Context allow to define option for example .js lookup
	//Option to check for typescript; and tsc compile
	//Design another set of class. Js and Ts 
	
	//Function Lookup and tools
	
	//Create Source
	
	/*
	 * Find the smartway to capture the console output, maybe callback?
	 * try (Context ctx = Context.newBuilder("js")
                                  .out(capture)
                                  .err(capture)       // optional: capture console.error too
                                  .build()) {
            // 3) Run JS that uses console.log()
            ctx.eval("js", "console.log('Hello', 'from', 'GraalVM');");
        }
	 */
	
	//--- ETC Methods
	public void shutdown() {
		engine.close();
	}
}
