package com.apidech.lib.apidechjavajsruntime;

import java.io.File;

public class ApidechJavaJsBuilder {
	
	private File jsRootDirectory;
	
	public void withRequire(File jsRootDirectory) {
		this.jsRootDirectory = jsRootDirectory;
	}
	
	public void build() {
		
	}
}
