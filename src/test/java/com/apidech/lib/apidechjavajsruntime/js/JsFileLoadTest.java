package com.apidech.lib.apidechjavajsruntime.js;

import com.apidech.lib.apidechjavajsruntime.ApidechJavaJsRuntime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsFileLoadTest {

    private static final File JS_DIR = new File("src/test/resources/js");

    private ApidechJavaJsRuntime runtime;
    private JsWorkingSpace working;

    @BeforeEach
    void setUp() {
        runtime = new ApidechJavaJsRuntime();
        working = runtime.createWorkingSpace(JS_DIR);
    }

    @AfterEach
    void tearDown() {
        working.close();
        runtime.shutdown();
    }

    @Test
    @DisplayName("Loading a plain .js file exposes top-level functions")
    void loadsPlainJsFile() throws Exception {
        JsFile file = working.compile(JsSource.create(new File(JS_DIR, "calculator.js")));

        assertNotNull(file);
        assertTrue(file.hasFunction("add"),   "expected top-level function 'add'");
        assertTrue(file.hasFunction("greet"), "expected top-level function 'greet'");
        assertFalse(file.hasFunction("nope"), "non-existent function should report false");
    }

    @Test
    @DisplayName("Loading an .mjs ES module file returns module exports")
    void loadsMjsFile() throws Exception {
        JsFile file = working.compile(JsSource.create(new File(JS_DIR, "greeter.mjs")));

        assertNotNull(file);
        assertNotNull(file.getResult(), "ESM eval should return module exports");
    }

    @Test
    @DisplayName("Loading a missing file throws")
    void missingFileThrows() {
        assertThrows(Exception.class,
            () -> JsSource.create(new File(JS_DIR, "does-not-exist.js")));
    }

    @Test
    @DisplayName("JsWorkingSpace.hasFunction reflects bindings after compile")
    void workingSpaceHasFunction() throws Exception {
        working.compile(JsSource.create(new File(JS_DIR, "calculator.js")));
        assertTrue(working.hasFunction("add"));
        assertFalse(working.hasFunction("nope"));
    }

    @Test
    @DisplayName("JsSource.create(CharSequence) returns a usable source, never null")
    void createFromCharSequenceNeverNull() {
        JsSource src = JsSource.create("function ping() { return 'pong'; }");
        assertNotNull(src);
        assertNotNull(src.getSource());
    }
}
