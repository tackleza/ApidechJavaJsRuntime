package com.apidech.lib.apidechjavajsruntime;

import com.apidech.lib.apidechjavajsruntime.js.JsResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingleEvalTest {

    private ApidechJavaJsRuntime runtime;

    @BeforeEach
    void setUp() {
        runtime = new ApidechJavaJsRuntime();
    }

    @AfterEach
    void tearDown() {
        runtime.shutdown();
    }

    @Test
    @DisplayName("singleEval returns a successful JsResult for valid JS")
    void singleEvalSuccess() {
        AtomicReference<JsResult> captured = new AtomicReference<>();
        runtime.singleEval(captured::set, "1 + 2");

        JsResult r = captured.get();
        assertNotNull(r);
        assertTrue(r.isSuccess());
        assertEquals(3, r.getResult().asInt());
        assertNull(r.getException());
    }

    @Test
    @DisplayName("singleEval surfaces JS syntax errors via JsResult.getException()")
    void singleEvalSyntaxError() {
        AtomicReference<JsResult> captured = new AtomicReference<>();
        runtime.singleEval(captured::set, "this is not valid javascript ===");

        JsResult r = captured.get();
        assertNotNull(r);
        assertFalse(r.isSuccess());
        assertNotNull(r.getException(), "exception must be populated on failure");
    }
}
