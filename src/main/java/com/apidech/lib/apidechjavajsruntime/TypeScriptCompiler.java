package com.apidech.lib.apidechjavajsruntime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TypeScriptCompiler {
	
    private final File rootJsDir;
    private String lastOutput;

    /**
     * @param rootJsDir the working directory in which tsc will be run
     */
    public TypeScriptCompiler(File rootJsDir) {
        if (rootJsDir == null || !rootJsDir.isDirectory()) {
            throw new IllegalArgumentException("rootJsDir must be an existing directory");
        }
        this.rootJsDir = rootJsDir;
    }

    /**
     * Compile the project according to tsconfig.json in rootJsDir.
     * 
     * @return the combined stdout+stderr from the tsc run
     * @throws IOException if there's a problem launching the process or reading its output
     * @throws InterruptedException if the current thread is interrupted while waiting
     * @throws CompilationException if tsc returns a non-zero exit code
     */
    public String compile() throws IOException, InterruptedException, CompilationException {
        List<String> cmd = new ArrayList<>();
        cmd.add("tsc"); // rely on tsconfig.json for file list and options

        ProcessBuilder pb = new ProcessBuilder(cmd)
                .directory(rootJsDir)
                .redirectErrorStream(true); // merge stderr into stdout

        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        int exitCode = process.waitFor();
        lastOutput = output.toString();

        if (exitCode != 0) {
            throw new CompilationException(
                    "tsc exited with code " + exitCode, exitCode, lastOutput);
        }

        return lastOutput;
    }

    /**
     * @return the output from the most recent compile() invocation (stdout + stderr)
     */
    public String getLastOutput() {
        return lastOutput;
    }

    /**
     * Exception thrown when the TypeScript compiler returns a non-zero exit code.
     */
    public static class CompilationException extends Exception {
        private static final long serialVersionUID = -8154967423501029610L;
		private final int exitCode;
        private final String compilerOutput;

        public CompilationException(String message, int exitCode, String compilerOutput) {
            super(message);
            this.exitCode = exitCode;
            this.compilerOutput = compilerOutput;
        }

        /** @return the exit code returned by tsc */
        public int getExitCode() {
            return exitCode;
        }

        /** @return the full stdout+stderr emitted by tsc */
        public String getCompilerOutput() {
            return compilerOutput;
        }
    }
}