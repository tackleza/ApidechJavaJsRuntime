package com.apidech.lib.apidechjavajsruntime;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Engine.Builder;
import org.graalvm.polyglot.PolyglotException;

import com.apidech.lib.apidechjavajsruntime.js.JsResult;
import com.apidech.lib.apidechjavajsruntime.js.JsRunnable;
import com.apidech.lib.apidechjavajsruntime.js.JsSource;
import com.apidech.lib.apidechjavajsruntime.js.JsWorkingSpace;
import com.apidech.lib.apidechjavajsruntime.misc.MemberValue;
import com.apidech.lib.apidechjavajsruntime.ts.TypeScriptCompileResult;
import com.apidech.lib.apidechjavajsruntime.ts.TypeScriptCompiler;

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
		JsResult jsResult = null;
		try {
			jsResult = new JsResult(new MemberValue(context.eval(source)));
		}
		catch (PolyglotException e) {
			jsResult = new JsResult(e);
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
	
	public static TypeScriptCompileResult compileTypeScript(File rootDir) throws IOException, InterruptedException {
		return new TypeScriptCompiler(rootDir).compile();
	}
	
	//--- ETC Methods
	public void shutdown() {
		engine.close();
	}
}
