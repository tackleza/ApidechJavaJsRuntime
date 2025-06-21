package com.apidech.lib.apidechjavajsruntime.ts.test;

import java.io.File;
import java.io.IOException;

import com.apidech.lib.apidechjavajsruntime.ts.TypeScriptCompiler;
import com.apidech.lib.apidechjavajsruntime.ts.TypeScriptCompiler.CompilationException;

public class TestTs {
	
	public static void main(String[] args) {
		
	}
	
	public static void testCompileTs(File projectRootDir) {
		try {
			TypeScriptCompiler.builder(new File(".")).compile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompilationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
