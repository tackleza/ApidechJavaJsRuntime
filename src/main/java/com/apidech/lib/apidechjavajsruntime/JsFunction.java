package com.apidech.lib.apidechjavajsruntime;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

public class JsFunction {
	
	private Value fn;
	public JsFunction(Value fn) {
		this.fn = fn;
	}
	
	public JsResult executeVoid(Object... arguments) {
		JsResult result = new JsResult();
		try {
			fn.executeVoid(arguments);
			result.isSuccess = true;
		}
		catch (PolyglotException e) {
			result.exception = e;
		}
		return result;
	}
	
	public JsResult execute(Object... arguments) {
		JsResult result = new JsResult();
		try {
			result.result = fn.execute(arguments);
			result.isSuccess = true;
		}
		catch (PolyglotException e) {
			result.exception = e;
		}
		return result;
	}
	
	public int getArgumentCount() {
		return fn.getMember("length").asInt();
	}
	
	public List<String> getArgumentNames() {
        // Extract source and parse between parentheses
        String fnSrc = fn.invokeMember("toString").asString();
        int start = fnSrc.indexOf('(') + 1;
        int end = fnSrc.indexOf(')', start);
        if (start < 0 || end < 0 || end <= start) {
            return Collections.emptyList();
        }
        String paramsSection = fnSrc.substring(start, end).trim();
        if (paramsSection.isEmpty()) {
            return Collections.emptyList();
        }
        // Split names by comma and trim
        return Arrays.stream(paramsSection.split(","))
                     .map(String::trim)
                     .collect(Collectors.toList());
    }
}
