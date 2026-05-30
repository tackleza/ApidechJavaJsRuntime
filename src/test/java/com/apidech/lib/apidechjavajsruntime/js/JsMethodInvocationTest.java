package com.apidech.lib.apidechjavajsruntime.js;

import com.apidech.lib.apidechjavajsruntime.ApidechJavaJsRuntime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsMethodInvocationTest {

    private static final File JS_DIR = new File("src/test/resources/js");

    private ApidechJavaJsRuntime runtime;
    private JsWorkingSpace working;
    private JsFile file;

    @BeforeEach
    void setUp() throws Exception {
        runtime = new ApidechJavaJsRuntime();
        working = runtime.createWorkingSpace(JS_DIR);
        file = working.compile(JsSource.create(new File(JS_DIR, "calculator.js")));
    }

    @AfterEach
    void tearDown() {
        working.close();
        runtime.shutdown();
    }

    // --- top-level function invocations ---

    @Test
    @DisplayName("Top-level function: add(2, 3) returns 5")
    void callTopLevelFunctionReturningInt() {
        JsFunction add = file.getFunction("add");
        assertNotNull(add, "add should be discoverable");

        JsResult result = add.execute(2, 3);
        assertTrue(result.isSuccess());
        assertEquals(5, result.getResult().asInt());
    }

    @Test
    @DisplayName("Top-level function: greet('Tackle') returns expected string")
    void callTopLevelFunctionReturningString() {
        JsResult result = file.getFunction("greet").execute("Tackle");
        assertTrue(result.isSuccess());
        assertEquals("Hello, Tackle!", result.getResult().asString());
    }

    @Test
    @DisplayName("Top-level function argument introspection")
    void topLevelFunctionArgs() {
        JsFunction add = file.getFunction("add");
        assertEquals(2, add.getArgumentCount());
        assertEquals(List.of("a", "b"), add.getArgumentNames());
    }

    @Test
    @DisplayName("Discovering a non-existent function returns null/false")
    void missingFunction() {
        assertNull(file.getFunction("notThere"));
        assertFalse(file.hasFunction("notThere"));
    }

    // --- class instance method invocations ---

    @Test
    @DisplayName("Instance method: Calculator(10).add(5) returns 15")
    void callInstanceMethodReturningInt() {
        JsClass calcClass = file.getClass("Calculator");
        JsClassInstance calc = calcClass.newInstance(10);

        int result = calc.getMethod("add").execute(5).asInt();
        assertEquals(15, result);
    }

    @Test
    @DisplayName("Instance method chain: add(5) then multiply(2) then getValue() => 30")
    void callInstanceMethodChain() {
        JsClassInstance calc = file.getClass("Calculator").newInstance(10);
        calc.getMethod("add").execute(5);
        calc.getMethod("multiply").execute(2);
        assertEquals(30, calc.getMethod("getValue").execute().asInt());
    }

    @Test
    @DisplayName("Class method introspection: getMethodNames lists declared methods")
    void classMethodNames() {
        JsClass calcClass = file.getClass("Calculator");
        Set<String> names = calcClass.getMethodNames();
        assertTrue(names.containsAll(Set.of("add", "multiply", "getValue")),
            "expected add/multiply/getValue in " + names);
        assertFalse(names.contains("constructor"),
            "constructor should be filtered out by JsClass.getMethodNames");
    }

    @Test
    @DisplayName("hasMethod / getMethodArgsCount / getMethodArgsNames")
    void instanceMethodArgIntrospection() throws Exception {
        JsClass calcClass = file.getClass("Calculator");
        assertTrue(calcClass.hasMethod("add"));
        assertFalse(calcClass.hasMethod("nope"));
        assertEquals(1, calcClass.getMethodArgsCount("add"));
        assertEquals(List.of("n"), calcClass.getMethodArgsNames("add"));
    }

    @Test
    @DisplayName("Getting a non-existent method returns null")
    void missingMethodReturnsNull() {
        JsClassInstance calc = file.getClass("Calculator").newInstance(0);
        assertNull(calc.getMethod("nopeNotReal"),
            "JsClassInstance.getMethod should return null for unknown members");
        assertThrows(NullPointerException.class,
            () -> calc.getMethod("nopeNotReal").execute());
    }
}
