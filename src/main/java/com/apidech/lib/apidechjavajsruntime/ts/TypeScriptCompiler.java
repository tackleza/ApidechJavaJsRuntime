package com.apidech.lib.apidechjavajsruntime.ts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.apidech.lib.apidechjavajsruntime.ts.TypeScriptCompileResult.Result;

public class TypeScriptCompiler {
	
    private final File rootTsProjectDir;

    /**
     * @param rootTsProjectDir the working directory in which tsc will be run
     */
    public TypeScriptCompiler(File rootTsProjectDir) {
        if (rootTsProjectDir == null || !rootTsProjectDir.isDirectory()) {
            throw new IllegalArgumentException("rootTsProjectDir must be an existing directory");
        }
        this.rootTsProjectDir = rootTsProjectDir;
    }

    /**
     * Compile the project according to tsconfig.json in rootTsProjectDir.
     *
     * @return the combined stdout+stderr from the tsc run
     * @throws IOException if there's a problem launching the process or reading its output,
     *                     or if tsc is not installed or not on PATH
     * @throws InterruptedException if the current thread is interrupted while waiting
     * @throws CompilationException if tsc returns a non-zero exit code
     */
    public TypeScriptCompileResult compile() throws IOException, InterruptedException {
        ensureTscAvailable();
        File outputDir = getOutputDir();

        List<String> cmd = new ArrayList<>();
        cmd.add("tsc");

        ProcessBuilder pb = new ProcessBuilder(cmd)
                .directory(rootTsProjectDir)
                .redirectErrorStream(true);

        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }
        if (process.waitFor() != 0) {
        	return new TypeScriptCompileResult(null, Result.FAILED, output.toString());
        }
        return new TypeScriptCompileResult(outputDir, Result.SUCCESS, null);
    }

    public File getOutputDir() {
        try {
        	
        	File tsConfigFile = new File(rootTsProjectDir, "tsconfig.json");
        	
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(new FileReader(tsConfigFile));

            JSONObject compilerOptions = (JSONObject) root.get("compilerOptions");
            if (compilerOptions == null) {
                return null;
            }

            boolean hasRootDir = compilerOptions.containsKey("rootDir");
            boolean hasOutDir = compilerOptions.containsKey("outDir");

            if(!hasRootDir || !hasOutDir) {
            	return null;
            }
            
            return new File(rootTsProjectDir, compilerOptions.get("outDir").toString());
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Checks that the TypeScript compiler ('tsc') is installed and accessible on the PATH.
     *
     * @throws IOException if the process cannot be started or returns a non-zero exit code
     * @throws InterruptedException if interrupted while waiting for the check process
     */
    private void ensureTscAvailable() throws IOException, InterruptedException {
        ProcessBuilder checkPb = new ProcessBuilder("tsc", "--version")
                .redirectErrorStream(true);
        Process checkProcess;
        try {
            checkProcess = checkPb.start();
        } catch (IOException e) {
            throw new IOException(
                "TypeScript compiler 'tsc' not found on system PATH. " +
                "Please install TypeScript (e.g., 'npm install -g typescript') or add it to your PATH.",
                e
            );
        }

        int code = checkProcess.waitFor();
        if (code != 0) {
            throw new IOException(
                "Unable to run 'tsc --version'. Process exited with code " + code + ". " +
                "Ensure that TypeScript is correctly installed and the 'tsc' executable is functional."
            );
        }
    }

    public static TypeScriptCompiler builder(File rootTsProjectDir) {
    	return new TypeScriptCompiler(rootTsProjectDir);
    }
}