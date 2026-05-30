package com.apidech.lib.apidechjavajsruntime.js;

import com.apidech.lib.apidechjavajsruntime.ApidechJavaJsRuntime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsClassInheritanceTest {

    private static final File JS_DIR = new File("src/test/resources/js");

    private ApidechJavaJsRuntime runtime;
    private JsWorkingSpace working;
    private JsFile file;

    @BeforeEach
    void setUp() throws Exception {
        runtime = new ApidechJavaJsRuntime();
        working = runtime.createWorkingSpace(JS_DIR);
        file = working.compile(JsSource.create(new File(JS_DIR, "animals.js")));
    }

    @AfterEach
    void tearDown() {
        working.close();
        runtime.shutdown();
    }

    @Test
    @DisplayName("Cat isInstanceOf Animal returns true")
    void catExtendsAnimal() {
        assertTrue(file.getClass("Cat").isInstanceOf("Animal"));
    }

    @Test
    @DisplayName("Rock isInstanceOf Animal returns false")
    void rockIsNotAnimal() {
        assertFalse(file.getClass("Rock").isInstanceOf("Animal"));
    }

    @Test
    @DisplayName("Class.getExtendedClassName returns parent name")
    void getExtendedClassName() {
        assertEquals("Animal", file.getClass("Cat").getExtendedClassName());
    }

    @Test
    @DisplayName("isInstanceOf with non-existent superclass throws")
    void unknownSuperclassThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> file.getClass("Cat").isInstanceOf("Ghost"));
    }

    @Test
    @DisplayName("isInstanceOf rejects injection in superClassName")
    void rejectsInjectionInSuperName() {
        // Old impl built JS by string-format: this would execute the payload.
        // New impl looks the name up in bindings, so this is a benign 'not found'.
        assertThrows(IllegalArgumentException.class,
            () -> file.getClass("Cat").isInstanceOf("Animal); throw new Error('pwned'); ("));
    }
}
