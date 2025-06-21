package com.apidech.lib.apidechjavajsruntime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TypeScriptCompiler {
    private final File rootTsProjectDir;
    private String lastOutput;

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
    public String compile() throws IOException, InterruptedException, CompilationException {
        // Ensure 'tsc' is available before attempting compilation
        ensureTscAvailable();

        List<String> cmd = new ArrayList<>();
        cmd.add("tsc"); // rely on tsconfig.json for file list and options

        ProcessBuilder pb = new ProcessBuilder(cmd)
                .directory(rootTsProjectDir)
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