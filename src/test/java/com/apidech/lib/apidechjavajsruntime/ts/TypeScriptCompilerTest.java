package com.apidech.lib.apidechjavajsruntime.ts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TypeScriptCompilerTest {

    @Test
    @DisplayName("getOutputDir resolves outDir from comment-laden tsconfig.json")
    void readsOutDirFromCommentedTsconfig() {
        File root = new File("testts/testproject");
        TypeScriptCompiler compiler = new TypeScriptCompiler(root);

        File outDir = compiler.getOutputDir();
        assertNotNull(outDir, "outDir should be resolved despite // comments in tsconfig.json");
        assertEquals(new File(root, "dist").getAbsolutePath(), outDir.getAbsolutePath());
    }
}
