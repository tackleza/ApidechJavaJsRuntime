package com.apidech.lib.apidechjavajsruntime.ts;

import java.io.File;

public class TypeScriptCompileResult {
	
	private File outputDir;
	private String errorMessage;
	private Result result;
	
	public TypeScriptCompileResult(File outputDir, Result result, String errorMessage) {
		this.outputDir = outputDir;
		this.result = result;
		this.errorMessage = errorMessage;
	}
	
	public File getOutputDir() {
		return outputDir;
	}
	
	public Result getResult() {
		return result;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public static enum Result{
		SUCCESS, FAILED;
	}
}
